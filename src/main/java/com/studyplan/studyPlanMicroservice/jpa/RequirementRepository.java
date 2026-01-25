package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
}
