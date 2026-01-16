package com.studyplan.studyPlanMicroservice.controller;

import com.studyplan.studyPlanMicroservice.data.ApiResponse;
import com.studyplan.studyPlanMicroservice.data.PageResponse;
import com.studyplan.studyPlanMicroservice.data.UniversityData;
import com.studyplan.studyPlanMicroservice.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
@Tag(name = "Universities", description = "University management")
@CrossOrigin(origins = "*")
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping
    @Operation(summary = "Create a new university")
    public ResponseEntity<ApiResponse<UniversityData>> createUniversity(
            @Valid @RequestBody UniversityData data) {
        UniversityData created = universityService.createUniversity(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "University created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get university by ID")
    public ResponseEntity<ApiResponse<UniversityData>> getUniversityById(@PathVariable Integer id) {
        UniversityData university = universityService.getUniversityById(id);
        return ResponseEntity.ok(ApiResponse.success(university, "University retrieved"));
    }

    @GetMapping
    @Operation(summary = "Get all universities with pagination and optional filters")
    public ResponseEntity<ApiResponse<PageResponse<UniversityData>>> getAllUniversities(
            @Parameter(description = "University name (optional filter)")
            @RequestParam(required = false) String name,
            @Parameter(description = "Country (optional filter)")
            @RequestParam(required = false) String country,
            @Parameter(description = "Status (optional filter)")
            @RequestParam(required = false) Boolean status,
            @Parameter(description = "Page number (0-indexed)")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") Integer size) {

        PageResponse<UniversityData> result;

        if (name != null || country != null || status != null) {
            result = universityService.searchUniversities(name, country, status, page, size);
        } else {
            result = universityService.getAllUniversities(page, size);
        }

        return ResponseEntity.ok(ApiResponse.success(result, "Universities retrieved successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update university")
    public ResponseEntity<ApiResponse<UniversityData>> updateUniversity(
            @PathVariable Integer id, @Valid @RequestBody UniversityData data) {
        UniversityData updated = universityService.updateUniversity(id, data);
        return ResponseEntity.ok(ApiResponse.success(updated, "University updated"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete university")
    public ResponseEntity<ApiResponse<Void>> deleteUniversity(@PathVariable Integer id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.ok(ApiResponse.success(null, "University deleted"));
    }
}