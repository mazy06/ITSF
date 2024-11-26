package io.mazy.testexercice2apirest.api;

public class DataResponse<T> extends ApiResponse<T> {

    private T data;

    public DataResponse(T data) {
        super(true);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
