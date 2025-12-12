package com.conpany.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.dao.SchoolRepository;
import com.company.project.domain.School;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    public void testSaveSchool() {
        School school = new School();
        school.setName("测试学校");
        school.getContactInfo().getAddress().setStreet("测试街道");
        school.getContactInfo().setPhoneNumber("1234567890");

        School savedSchool = schoolRepository.save(school);

        assertThat(savedSchool.getId()).isNotNull();
        assertThat(savedSchool.getName()).isEqualTo("测试学校");
        assertThat(savedSchool.getContactInfo().getAddress().getStreet()).isEqualTo("测试街道");
        assertThat(savedSchool.getContactInfo().getPhoneNumber()).isEqualTo("1234567890");
    }

    @Test
    public void testFindAllSchools() {
        School school1 = new School();
        school1.setName("学校1");

        School school2 = new School();
        school2.setName("学校2");

        schoolRepository.save(school1);
        schoolRepository.save(school2);

        List<School> schools = schoolRepository.findAll();

        assertThat(schools).hasSize(2);
        assertThat(schools).extracting(School::getName).containsExactlyInAnyOrder("学校1", "学校2");
    }

    @Test
    public void testFindSchoolById() {
        School school = new School();
        school.setName("测试学校");
        School savedSchool = schoolRepository.save(school);

        Optional<School> foundSchool = schoolRepository.findById(savedSchool.getId());

        assertThat(foundSchool).isPresent();
        assertThat(foundSchool.get().getId()).isEqualTo(savedSchool.getId());
        assertThat(foundSchool.get().getName()).isEqualTo("测试学校");
    }

    @Test
    public void testDeleteSchool() {
        School school = new School();
        school.setName("待删除学校");
        School savedSchool = schoolRepository.save(school);

        schoolRepository.deleteById(savedSchool.getId());

        Optional<School> deletedSchool = schoolRepository.findById(savedSchool.getId());
        assertThat(deletedSchool).isNotPresent();
    }
}