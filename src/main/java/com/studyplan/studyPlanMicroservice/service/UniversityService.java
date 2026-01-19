package com.studyplan.studyPlanMicroservice.service;

import com.studyplan.studyPlanMicroservice.data.PageResponse;
import com.studyplan.studyPlanMicroservice.data.UniversityData;
import com.studyplan.studyPlanMicroservice.domain.University;
import com.studyplan.studyPlanMicroservice.jpa.UniversityRepository;

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
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Transactional
    public UniversityData createUniversity(UniversityData data) {
        if (universityRepository.existsByDscName(data.getDscName())) {
            throw new RuntimeException("University already exists: " + data.getDscName());
        }

        University university = University.builder()
                .dscName(data.getDscName())
                .dscCountry(data.getDscCountry())
                .status(data.getStatus())
                .build();

        University saved = universityRepository.save(university);
        return toData(saved);
    }

    @Transactional(readOnly = true)
    public UniversityData getUniversityById(Integer id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found: " + id));
        return toData(university);
    }

    @Transactional(readOnly = true)
    public PageResponse<UniversityData> getAllUniversities(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "dscName")
        );

        Page<University> universityPage = universityRepository.findAll(pageable);

        return PageResponse.from(universityPage, this::toData);
    }

    @Transactional(readOnly = true)
    public PageResponse<UniversityData> searchUniversities(
            String name,
            String country,
            Boolean status,
            Integer page,
            Integer size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, "dscName")
        );
        Page<University> universityPage = universityRepository.findAll(
                buildSpecification(name, country, status),
                pageable
        );
        return PageResponse.from(universityPage, this::toData);
    }

    @Transactional
    public UniversityData updateUniversity(Integer id, UniversityData data) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found: " + id));

        university.setDscName(data.getDscName());
        university.setDscCountry(data.getDscCountry());
        university.setStatus(data.getStatus());

        University updated = universityRepository.save(university);
        return toData(updated);
    }

    @Transactional
    public void deleteUniversity(Integer id) {
        if (!universityRepository.existsById(id)) {
            throw new RuntimeException("University not found: " + id);
        }
        universityRepository.deleteById(id);
    }

    private Specification<University> buildSpecification(String name, String country, Boolean status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("dscName")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }
            if (country != null && !country.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("dscCountry")),
                                "%" + country.toLowerCase() + "%"
                        )
                );
            }
            if (status != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private UniversityData toData(University university) {
        return UniversityData.builder()
                .idUniversity(university.getIdUniversity())
                .dscName(university.getDscName())
                .dscCountry(university.getDscCountry())
                .status(university.getStatus())
                .build();
    }
}