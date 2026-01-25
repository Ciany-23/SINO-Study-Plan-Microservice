package com.studyplan.studyPlanMicroservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follower")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_follower")
    private Integer idFollower;

    @Column(name = "id_user", nullable = false)
    private Integer idUser; // The user being followed

    @Column(name = "follower_user_id")
    private Integer followerUserId; // The user who follows

    @Column(name = "status", length = 50)
    private String status;
}
