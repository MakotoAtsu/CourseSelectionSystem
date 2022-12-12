package com.makoto.services;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.domain.entities.Student;
import com.makoto.domain.entities.Teacher;
import com.makoto.models.StudentViewModel;
import com.makoto.models.TeacherViewModel;
import com.makoto.repositories.StudentRepository;
import com.makoto.repositories.TeacherRepository;

@Singleton
public class AccountService {

    private TeacherRepository teacherRepo;
    private StudentRepository studentRepo;

    @Inject
    public AccountService(TeacherRepository teacherRepo, StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    public void createTeacher(TeacherViewModel model) throws SQLException, BadRequestException {
        var teacher = new Teacher();
        teacher.setTeacherId(model.teacher_id);
        teacher.setName(model.name);
        if (this.teacherRepo.exist(teacher.getTeacherId()))
            throw new BadRequestException(400, "Duplicate teacher_id: " + teacher.getTeacherId());
        teacherRepo.create(teacher);
    }

    public Collection<TeacherViewModel> getAllTeacher() throws SQLException {
        var models = this.teacherRepo.getAll().stream().map(x -> {
            var teacher = new TeacherViewModel();
            teacher.teacher_id = x.getTeacherId();
            teacher.name = x.getName();
            return teacher;
        }).toList();
        return models;
    }

    public void createStudent(StudentViewModel model) throws SQLException, BadRequestException {
        var student = new Student();
        student.setStudentId(model.student_id);
        student.setName(model.name);
        student.setGrade(model.grade);
        if (this.studentRepo.exist(student.getStudentId()))
            throw new BadRequestException(400, "Duplicate teacher_id: " + student.getStudentId());
        this.studentRepo.create(student);
    }

    public Collection<StudentViewModel> getAllStudent() throws SQLException {
        var models = this.studentRepo.getAll().stream().map(x -> {
            var student = new StudentViewModel();
            student.student_id = x.getStudentId();
            student.name = x.getName();
            student.grade = x.getGrade();
            return student;
        }).toList();
        return models;
    }
}
