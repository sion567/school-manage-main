package com.company.project.dao;

import java.util.List;
import java.util.Optional;

import com.company.project.domain.Teacher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepositoryImplementation<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.personalInfo.home.email = :email OR t.work.email = :email")
    Optional<Teacher> findByEmail(@Param("email") String email);

    List<Teacher> findBySchoolId(Long schoolId);

    List<Teacher> findByFirstNameContainingOrLastNameContaining(String key1, String key2);

    @Query("SELECT t FROM Teacher t WHERE " +
            "LOWER(t.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Teacher> searchTeachers(@Param("keyword") String keyword);

    @Query("SELECT s FROM Teacher s LEFT JOIN FETCH s.school")
    List<Teacher> findAllWithSchool();

    @Query("SELECT s FROM Teacher s LEFT JOIN FETCH s.school where s.school.id = :schoolId")
    List<Teacher> findBySchoolIdWithSchool(@Param("schoolId") Long schoolId);

//    @Query("SELECT t FROM Teacher t JOIN FETCH t.students s WHERE s.id = :studentId")
//    List<Teacher> findTeachersByStudentId(@Param("studentId") Long studentId);
//
//    List<Teacher> findBySubject(String subject);
//

//    @Query("SELECT COUNT(t) FROM Teacher t WHERE t.school.id = :schoolId")
//    long countBySchoolId(@Param("schoolId") Long schoolId);

}
