package org.example;

class StatusMassage {
    private String status;
    private String message;

    public StatusMassage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StatusMassage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}