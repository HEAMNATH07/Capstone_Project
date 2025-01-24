package com.example.WorkSpace.Response;

public class MessageResponse {
    private String message;
    private int statusCode;

    // Constructors
    public MessageResponse() {}

    public MessageResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    // toString for logging/debugging
    @Override
    public String toString() {
        return "MessageResponse{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
