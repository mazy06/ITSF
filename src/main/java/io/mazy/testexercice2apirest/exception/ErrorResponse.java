package io.mazy.testexercice2apirest.exception;

import io.mazy.testexercice2apirest.api.ApiResponse;

public class ErrorResponse extends ApiResponse <Void>{

    private String message;

    public ErrorResponse(String message) {
        super(false);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
