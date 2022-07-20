package com.example.demo_uloadfile_java.entity.bo;

public class SystemResponse<T> {
    private int status;

    private String error;
    private String message;
    private T data;

    public SystemResponse() {
    }

    public SystemResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public SystemResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

