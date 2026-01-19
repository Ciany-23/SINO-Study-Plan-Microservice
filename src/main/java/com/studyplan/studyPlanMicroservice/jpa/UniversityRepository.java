package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer>,
        JpaSpecificationExecutor<University> {

    Optional<University> findByDscName(String dscName);
    boolean existsByDscName(String dscName);
}