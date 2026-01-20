package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Integer>,
        JpaSpecificationExecutor<StudyPlan> {

    List<StudyPlan> findByIdUniversity(Integer universityId);

    boolean existsByDscCareerAndIdUniversity(String career, Integer universityId);
}