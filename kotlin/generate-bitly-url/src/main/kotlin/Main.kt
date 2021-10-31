import kong.unirest.Unirest
import kong.unirest.UnirestException

var API_KEY: String? = null

@Throws(UnirestException::class)
fun main(args: Array<String>) {
    API_KEY = System.getenv("BITLY_API_KEY")
    val url = System.getenv("APPWRITE_FUNCTION_DATA")

    if (url != null && url.isNotEmpty()) {
        try {
            val response = getBitlyLink(url)
            println(response)
        } catch (e: Exception) {
            print("[ERROR] There was an error")
            println(e.message)
        }
    } else {
        println("[INFO] APPWRITE_FUNCTION_DATA is empty")
    }
}

@Throws(UnirestException::class)
fun getBitlyLink(url: String?): String {
    val request = Unirest.post("https://api-ssl.bitly.com/v4/shorten")
        .header("Authorization", "Bearer $API_KEY")
        .field("long_url", url)
        .asJson()
    return request.body.toPrettyString()
}