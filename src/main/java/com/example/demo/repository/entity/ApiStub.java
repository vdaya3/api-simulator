package com.example.demo.repository.entity;// src/main/java/com/example/apisimulator/model/ApiStub.java

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity
public class ApiStub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String path;
    private String method; // HTTP method (GET, POST, etc.)
    @ElementCollection
    private Map<String, String> headers;
    private int successStatus;

    @Column(columnDefinition = "TEXT")
    private String successResponse;

    private int errorStatus;

    @Column(columnDefinition = "TEXT")
    private String errorResponse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(String successResponse) {
        this.successResponse = successResponse;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }
}
