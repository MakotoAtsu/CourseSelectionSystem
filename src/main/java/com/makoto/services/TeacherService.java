package com.makoto.services;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.domain.entities.Course;
import com.makoto.domain.entities.Teacher;
import com.makoto.models.CourseViewModel;
import com.makoto.repositories.CourseRegisterRepository;
import com.makoto.repositories.CourseRepository;
import com.makoto.repositories.TeacherRepository;

@Singleton
public class TeacherService {

    private CourseRepository courseRepo;
    private TeacherRepository teacherRepo;

    @Inject
    public TeacherService(TeacherRepository teacherRepo, CourseRepository courseRepo) {
        this.teacherRepo = teacherRepo;
        this.courseRepo = courseRepo;
    }

    public void createCourse(String teacherId, CourseViewModel model)
            throws SQLException, BadRequestException {
        var teacher = getTeacher(teacherId);
        var course = new Course();
        course.setAmount(model.amount);
        course.setCourseCode(model.courseCode);
        course.setDescription(model.description);
        course.setName(model.name);
        course.setSemester(model.semester);
        course.setTeacher(teacher);
        this.courseRepo.create(course);
    }

    public void updateCourse(String teacherId, CourseViewModel model)
            throws SQLException, BadRequestException {
        var course = this.courseRepo.findByCode(model.courseCode);
        if (course == null)
            throw new BadRequestException(400, "course not exist");
        if (course.getTeacher().getTeacherId() != teacherId)
            throw new BadRequestException(401, "Only owner can modify course");
        course.setAmount(model.amount);
        course.setCourseCode(model.courseCode);
        course.setDescription(model.description);
        course.setName(model.name);
        course.setSemester(model.semester);
        this.courseRepo.update(course);
    }

    public void deleteCourse(String teacherId, String courseCode)
            throws SQLException, BadRequestException {
        var course = this.courseRepo.findByCode(courseCode);
        if (course == null)
            throw new BadRequestException(400, "course not exist");
        if (course.getTeacher().getTeacherId() != teacherId)
            throw new BadRequestException(401, "Only owner can be delete course");
        this.courseRepo.delete(course);
    }

    public CourseViewModel getCourseInfo(String courseCode)
            throws SQLException, BadRequestException {
        var course = this.courseRepo.findByCode(courseCode);
        if (course == null)
            throw new BadRequestException(404, "courseCode: " + courseCode + " not exist");

        var model = convertToViewModel(course);
        return model;
    }

    public Collection<CourseViewModel> getAllCourseByOwner(String teacherId)
            throws SQLException, BadRequestException {
        var teacher = this.teacherRepo.findByTeacherId(teacherId);
        if (teacher == null)
            throw new BadRequestException(404, "teacher: " + teacherId + " not exist");
        var course = teacher.getCourses();
        var models = course.stream().map(x -> convertToViewModel(x)).toList();
        return models;
    }

    private CourseViewModel convertToViewModel(Course course) {
        var model = new CourseViewModel();
        model.amount = course.getAmount();
        model.courseCode = course.getCourseCode();
        model.description = course.getDescription();
        model.name = course.getName();
        model.semester = course.getSemester();
        model.teacher_id = course.getTeacher().getTeacherId();
        return model;
    }

    private Teacher getTeacher(String teacherId) throws BadRequestException, SQLException {
        var teacher = teacherRepo.findByTeacherId(teacherId);
        if (teacher == null)
            throw new BadRequestException(404, "teacher: " + teacherId + " not exist");
        return teacher;
    }

}
