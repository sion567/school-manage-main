package com.company.project.dao;

import java.util.List;
import java.util.Optional;

import com.company.project.core.dao.BaseRepository;
import com.company.project.domain.School;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends BaseRepository<School, Long> {
    Optional<School> findByName(String name);

//    @Query("SELECT s FROM School s JOIN FETCH s.teachers t WHERE t.id = :teacherId")
//    List<School> findSchoolsByTeacherId(@Param("teacherId") Long teacherId);
}
