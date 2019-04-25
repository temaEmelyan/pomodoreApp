package com.temelyan.pomoapp.util.ExceptionHandler

import java.util.*

class ErrorInfo(url: CharSequence, private val type: String, private val typeMessage: String, vararg details: String) {
    private val url: String
    private val details: Array<out String>

    init {
        this.url = url.toString()
        this.details = details
    }

    override fun toString(): String {
        return "ErrorInfo{" +
                "url='" + url + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", typeMessage='" + typeMessage + '\''.toString() +
                ", details=" + Arrays.toString(details) +
                '}'.toString()
    }
}
