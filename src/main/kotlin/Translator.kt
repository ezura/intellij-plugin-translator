import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import okhttp3.*
import settings.AppSettingsState
import java.io.IOException

class Translator {

    companion object {
        const val papagoURL = "https://openapi.naver.com/v1/papago/n2mt"
    }

    @Throws(IOException::class)
    fun translate(text: String): Deferred<String> = GlobalScope.async(
        Dispatchers.Default,
        CoroutineStart.DEFAULT
    ) {
        val client = OkHttpClient()
        val appClientConfig = AppSettingsState.instance.apiClientConfig
        val headers = Headers.headersOf(
            "Content-Type", "application / x-www-form-urlencoded; charset = UTF-8",
            "X-Naver-Client-Id", appClientConfig.id,
            "X-Naver-Client-Secret", appClientConfig.secret
        )
        val requestBody = FormBody.Builder()
            .add("text", text)
            .add("source", "ko")
            .add("target", "ja")
            .build()

        val request = Request.Builder()
            .url(papagoURL)
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
