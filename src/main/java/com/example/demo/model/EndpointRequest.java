package com.example.demo.model;

import java.util.Map;

public class EndpointRequest {
    private String projectName;
    private String path;
    private String method;
    private Map<String, String> headers;
    private int successStatus;          // Ensure this is int
    private Map<String, Object> successResponse; // Ensure this is a Map, not String
    private int errorStatus;             // Ensure this is int
    private Map<String, Object> errorResponse; // Ensure this is a Map, not String

    // Getters and setters

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(int successStatus) {
        this.successStatus = successStatus;
    }

    public Map<String, Object> getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(Map<String, Object> successResponse) {
        this.successResponse = successResponse;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public Map<String, Object> getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(Map<String, Object> errorResponse) {
        this.errorResponse = errorResponse;
    }
}
