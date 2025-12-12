package com.company.project.dao;

import java.util.List;
import java.util.Optional;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.Gender;
import com.company.project.domain.Student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository  extends BaseRepository<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);

    // 根据性别查询
    List<Student> findByPersonalInfoGender(Gender gender);

//    @Query("SELECT s FROM Student s JOIN FETCH s.teachers t WHERE t.id = :teacherId")
//    List<Student> findStudentsByTeacherId(@Param("teacherId") Long teacherId);
//
//    @Query("SELECT s FROM Student s JOIN FETCH s.school sc WHERE sc.id = :schoolId")
//    List<Student> findStudentsBySchoolId(@Param("schoolId") Long schoolId);
//
//    @Query("SELECT COUNT(s) FROM Student s WHERE s.school.id = :schoolId")
//    long countBySchoolId(@Param("schoolId") Long schoolId);
}
