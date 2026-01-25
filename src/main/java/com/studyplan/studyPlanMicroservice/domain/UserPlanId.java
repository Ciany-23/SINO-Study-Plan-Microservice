package com.studyplan.studyPlanMicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanId implements Serializable {
    private Integer idUser;
    private Integer idStudyPlan;
}
