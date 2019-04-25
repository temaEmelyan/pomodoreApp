package com.temelyan.pomoapp.config

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class CustomErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(webRequest: WebRequest, includeStackTrace: Boolean): Map<String, Any> {
        val errorAttributes = super.getErrorAttributes(webRequest, false)
        val status = Integer.parseInt(errorAttributes["status"].toString())

        if (status >= 500) {

            errorAttributes.remove("error")
            errorAttributes["message"] = "Server error"
        }
        return errorAttributes
    }
}
