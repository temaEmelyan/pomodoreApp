package com.temelyan.pomoapp.util.ExceptionHandler;

import java.util.Arrays;

public class ErrorInfo {
    private final String url;
    private final String type;
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(CharSequence url, String type, String typeMessage, String... details) {
        this.url = url.toString();
        this.type = type;
        this.typeMessage = typeMessage;
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", details=" + Arrays.toString(details) +
                '}';
    }
}