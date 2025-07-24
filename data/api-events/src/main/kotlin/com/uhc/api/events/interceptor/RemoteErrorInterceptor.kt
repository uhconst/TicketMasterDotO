package com.uhc.api.events.interceptor

import okhttp3.Interceptor
import okhttp3.Response

//todo move to library module
class RemoteErrorInterceptor : Interceptor {

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
/*        try {
            val jsonError = response.body.string()
            val jsonObj = JSONObject(jsonError)
            throw DefaultException(
                jsonObj.getString("status_code"),
                jsonObj.getString("status_message")
            )
        } catch (e: JSONException) {
            throw DefaultException()
        } catch (e: IOException) {
            throw DefaultException()
        }*/
    }

}