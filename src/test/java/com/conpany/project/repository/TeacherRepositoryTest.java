package com.conpany.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import com.company.project.dao.TeacherRepository;
import com.company.project.domain.Teacher;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void testSaveTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("张");
        teacher.setLastName("老师");
        teacher.getPersonalInfo().getHome().setEmail("zhangsan@example.com");
        teacher.getPersonalInfo().getHome().setPhoneNumber("13800138000");

        Teacher savedTeacher = teacherRepository.save(teacher);

        assertThat(savedTeacher.getId()).isNotNull();
        assertThat(savedTeacher.getFirstName()).isEqualTo("张");
        assertThat(savedTeacher.getLastName()).isEqualTo("老师");
        assertThat(savedTeacher.getPersonalInfo().getHome().getEmail()).isEqualTo("zhangsan@example.com");
        assertThat(savedTeacher.getPersonalInfo().getHome().getPhoneNumber()).isEqualTo("13800138000");
    }

    @Test
    public void testFindAllTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("李");
        teacher1.setLastName("四");
        teacher1.getPersonalInfo().getHome().setEmail("lisi@example.com");

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("王");
        teacher2.setLastName("五");
        teacher2.getPersonalInfo().getHome().setEmail("wangwu@example.com");

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherRepository.findAll();

        assertThat(teachers).hasSize(2);
        assertThat(teachers).extracting(t -> t.getFullName())
                .containsExactlyInAnyOrder("李 四", "王 五");
    }

    @Test
    public void testFindTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("赵");
        teacher.setLastName("六");
        teacher.getPersonalInfo().getHome().setEmail("zhaoliu@example.com");
        Teacher savedTeacher = teacherRepository.save(teacher);

        Optional<Teacher> foundTeacher = teacherRepository.findById(savedTeacher.getId());

        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getId()).isEqualTo(savedTeacher.getId());
        assertThat(foundTeacher.get().getFullName()).isEqualTo("赵六");
    }

    @Test
    public void testDeleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("钱");
        teacher.setLastName("七");
        teacher.getPersonalInfo().getHome().setEmail("qianqi@example.com");
        Teacher savedTeacher = teacherRepository.save(teacher);

        teacherRepository.deleteById(savedTeacher.getId());

        Optional<Teacher> deletedTeacher = teacherRepository.findById(savedTeacher.getId());
        assertThat(deletedTeacher).isNotPresent();
    }
}
