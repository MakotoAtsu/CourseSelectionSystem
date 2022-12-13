package com.makoto.services;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.argThat;
import org.junit.Test;
import org.mockito.Mockito;
import com.makoto.domain.entities.Student;
import com.makoto.domain.entities.Teacher;
import com.makoto.models.StudentViewModel;
import com.makoto.models.TeacherViewModel;
import com.makoto.repositories.*;

public class AccountServiceTest {

    @Test
    public void test_createTeacher_will_call_repo() {
        var teacherRepo = Mockito.mock(TeacherRepository.class);
        var studentRepo = Mockito.mock(StudentRepository.class);
        var service = new AccountService(teacherRepo, studentRepo);
        var model = new TeacherViewModel();
        model.name = "Teacher1";
        model.teacher_id = "T-10001";

        try {
            service.createTeacher(model);


            Mockito.verify(teacherRepo).create(argThat((Teacher t) -> t.getName().equals("Teacher1")
                    && t.getTeacherId().equals("T-10001")));
        } catch (Exception e) {
            fail("should not throw exception");
        }
    }

    @Test
    public void test_createStudent_will_call_repo() {
        var teacherRepo = Mockito.mock(TeacherRepository.class);
        var studentRepo = Mockito.mock(StudentRepository.class);
        var service = new AccountService(teacherRepo, studentRepo);
        var model = new StudentViewModel();
        model.name = "student_1";
        model.student_id = "S-10001";

        try {
            service.createStudent(model);

            Mockito.verify(studentRepo)
                    .create(argThat((Student s) -> s.getName().equals("student_1")
                            && s.getStudentId().equals("S-10001")));
        } catch (Exception e) {
            fail("should not throw exception");
        }
    }
}
