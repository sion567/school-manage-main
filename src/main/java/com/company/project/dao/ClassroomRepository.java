package com.company.project.dao;

import java.util.List;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.Classroom;
import com.company.project.vo.ClassroomDetailRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends BaseRepository<Classroom, Long> {
    @Query(name = "Classroom.findAllWithNames")
    List<ClassroomDetailRecord> findAllWithNames();

    @Query(name = "Classroom.findByGradeIdWithNames")
    List<ClassroomDetailRecord> findByGradeIdWithNames(Long schoolId);
}
