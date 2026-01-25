package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    List<StudentCourse> findByUser_IdUser(Integer idUser);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc WHERE sc.user.idUser = :userId AND sc.course.idStudyPlan = :planId AND sc.status.dscName = 'Aprobado'")
    long countApprovedCourses(@Param("userId") Integer userId, @Param("planId") Integer planId);
}
