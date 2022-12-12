package com.makoto.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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

    public void createTeacher(TeacherViewModel model) throws Exception {
        var teacher = new Teacher();
        teacher.setTeacher_id(model.teacher_id);
        teacher.setName(model.name);
        if (this.teacherRepo.exist(teacher.getTeacher_id()))
            throw new Exception("Duplicate teacher_id: " + teacher.getTeacher_id());
        teacherRepo.create(teacher);
    }

    public Collection<TeacherViewModel> getAllTeacher() {
        try {
            var models = this.teacherRepo.getAll().stream().map(x -> {
                var teacher = new TeacherViewModel();
                teacher.teacher_id = x.getTeacher_id();
                teacher.name = x.getName();
                return teacher;
            }).toList();
            return models;
        } catch (SQLException e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    public void createStudent(StudentViewModel model) throws Exception {
        var student = new Student();
        student.setStudentId(model.student_id);
        student.setName(model.name);
        student.setGrade(model.grade);
        if (this.studentRepo.exist(student.getStudentId()))
            throw new Exception("Duplicate teacher_id: " + student.getStudentId());
        this.studentRepo.create(student);
    }

    public Collection<StudentViewModel> getAllStudent() {
        try {
            var models = this.studentRepo.getAll().stream().map(x -> {
                var student = new StudentViewModel();
                student.student_id = x.getStudentId();
                student.name = x.getName();
                student.grade = x.getGrade();
                return student;
            }).toList();
            return models;
        } catch (SQLException e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }
}
