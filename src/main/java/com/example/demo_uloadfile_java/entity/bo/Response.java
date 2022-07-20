package com.example.demo_uloadfile_java.entity.bo;

import com.example.demo_uloadfile_java.utils.StringResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    private Response() {
    }

    public static <T> SystemResponse<T> from(HttpStatus status, String msg) {
        return new SystemResponse<>(status.value(), msg);
    }

    public static <T> ResponseEntity<SystemResponse<T>> unauthorized(String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new SystemResponse<>(401, msg));
    }

    public static <T> ResponseEntity<SystemResponse<T>> unauthorized(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new SystemResponse<>(code, msg));
    }

    public static <T> ResponseEntity<SystemResponse<T>> badRequest(String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new SystemResponse<>(400, msg));
    }

    public static <T> ResponseEntity<SystemResponse<T>> badRequest(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new SystemResponse<>(code, msg));
    }

    public static <T> ResponseEntity<SystemResponse<T>> badRequest(int code, String msg, T data) {
        return ResponseEntity
                .badRequest()
                .body(new SystemResponse<>(code, msg, data));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(int code, String msg, T body) {
        return ResponseEntity.ok(new SystemResponse<>(code, msg, body));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(T body) {
        return ResponseEntity.ok(new SystemResponse<>(100, StringResponse.OK,body));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok() {
        return ResponseEntity.ok(new SystemResponse<>(100, StringResponse.OK, null));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(int code, String message) {
        return ResponseEntity.ok(new SystemResponse<>(code, message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(String message, T body) {
        return ResponseEntity.ok(new SystemResponse<>(100, message, body));
    }

//    public static <T> ResponseEntity<SystemResponse<T>> httpError(HttpErrorException e) {
//        return ResponseEntity
//                .status(e.getStatus())
//                .body(from(e.getStatus(), e.getMessage()));
//    }
}
