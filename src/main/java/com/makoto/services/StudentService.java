package com.makoto.services;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.domain.entities.Course;
import com.makoto.domain.entities.CourseRegister;
import com.makoto.models.CourseViewModel;
import com.makoto.models.StudentViewModel;
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

    public Collection<CourseViewModel> getAllCourse() throws SQLException {
        var models = this.courseRepo.getAll().stream().map(x -> convertToViewModel(x)).toList();
        return models;
    }

    public CourseViewModel getCourseDetail(String code) throws SQLException, BadRequestException {
        var course = this.courseRepo.findByCode(code);
        if (course == null)
            throw new BadRequestException(400, "course not exists");
        var model = convertToViewModel(course);
        return model;
    }

    public void registerCourse(String studentId, String courseCode)
            throws SQLException, BadRequestException {
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
    }

    public void unregisterCourse(String studentId, String courseCode)
            throws SQLException, BadRequestException {
        var student = this.studentRepo.findByStudentId(studentId);
        if (student == null)
            throw new BadRequestException(400, "student not exists");
        var registerCourse = student.getRegisterCourse().stream()
                .filter(x -> x.getCourse().getCourseCode().equals(courseCode)).findFirst()
                .orElse(null);
        if (registerCourse != null)
            this.registerRepo.deleteById(registerCourse.getId());

    }

    public Collection<CourseViewModel> getRegisterCourse(String studentId)
            throws SQLException, BadRequestException {
        var student = this.studentRepo.findByStudentId(studentId);
        if (student == null)
            throw new BadRequestException(400, "student not exists");
        var models = this.registerRepo.getAll().stream()
                .filter(x -> x.getStudent().getId() == student.getId()).map(x -> x.getCourse())
                .map(x -> convertToViewModel(x)).toList();
        return models;
    }

    private CourseViewModel convertToViewModel(Course course) {
        var model = new CourseViewModel();
        model.courseCode = course.getCourseCode();
        model.name = course.getName();
        model.teacher_id = course.getTeacher().getTeacherId();
        model.amount = course.getAmount();
        model.description = course.getDescription();
        return model;
    }

}
