import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import okhttp3.*
import settings.AppSettingsState
import java.io.IOException

class Translator(
    private val appSettingsState: AppSettingsState = AppSettingsState.instance
) {

    companion object {
        const val papagoTranslationURL = "https://openapi.naver.com/v1/papago/n2mt"
    }

    private val client = OkHttpClient()
    private val naverApiBaseHeaders: Headers
        get() {
            Headers.headersOf(
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
        val headers = naverApiBaseHeaders
        // TODO: Execute language detection when `isEnabledLanguageDetection` is `true`.
        val requestBody = FormBody.Builder()
            .add("text", text)
            .add("source", appSettingsState.languageSettings.defaultSourceLanguage)
            .add("target", appSettingsState.languageSettings.targetLanguage)
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
}
