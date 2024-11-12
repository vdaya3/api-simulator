package com.example.demo.service;

import com.example.demo.model.EndpointRequest;
import com.example.demo.repository.ApiStubRepository;
import com.example.demo.repository.entity.ApiStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ApiSimulatorService {

    @Autowired
    private ApiStubRepository apiStubRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiStub saveEndpoint(EndpointRequest request) {
        Objects.requireNonNull(request, "request can't be null!");
        Optional<ApiStub> apiStub = apiStubRepository.findByProjectNameAndPathAndMethod(request.getProjectName(), request.getPath(), request.getMethod());
        if (apiStub.isPresent()) {
            throw new IllegalArgumentException("An endpoint with the same method and path already exists in this project.");
        }
        // Convert request to entity
        ApiStub stub = new ApiStub();
        stub.setProjectName(request.getProjectName());
        stub.setPath(request.getPath());
        stub.setMethod(request.getMethod());
        stub.setHeaders(request.getHeaders());
        stub.setSuccessStatus(request.getSuccessStatus());
        stub.setErrorStatus(request.getErrorStatus());

        // Convert successResponse and errorResponse maps to JSON strings
        stub.setSuccessResponse(mapToJson(request.getSuccessResponse()));
        stub.setErrorResponse(mapToJson(request.getErrorResponse()));

        return apiStubRepository.save(stub);
    }

    private String mapToJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert map to JSON", e);
        }
    }

    public Optional<ApiStub> getEndpoint(String projectName, String path, String method) {
        return apiStubRepository.findByProjectNameAndPathAndMethod(projectName, path, method);
    }

    public List<ApiStub> getEndpointsByProject(String projectName) {
        return apiStubRepository.findAllByProjectName(projectName);
    }

    public boolean deleteEndpoint(String projectName, String path, String method) {
        Optional<ApiStub> apiStub = apiStubRepository.findByProjectNameAndPathAndMethod(projectName, path, method);
        apiStub.ifPresent(apiStubRepository::delete);
        return apiStub.isPresent();
    }

    public boolean deleteProject(String projectName) {
        List<ApiStub> projectEndpoints = apiStubRepository.findAllByProjectName(projectName);
        if (!projectEndpoints.isEmpty()) {
            apiStubRepository.deleteAllByProjectName(projectName);
            return true;
        }
        return false;
    }
}
