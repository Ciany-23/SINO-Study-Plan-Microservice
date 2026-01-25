package com.studyplan.studyPlanMicroservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requirement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requirement")
    private Integer idRequirement;

    @Column(name = "id_course", nullable = false)
    private Integer idCourse;

    @Column(name = "id_course_requirement", nullable = false)
    private Integer idCourseRequirement;

    @Column(name = "type_requirement", nullable = false)
    private String typeRequirement;
}
