package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Integer>,
        JpaSpecificationExecutor<StudyPlan> {

    List<StudyPlan> findByIdUniversity(Integer universityId);

    boolean existsByDscCareerAndIdUniversity(String career, Integer universityId);

    @Query(value = "SELECT sp.* FROM study_plan sp JOIN user_plan up ON sp.id_study_plan = up.id_study_plan JOIN user u ON up.id_user = u.id_user WHERE u.email = :email", nativeQuery = true)
    List<StudyPlan> findByUserEmail(@Param("email") String email);

    @Modifying
    @Query(value = "INSERT INTO user_plan (id_user, id_study_plan, date_start) VALUES (:userId, :planId, CURRENT_DATE)", nativeQuery = true)
    void linkUserToPlan(@Param("userId") Integer userId, @Param("planId") Integer planId);

    @Query(value = "SELECT COUNT(*) FROM user_plan WHERE id_user = :userId AND id_study_plan = :planId", nativeQuery = true)
    int existsRelationship(@Param("userId") Integer userId, @Param("planId") Integer planId);
}