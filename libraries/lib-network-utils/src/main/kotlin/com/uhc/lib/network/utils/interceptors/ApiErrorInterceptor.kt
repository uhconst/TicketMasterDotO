package com.uhc.lib.network.utils.interceptors

import com.uhc.lib.network.utils.data.DefaultException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ApiErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            throwDefaultException(response)
        }

        return response
    }

    /**
     * Throw Default Exception
     * If any remote error occur try to get json body and
     * fill up DefaultException parameters.
     * When throw occur in RX the onError is called immediately
     */
    private fun throwDefaultException(response: Response) {
        try {
            val jsonError = response.body.string()
            val jsonObj = JSONObject(jsonError)
            throw DefaultException(
                code = jsonObj.getString("status_code"),
                message = jsonObj.getString("status_message")
            )
        } catch (e: JSONException) {
            throw DefaultException()
        } catch (e: IOException) {
            throw DefaultException()
        }
    }
}
