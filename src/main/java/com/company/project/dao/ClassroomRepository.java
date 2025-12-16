package com.company.project.dao;

import java.util.List;

import com.company.project.domain.Classroom;
import com.company.project.vo.ClassroomDetailRecord;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepositoryImplementation<Classroom, Long> {
    @Query(name = "Classroom.findAllWithNames")
    List<ClassroomDetailRecord> findAllWithNames();

    @Query(name = "Classroom.findByGradeIdWithNames")
    List<ClassroomDetailRecord> findByGradeIdWithNames(Long schoolId);

//    @Modifying
//    @Query("DELETE FROM #{#entityName} e WHERE e.id = :id")
//    int deleteByIdWithCount(@Param("id") ID id);
//
//    @Modifying
//    @Query("DELETE FROM #{#entityName} e WHERE e.id IN :ids")
//    int deleteByIdsWithCount(@Param("ids") List<ID> ids);
}
