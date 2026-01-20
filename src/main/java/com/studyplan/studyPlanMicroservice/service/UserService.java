package com.studyplan.studyPlanMicroservice.service;

import com.studyplan.studyPlanMicroservice.data.PageResponse;
import com.studyplan.studyPlanMicroservice.data.UserData;
import com.studyplan.studyPlanMicroservice.domain.User;
import com.studyplan.studyPlanMicroservice.jpa.UserRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserData createUser(UserData data) {
        if (userRepository.existsByEmail(data.getEmail())) {
            throw new RuntimeException("User already exists with email: " + data.getEmail());
        }

        User user = User.builder()
                .email(data.getEmail())
                .dateRegister(data.getDateRegister())
                .datePurchase(data.getDatePurchase())
                .type(data.getType())
                .build();

        User saved = userRepository.save(user);
        return toData(saved);
    }

    @Transactional(readOnly = true)
    public UserData getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return toData(user);
    }

    @Transactional(readOnly = true)
    public UserData getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return toData(user);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserData> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "email")
        );

        Page<User> userPage = userRepository.findAll(pageable);

        return PageResponse.from(userPage, this::toData);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserData> searchUsers(
            String email,
            String type,
            Integer page,
            Integer size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "email")
        );
        Page<User> userPage = userRepository.findAll(
                buildSpecification(email, type),
                pageable
        );
        return PageResponse.from(userPage, this::toData);
    }

    @Transactional
    public UserData updateUser(Integer id, UserData data) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        // verify that the email address is not in use by another user.
        if (!user.getEmail().equals(data.getEmail()) && userRepository.existsByEmail(data.getEmail())) {
            throw new RuntimeException("Email already in use: " + data.getEmail());
        }

        user.setEmail(data.getEmail());
        user.setDateRegister(data.getDateRegister());
        user.setDatePurchase(data.getDatePurchase());
        user.setType(data.getType());

        User updated = userRepository.save(user);
        return toData(updated);
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    private Specification<User> buildSpecification(String email, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (email != null && !email.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }
            if (type != null && !type.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("type")),
                                type.toLowerCase()
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private UserData toData(User user) {
        return UserData.builder()
                .idUser(user.getIdUser())
                .email(user.getEmail())
                .dateRegister(user.getDateRegister())
                .datePurchase(user.getDatePurchase())
                .type(user.getType())
                .build();
    }
}