package com.example.demo.controller;

import com.example.demo.model.EndpointRequest;
import com.example.demo.repository.entity.ApiStub;
import com.example.demo.service.ApiSimulatorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/simulator")
public class ApiSimulatorController {

    @Autowired
    private ApiSimulatorService apiSimulatorService;


    // Configure a new API endpoint
    @PostMapping("/configure")
    public ResponseEntity<?> configureEndpoint(
            @RequestBody EndpointRequest request) {
        try {
            ApiStub savedEndpoint = apiSimulatorService.saveEndpoint(request);
            return ResponseEntity.ok(savedEndpoint);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Get all endpoints for a specific project
    @GetMapping("/{projectName}/endpoints")
    public ResponseEntity<List<ApiStub>> getEndpointsByProject(@PathVariable String projectName) {
        List<ApiStub> endpoints = apiSimulatorService.getEndpointsByProject(projectName);
        return ResponseEntity.ok(endpoints);
    }

    // Delete a specific endpoint
    @DeleteMapping("/{projectName}/endpoints")
    public ResponseEntity<String> deleteEndpoint(
            @PathVariable String projectName,
            @RequestParam String path,
            @RequestParam String method) {
        boolean deleted = apiSimulatorService.deleteEndpoint(projectName, path, method);
        if (deleted) {
            return ResponseEntity.ok("Endpoint deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Endpoint not found");
        }
    }

    // Delete a project and its associated endpoints
    @DeleteMapping("/{projectName}")
    public ResponseEntity<String> deleteProject(@PathVariable String projectName) {
        boolean deleted = apiSimulatorService.deleteProject(projectName);
        if (deleted) {
            return ResponseEntity.ok("Project and associated endpoints deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Project not found");
        }
    }

    // Simulate API request
    @RequestMapping("/{projectName}/**")
    public ResponseEntity<String> handleRequest(
            @RequestHeader(value = "X-Simulate-Error", required = false) Boolean simulateError,
            HttpServletRequest request,
            @PathVariable String projectName) {

        // Determine the HTTP method and dynamic path
        String method = request.getMethod();
        String path = request.getRequestURI().substring(request.getRequestURI().indexOf(projectName) + projectName.length());

        // Retrieve the endpoint details from the service
        Optional<ApiStub> optionalEndpoint = apiSimulatorService.getEndpoint(projectName, path, method);

        if (optionalEndpoint.isPresent()) {
            ApiStub endpoint = optionalEndpoint.get();

            // Choose success or error response based on the simulateError flag
            int status = Boolean.TRUE.equals(simulateError) ? endpoint.getErrorStatus() : endpoint.getSuccessStatus();
            String responseBody = Boolean.TRUE.equals(simulateError) ? endpoint.getErrorResponse() : endpoint.getSuccessResponse();

            // Return standard HTTP response with the configured status and response body
            return ResponseEntity.status(status).body(responseBody);
        }

        // Return a 404 Not Found if the endpoint is not configured
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endpoint not configured");
    }
}
