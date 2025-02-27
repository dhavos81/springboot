package com.testing.course.springboottesting.mainApp.rest;

import java.util.Date;
import java.util.UUID;

public class EmployeeErrorResponse {

    private int status;
    private String message;
    private Date timestamp;
    private UUID uuid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
