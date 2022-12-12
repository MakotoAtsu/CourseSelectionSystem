package com.makoto.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.domain.entities.Course;
import com.makoto.domain.entities.Teacher;
import com.makoto.models.CourseViewModel;
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

    public void createCourse(String teacherId, CourseViewModel model) throws BadRequestException {
        try {
            var teacher = getTeacher(teacherId);
            var course = new Course();
            course.setAmount(model.amount);
            course.setCourseCode(model.courseCode);
            course.setDescription(model.description);
            course.setName(model.name);
            course.setSemester(model.semester);
            course.setTeacher(teacher);
            this.courseRepo.create(course);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(CourseViewModel model) throws BadRequestException {
        try {
            var course = this.courseRepo.findByCode(model.courseCode);
            if (course == null)
                throw new BadRequestException(400, "course not exist");
            course.setAmount(model.amount);
            course.setCourseCode(model.courseCode);
            course.setDescription(model.description);
            course.setName(model.name);
            course.setSemester(model.semester);
            this.courseRepo.update(course);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void deleteCourse(String teacherId, String courseCode) throws BadRequestException {
        try {
            var course = this.courseRepo.findByCode(courseCode);
            if (course == null)
                throw new BadRequestException(400, "course not exist");
            if (course.getTeacher().getTeacher_id() != teacherId)
                throw new BadRequestException(401, "Only owner can be delete course");
            this.courseRepo.delete(course);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public CourseViewModel getCourseInfo(String courseCode) throws BadRequestException {
        try {
            var course = this.courseRepo.findByCode(courseCode);
            if (course == null)
                throw new BadRequestException(404, "courseCode: " + courseCode + " not exist");

            var model = convertToViewModel(course);
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<CourseViewModel> getAllCourseByOwner(String teacherId)
            throws BadRequestException {
        try {
            var teacher = this.teacherRepo.findByTeacherId(teacherId);
            if (teacher == null)
                throw new BadRequestException(404, "teacher: " + teacherId + " not exist");
            var course = teacher.getCourses();
            var models = course.stream().map(x -> convertToViewModel(x)).toList();
            return models;
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    private CourseViewModel convertToViewModel(Course course) {
        var model = new CourseViewModel();
        model.amount = course.getAmount();
        model.courseCode = course.getCourseCode();
        model.description = course.getDescription();
        model.name = course.getName();
        model.semester = course.getSemester();
        return model;
    }

    private Teacher getTeacher(String teacherId) throws BadRequestException, SQLException {
        var teacher = teacherRepo.findByTeacherId(teacherId);
        if (teacher == null)
            throw new BadRequestException(404, "teacher: " + teacherId + " not exist");
        return teacher;
    }

}