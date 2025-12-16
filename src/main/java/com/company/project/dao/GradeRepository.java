package com.company.project.dao;

import java.util.List;

import com.company.project.domain.Grade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepositoryImplementation<Grade, Long> {
    @Query("SELECT s FROM Grade s LEFT JOIN FETCH s.school")
    List<Grade> findAllWithSchool();

    @Query("SELECT s FROM Grade s LEFT JOIN FETCH s.school where s.school.id = :schoolId")
    List<Grade> findBySchoolIdWithSchool(@Param("schoolId") Long schoolId);

    List<Grade> findBySchoolId(Long schoolId);
}
