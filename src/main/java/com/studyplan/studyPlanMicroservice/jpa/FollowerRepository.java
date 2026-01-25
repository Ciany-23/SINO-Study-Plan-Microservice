package com.studyplan.studyPlanMicroservice.jpa;

import com.studyplan.studyPlanMicroservice.domain.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    long countByIdUser(Integer idUser); // Followers
    long countByFollowerUserId(Integer followerUserId); // Following
}
