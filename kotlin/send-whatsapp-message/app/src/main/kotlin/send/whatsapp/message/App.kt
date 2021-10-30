/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package send.whatsapp.message

import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject


/**
 * Class *Whatsapp*.
 *
 * This class initializing Twilio to send whatsapp message using Twilio Whatsapp API
 *
 * @property accountSsid Twilio Account SID.
 * @property authToken Twilio Account Auth Token.
 * @property fromNumber Twilio Sender Number.
 * @constructor Init Twilio.
 */
class Whatsapp(private val accountSsid: String, private val authToken: String, private val fromNumber: String) {
    /**
     * send whatsapp message.
     * @param phoneNumber target phone number.
     * @param text message body.
     */
    fun sendWhatsAppMessage(phoneNumber: String, text: String): ApiResponse {
        val client = OkHttpClient().newBuilder().build()
        val formBody: RequestBody = FormBody.Builder()
            .add("From", "whatsapp:${fromNumber}")
            .add("Body", text)
            .add("To", "whatsapp:${phoneNumber}")
            .build()

        val request: Request = Request.Builder()
            .url("https://api.twilio.com/2010-04-01/Accounts/${accountSsid}/Messages.json")
            .addHeader("Authorization", Credentials.basic(accountSsid, authToken))
            .addHeader("Accept", "application/json")
            .post(formBody)
            .build()

        val response: Response = client.newCall(request).execute()
        val jsonres: String = response.body()?.string() ?: "NULL"
        val objRes: ApiResponse = Gson().fromJson(jsonres, ApiResponse::class.java)
        return  objRes
    }
}

/**
 * main function get ENV Variabel then call sendWhatsAppMessage inside Whatsapp Class.
 */
fun main() {
    val payload = System.getenv("APPWRITE_FUNCTION_DATA")
    val accountSsid = System.getenv("ACCOUNT_SID")
    val authToken = System.getenv("AUTH_TOKEN")
    val fromNumber = System.getenv("FROM_NUMBER")
    if (!payload.isNullOrEmpty()) {
        try {
            val json = JSONObject(payload)
            val phoneNumber = json.getString("phoneNumber")
            val text = json.getString("text")
            val whatsapp = Whatsapp(accountSsid, authToken, fromNumber)
            val msg = whatsapp.sendWhatsAppMessage(phoneNumber, text)
            println("Message ${msg.status} TO : ${msg.to} at ${msg.date_created}")
        } catch (e: Exception) {
            println("Error Sending Message")
            println(e.message)
        }
    }else{
        println("You Must Provide Phone Number and text message")
    }
}