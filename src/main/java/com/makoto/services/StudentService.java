package com.makoto.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.domain.entities.CourseRegister;
import com.makoto.models.CourseViewModel;
import com.makoto.repositories.CourseRegisterRepository;
import com.makoto.repositories.CourseRepository;
import com.makoto.repositories.StudentRepository;

@Singleton
public class StudentService {

    private CourseRegisterRepository registerRepo;
    private CourseRepository courseRepo;
    private StudentRepository studentRepo;

    @Inject
    public StudentService(CourseRepository courseRepo, CourseRegisterRepository courseRegisterRepo,
            StudentRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.registerRepo = courseRegisterRepo;
        this.studentRepo = studentRepo;
    }

    public Collection<CourseViewModel> getAllCourse() {
        try {
            var models = this.courseRepo.getAll().stream().map(x -> {
                var course = new CourseViewModel();
                course.courseCode = x.getCourseCode();
                course.name = x.getName();
                course.teacher_id = x.getTeacher().getTeacher_id();
                course.amount = x.getAmount();
                return course;
            }).toList();
            return models;
        } catch (SQLException e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    public CourseViewModel getCourseDetail(String code) {
        try {
            var course = this.courseRepo.findByCode(code);
            var model = new CourseViewModel();
            model.courseCode = course.getCourseCode();
            model.name = course.getName();
            model.teacher_id = course.getTeacher().getTeacher_id();
            model.amount = course.getAmount();
            model.description = course.getDescription();
            model.current_register = this.registerRepo.countRegisters(course);
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerCourse(String studentId, String courseCode) throws BadRequestException {
        try {
            var course = this.courseRepo.findByCode(courseCode);
            if (course == null)
                throw new BadRequestException(400, "course not exists");
            var student = this.studentRepo.findByStudentId(studentId);
            if (student == null)
                throw new BadRequestException(400, "student not exists");
            var registerInfo = new CourseRegister();
            registerInfo.setStudent(student);
            registerInfo.setCourse(course);
            this.registerRepo.create(registerInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void unregisterCourse(String studentId, String courseCode) throws BadRequestException {
        try {
            var student = this.studentRepo.findByStudentId(studentId);
            if (student == null)
                throw new BadRequestException(400, "student not exists");
            var registerCourse = student.getRegisterCourse().stream()
                    .filter(x -> x.getCourse().getCourseCode() == courseCode).findFirst()
                    .orElse(null);
            if (registerCourse != null)
                this.registerRepo.deleteById(registerCourse.getId());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
