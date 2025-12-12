package com.conpany.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.dao.GradeRepository;
import com.company.project.dao.SchoolRepository;
import com.company.project.domain.Grade;
import com.company.project.domain.School;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class GradeRepositoryTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void testSaveGrade() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("一年级");
        grade.setSchool(savedSchool);

        Grade savedGrade = gradeRepository.save(grade);

        assertThat(savedGrade.getId()).isNotNull();
        assertThat(savedGrade.getName()).isEqualTo("一年级");
        assertThat(savedGrade.getSchool().getId()).isEqualTo(savedSchool.getId());
    }

    @Test
    public void testFindAllGrades() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade1 = new Grade();
        grade1.setName("一年级");
        grade1.setSchool(savedSchool);

        Grade grade2 = new Grade();
        grade2.setName("二年级");
        grade2.setSchool(savedSchool);

        gradeRepository.save(grade1);
        gradeRepository.save(grade2);

        List<Grade> grades = gradeRepository.findAll();

        assertThat(grades).hasSize(2);
        assertThat(grades).extracting(Grade::getName).containsExactlyInAnyOrder("一年级", "二年级");
    }

    @Test
    public void testFindGradeById() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("三年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        Optional<Grade> foundGrade = gradeRepository.findById(savedGrade.getId());

        assertThat(foundGrade).isPresent();
        assertThat(foundGrade.get().getId()).isEqualTo(savedGrade.getId());
        assertThat(foundGrade.get().getName()).isEqualTo("三年级");
    }

    @Test
    public void testDeleteGrade() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Grade grade = new Grade();
        grade.setName("四年级");
        grade.setSchool(savedSchool);
        Grade savedGrade = gradeRepository.save(grade);

        gradeRepository.deleteById(savedGrade.getId());

        Optional<Grade> deletedGrade = gradeRepository.findById(savedGrade.getId());
        assertThat(deletedGrade).isNotPresent();
    }
}