import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.openapi.diagnostic.logger
import kotlinx.coroutines.*
import okhttp3.*
import settings.AppSettingsState
import java.io.IOException

class Translator(
    private val appSettingsState: AppSettingsState = AppSettingsState.instance
) {

    companion object {
        val logger = logger<Translator>()
        const val papagoTranslationURL = "https://openapi.naver.com/v1/papago/n2mt"
        const val papagoDetectLangURL = "https://openapi.naver.com/v1/papago/detectLangs"
    }

    private val client = OkHttpClient()
    private val naverApiBaseHeaders: Headers
        get() {
            return Headers.headersOf(
                "Content-Type", "application / x-www-form-urlencoded; charset = UTF-8",
                "X-Naver-Client-Id", appSettingsState.apiClientSettings.id,
                "X-Naver-Client-Secret", appSettingsState.apiClientSettings.secret
            )
        }

    @Throws(IOException::class)
    fun translateAsync(text: String): Deferred<String> = GlobalScope.async(
        Dispatchers.Default,
        CoroutineStart.DEFAULT
    ) {
        val sourceLangCode: String =
            if (appSettingsState.languageSettings.isEnabledLanguageDetection)
                try {
                    this@Translator.detectLanguage(text)
                } catch (e: IOException) {
                    logger.error(e)
                    appSettingsState.languageSettings.defaultSourceLanguage
                }
            else
                appSettingsState.languageSettings.defaultSourceLanguage

        val targetLangCode = appSettingsState.languageSettings.targetLanguage

        if (sourceLangCode == targetLangCode) {
            return@async text
        }

        val headers = naverApiBaseHeaders

        val requestBody = FormBody.Builder()
            .add("text", text)
            .add("source", sourceLangCode)
            .add("target", targetLangCode)
            .build()

        val request = Request.Builder()
            .url(papagoTranslationURL)
            .headers(headers)
            .post(requestBody)
            .build()

        val result = client.newCall(request)
            .execute()
            .use { response ->
                val result: Result<String> = kotlin
                    .runCatching {
                        if (!response.isSuccessful) {
                            throw IOException("Unexpected code $response")
                        }
                        val json = response.body?.string()
                            ?: throw IOException("Unexpected body $response")
                        val mapper = ObjectMapper()
                        val node = mapper.readTree(json)
                        val translatedText = node
                            .get("message")
                            .get("result")
                            .get("translatedText")
                            .asText()
                        return@runCatching translatedText
                    }
                return@use result
            }
        return@async result.getOrThrow()
    }

    @Throws(IOException::class)
    private fun detectLanguage(text: String): String {
        val headers = naverApiBaseHeaders
        val requestBody = FormBody.Builder()
            .add("query", text)
            .build()

        val request = Request.Builder()
            .url(papagoDetectLangURL)
            .headers(headers)
            .post(requestBody)
            .build()

        val result = client.newCall(request)
            .execute()
            .use { response ->
                val result: Result<String> = kotlin
                    .runCatching {
                        if (!response.isSuccessful) {
                            throw IOException("Unexpected code $response")
                        }
                        val json = response.body?.string()
                            ?: throw IOException("Unexpected body $response")
                        val mapper = ObjectMapper()
                        val node = mapper.readTree(json)
                        val langCode = node.get("langCode").asText()
                        return@runCatching langCode
                    }
                return@use result
            }
        return result.getOrThrow()
    }
}
