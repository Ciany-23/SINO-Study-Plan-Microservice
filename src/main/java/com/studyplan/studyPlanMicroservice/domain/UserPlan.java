package com.studyplan.studyPlanMicroservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserPlanId.class)
public class UserPlan {
    @Id
    @Column(name = "id_user")
    private Integer idUser;

    @Id
    @Column(name = "id_study_plan")
    private Integer idStudyPlan;

    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;
}
