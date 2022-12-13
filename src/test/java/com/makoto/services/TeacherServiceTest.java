package com.makoto.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.Test;
import org.mockito.Mockito;
import com.makoto.domain.entities.Course;
import com.makoto.domain.entities.Teacher;
import com.makoto.repositories.CourseRepository;
import com.makoto.repositories.TeacherRepository;

public class TeacherServiceTest {

    @Test
    public void test_findByTeacerId_will_NOT_call_courseRepo() {
        try {
            var teacherRepo = Mockito.mock(TeacherRepository.class);
            var courseRepo = Mockito.mock(CourseRepository.class);
            var service = new TeacherService(teacherRepo, courseRepo);
            var teacher = new Teacher(1, "T-10001", "Teacher1", Arrays.asList());
            var courses = Arrays.asList(
                    new Course(1, "course-1", "course-name", teacher, 10, "2022-04", "desc"),
                    new Course(2, "course-2", "course-name2", teacher, 10, "2022-04", "desc"));
            teacher.setCourses(courses);
            when(teacherRepo.findByTeacherId("T-10001")).thenReturn(teacher);


            var result = service.getAllCourseByOwner("T-10001");

            Mockito.verify(courseRepo, never()).getAll();
            Mockito.verify(courseRepo, never()).findByCode(any());
            assertTrue("size must equal 2", result.size() == 2);
        } catch (Exception e) {
            fail("should not throw ex");
        }
    }
}
