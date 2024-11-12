package com.example.demo.repository;

import com.example.demo.repository.entity.ApiStub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiStubRepository extends JpaRepository<ApiStub, Long> {
    Optional<ApiStub> findByProjectNameAndPathAndMethod(String projectName, String path, String method);
    List<ApiStub> findAllByProjectName(String projectName);
    void deleteAllByProjectName(String projectName);
}