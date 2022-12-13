package com.makoto.services;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import com.makoto.BadRequestException;
import com.makoto.repositories.CourseRegisterRepository;
import com.makoto.repositories.CourseRepository;
import com.makoto.repositories.StudentRepository;

public class StudentServiceTest {

    @Test
    public void test_registerCourse_will_throw_ex_if_course_not_exist() {
        var courseRepo = mock(CourseRepository.class);
        var registerRepo = mock(CourseRegisterRepository.class);
        var studentRepo = mock(StudentRepository.class);
        var service = new StudentService(courseRepo, registerRepo, studentRepo);
        try {
            when(courseRepo.findByCode(any())).thenReturn(null);


            service.registerCourse("S-10001", "Course-1");


            fail("should throw ex");

        } catch (BadRequestException e) {
            if (!e.getMessage().equals("course not exists"))
                fail("Wrong ex");
        } catch (Exception e) {
            fail("Should not throw ex");
        }
    }
}
