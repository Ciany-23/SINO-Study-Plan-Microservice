package com.studyplan.studyPlanMicroservice.controller;

import com.studyplan.studyPlanMicroservice.data.ApiResponse;
import com.studyplan.studyPlanMicroservice.data.PageResponse;
import com.studyplan.studyPlanMicroservice.data.StudyPlanData;
import com.studyplan.studyPlanMicroservice.service.StudyPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-plans")
@RequiredArgsConstructor
@Tag(name = "Study Plans", description = "Study plan management")
@CrossOrigin(origins = "*")
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    @PostMapping
    @Operation(summary = "Create a new study plan")
    public ResponseEntity<ApiResponse<StudyPlanData>> createStudyPlan(
            @Valid @RequestBody StudyPlanData data) {
        StudyPlanData created = studyPlanService.createStudyPlan(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Study plan created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get study plan by ID")
    public ResponseEntity<ApiResponse<StudyPlanData>> getStudyPlanById(@PathVariable Integer id) {
        StudyPlanData studyPlan = studyPlanService.getStudyPlanById(id);
        return ResponseEntity.ok(ApiResponse.success(studyPlan, "Study plan retrieved"));
    }

    @GetMapping
    @Operation(summary = "Get all study plans with pagination and optional filters")
    public ResponseEntity<ApiResponse<PageResponse<StudyPlanData>>> getAllStudyPlans(
            @Parameter(description = "Study plan name (optional filter)")
            @RequestParam(required = false) String name,
            @Parameter(description = "Career name (optional filter)")
            @RequestParam(required = false) String career,
            @Parameter(description = "University ID (optional filter)")
            @RequestParam(required = false) Integer universityId,
            @Parameter(description = "Status (optional filter)")
            @RequestParam(required = false) Boolean status,
            @Parameter(description = "Page number (0-indexed)")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") Integer size) {

        PageResponse<StudyPlanData> result;

        if (name != null || career != null || universityId != null || status != null) {
            result = studyPlanService.searchStudyPlans(name, career, universityId, status, page, size);
        } else {
            result = studyPlanService.getAllStudyPlans(page, size);
        }

        return ResponseEntity.ok(ApiResponse.success(result, "Study plans retrieved successfully"));
    }

    @GetMapping("/university/{universityId}")
    @Operation(summary = "Get study plans by university")
    public ResponseEntity<ApiResponse<List<StudyPlanData>>> getStudyPlansByUniversity(
            @PathVariable Integer universityId) {
        List<StudyPlanData> plans = studyPlanService.getStudyPlansByUniversity(universityId);
        return ResponseEntity.ok(ApiResponse.success(plans, "Study plans retrieved"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update study plan")
    public ResponseEntity<ApiResponse<StudyPlanData>> updateStudyPlan(
            @PathVariable Integer id, @Valid @RequestBody StudyPlanData data) {
        StudyPlanData updated = studyPlanService.updateStudyPlan(id, data);
        return ResponseEntity.ok(ApiResponse.success(updated, "Study plan updated"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete study plan")
    public ResponseEntity<ApiResponse<Void>> deleteStudyPlan(@PathVariable Integer id) {
        studyPlanService.deleteStudyPlan(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Study plan deleted"));
    }
}