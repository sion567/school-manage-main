package com.company.project.web.api;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.company.project.core.dto.PageResponse;
import com.company.project.core.dto.Result;
import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.ResourceNotFoundException;
import com.company.project.domain.Classroom;
import com.company.project.domain.Course;
import com.company.project.domain.Grade;
import com.company.project.domain.School;
import com.company.project.domain.Student;
import com.company.project.domain.Teacher;
import com.company.project.dto.*;
import com.company.project.service.ClassroomService;
import com.company.project.service.CourseService;
import com.company.project.service.GradeService;
import com.company.project.service.SchoolService;
import com.company.project.service.StudentService;
import com.company.project.service.TeacherService;

import com.company.project.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// RestController层
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "学校管理", description = "学校相关的API接口")
@OpenAPIDefinition(tags = {
        @Tag(name = "createSchool", description = "Add school"),
        @Tag(name = "updateSchool", description = "Update school"),
        @Tag(name = "deleteSchool", description = "Delete school"),
        @Tag(name = "getSchoolById", description = "Find school"),
        @Tag(name = "getAllSchools", description = "Find All")
})
@RequiredArgsConstructor
public class ApiSchoolController {

    private final SchoolService schoolService;
    private final GradeService gradeService;
    private final ClassroomService classroomService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final StudentService studentService;


    @Operation(summary = "获取学校列表", description = "分页获取学校列表")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功返回用户列表",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/schools")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Result<PageResponse<SchoolVO>>> getAllSchools(
            @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        Page<SchoolVO> schoolPage = schoolService.findAll(PageRequest.of(page, size));
        PageResponse<SchoolVO> response = new PageResponse<>(
                schoolPage.getContent(),
                schoolPage.getTotalElements(),
                schoolPage.getTotalPages(),
                schoolPage.getNumber(),
                schoolPage.getSize(),
                schoolPage.hasNext(),
                schoolPage.hasPrevious()
        );

        return ResponseEntity.ok(Result.success(response));
    }

    @Operation(summary = "获取学校详情")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "学校不存在", content = @Content)
    })
    @GetMapping("/schools/{id}")
    public ResponseEntity<Result<SchoolVO>> getSchoolById(
            @Parameter(description = "学校ID", example = "1", required = true) @PathVariable Long id) {
        SchoolVO school = schoolService.findById(id).orElseThrow(() -> new ResourceNotFoundException("School not found", "school Id:"+id));
        return ResponseEntity.ok(Result.success(school));
    }

    @Operation(summary = "创建学校", description = "创建新的学校")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "School created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Result.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input provided") })
    @PostMapping("/schools")
    public ResponseEntity<Result<SchoolVO>> createSchool(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "School to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = School.class),
                    examples = @ExampleObject(value = "{ \"name\": \"New School\" }")))
            @Valid @RequestBody SchoolCreateDTO dto) {
        SchoolVO createdSchool = schoolService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(createdSchool));


//        try {
//            // 1. 首先处理Spring Validation的验证结果
//            if (bindingResult.hasErrors()) {
//                Map<String, String> errors = new HashMap<>();
//                bindingResult.getFieldErrors().forEach(error ->
//                        errors.put(error.getField(), error.getDefaultMessage())
//                );
//                throw new CustomValidationException("参数验证失败", errors, "PARAMETER_VALIDATION_ERROR");
//            }
//
//            // 2. 然后进行业务逻辑验证
//            businessValidationService.validateUser(user);
//            businessValidationService.validateUserWithCustomLogic(user);
//
//            // 3. 如果都通过，处理业务逻辑
//            // 这里应该是真正的业务处理逻辑
//            return ResponseEntity.ok("用户创建成功");
//
//        } catch (CustomValidationException e) {
//            // 4. 抛出自定义验证异常
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", 400);
//            response.put("message", e.getMessage());
//            response.put("errorCode", e.getErrorCode());
//
//            if (e.getFieldErrors() != null) {
//                response.put("errors", e.getFieldErrors());
//            }
//
//            return ResponseEntity.badRequest().body(response);
//        } catch (Exception e) {
//            // 5. 处理其他异常
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", 500);
//            response.put("message", "服务器内部错误");
//            return ResponseEntity.status(500).body(response);
//        }











    }

    @Operation(summary = "更新学校", description = "根据ID更新学校信息")
    @PutMapping("/schools/{id}")
    public ResponseEntity<SchoolVO> updateSchool(
            @Parameter(description = "学校ID", example = "1", required = true) @PathVariable Long id,
            @Parameter(description = "学校信息", required = true) @Valid @RequestBody SchoolUpdateDTO dto) {
        if (!id.equals(dto.getId())) {
            throw new BusinessException("URL参数ID与DTO中的ID不匹配");
        }
        SchoolVO updateSchool = schoolService.update(dto);
        return ResponseEntity.ok(updateSchool);
    }

    @Operation(summary = "删除学校", description = "根据ID删除学校")
    @DeleteMapping("/schools/{id}")
    public ResponseEntity<Void> deleteSchool(
            @Parameter(description = "学校ID", example = "1", required = true) @PathVariable Long id) {
        schoolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{teacherId}/students/{studentId}")
//    public ResponseEntity<Void> assignStudentToTeacher(@PathVariable Long teacherId,
//                                                       @PathVariable Long studentId) {
//        service.assignStudentToTeacher(teacherId, studentId);
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/{schoolId}/teachers/{teacherId}")
//    public ResponseEntity<Void> addTeacherToSchool(@PathVariable Long schoolId,
//                                                   @PathVariable Long teacherId) {
//        service.addTeacherToSchool(schoolId, teacherId);
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/{schoolId}/students/{studentId}")
//    public ResponseEntity<Void> addStudentToSchool(@PathVariable Long schoolId,
//                                                   @PathVariable Long studentId) {
//        service.addStudentToSchool(schoolId, studentId);
//        return ResponseEntity.ok().build();
//    }

    // 年级API
    @GetMapping("/grades")
    public ResponseEntity<List<GradeVO>> getAllGrades() {
        List<GradeVO> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<GradeVO> getGradeById(@PathVariable Long id) {
        Optional<GradeVO> grade = gradeService.findById(id);
        return grade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/grades")
    public ResponseEntity<GradeVO> createGrade(@RequestBody GradeCreateDTO dto) {
        GradeVO createdGrade = gradeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @PutMapping("/grades/{id}")
    public ResponseEntity<GradeVO> updateGrade(@PathVariable Long id, @RequestBody GradeUpdateDTO dto) {
        GradeVO updatedGrade = gradeService.update(dto);
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 班级API
    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomVO>> getAllClassrooms() {
        List<ClassroomVO> classrooms = classroomService.findAll();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/classrooms/{id}")
    public ResponseEntity<ClassroomVO> getClassroomById(@PathVariable Long id) {
        Optional<ClassroomVO> classroom = classroomService.findById(id);
        return classroom.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/classrooms")
    public ResponseEntity<ClassroomVO> createClassroom(@RequestBody ClassroomCreateDTO dto) {
        ClassroomVO createdClassroom = classroomService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClassroom);
    }

    @PutMapping("/classrooms/{id}")
    public ResponseEntity<ClassroomVO> updateClassroom(@PathVariable Long id, @RequestBody ClassroomUpdateDTO dto) {
        ClassroomVO updatedClassroom = classroomService.update(dto);
        return ResponseEntity.ok(updatedClassroom);
    }

    @DeleteMapping("/classrooms/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 教师API
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherVO>> getAllTeachers() {
        List<TeacherVO> teachers = teacherService.findAll();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherVO> getTeacherById(@PathVariable Long id) {
        Optional<TeacherVO> teacher = teacherService.findById(id);
        return teacher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/teachers")
    public ResponseEntity<TeacherVO> createTeacher(@RequestBody TeacherCreateDTO dto) {
        TeacherVO createdTeacher = teacherService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<TeacherVO> updateTeacher(@PathVariable Long id, @RequestBody TeacherUpdateDTO dto) {
        TeacherVO updatedTeacher = teacherService.update(dto);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 课程API
    @GetMapping("/courses")
    public ResponseEntity<List<CourseVO>> getAllCourses() {
        List<CourseVO> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseVO> getCourseById(@PathVariable Long id) {
        Optional<CourseVO> course = courseService.findById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseVO> createCourse(@RequestBody CourseCreateDTO dto) {
        CourseVO createdCourse = courseService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseVO> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO dto) {
        CourseVO updatedCourse = courseService.update(dto);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 学生API
    @GetMapping("/students")
    public ResponseEntity<List<StudentVO>> getAllStudents() {
        List<StudentVO> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentVO> getStudentById(@PathVariable Long id) {
        Optional<StudentVO> student = studentService.findById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<StudentVO> createStudent(@RequestBody StudentCreateDTO dto) {
        StudentVO createdStudent = studentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentVO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO dto) {
        StudentVO updatedStudent = studentService.update(dto);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}