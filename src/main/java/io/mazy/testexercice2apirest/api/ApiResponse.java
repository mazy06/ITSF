package io.mazy.testexercice2apirest.api;

public abstract class ApiResponse<T> {

    private boolean success;

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
