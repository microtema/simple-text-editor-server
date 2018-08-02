package de.seven.fate.texteditor.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageResponse {

    private int status;
    private String error;
    private String exception;

    public ErrorMessageResponse() {
    }

    public ErrorMessageResponse(int status, String error, String exception) {
        this.status = status;
        this.error = error;
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    @JsonProperty("message")
    public String getError() {
        return error;
    }

    @JsonProperty("type")
    public String getException() {
        return exception;
    }
}