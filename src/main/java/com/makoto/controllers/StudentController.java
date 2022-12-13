package com.makoto.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.models.CourseViewModel;
import com.makoto.services.StudentService;
import com.makoto.utility.StringHelper;
import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class StudentController {
        public static final String prefixRoute = "/api/v1/student";
        private final String apiTag = "StudentAPI";
        private StudentService studentService;

        @Inject
        public StudentController(StudentService service) {
                this.studentService = service;
        }

        @OpenApi(summary = "Get all course", operationId = "getAllCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute + "/course",
                        headers = @OpenApiParam(name = "studentId", required = true,
                                        type = String.class, example = "S-10001"),
                        responses = {@OpenApiResponse(status = "200",
                                        content = @OpenApiContent(from = CourseViewModel[].class))})
        public void getAllCourse(Context ctx) throws Exception {
                ctx.json(studentService.getAllCourse());
        }

        @OpenApi(summary = "Get course detail", operationId = "getCourseDetail",
                        methods = HttpMethod.GET, tags = apiTag,
                        path = prefixRoute + "/course/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = String.class,
                                        required = true, description = "The course code")},
                        headers = @OpenApiParam(name = "studentId", required = true,
                                        type = String.class, example = "S-10001"),
                        responses = {@OpenApiResponse(status = "200",
                                        content = @OpenApiContent(from = CourseViewModel[].class))})
        public void getCourseDetail(Context ctx) throws Exception {
                var code = ctx.pathParamAsClass("courseCode", String.class).get();
                var course = this.studentService.getCourseDetail(code);
                if (course == null)
                        ctx.status(404);
                else
                        ctx.json(course);
        }

        @OpenApi(summary = "Get register course", operationId = "getRegisterCourse",
                        methods = HttpMethod.GET, tags = apiTag,
                        path = prefixRoute + "/course/register",
                        headers = @OpenApiParam(name = "studentId", required = true,
                                        type = String.class, example = "S-10001"),
                        responses = {@OpenApiResponse(status = "200",
                                        content = @OpenApiContent(from = CourseViewModel.class))})
        public void getAlreadyRegisterCourse(Context ctx) throws Exception {
                var studentId = validateStudentId(ctx);
                ctx.json(studentService.getRegisterCourse(studentId));
        }

        @OpenApi(summary = "Register course", operationId = "registerCourse",
                        methods = HttpMethod.POST, tags = apiTag,
                        path = prefixRoute + "/course/register",
                        headers = @OpenApiParam(name = "studentId", required = true,
                                        type = String.class, example = "S-10001"),
                        requestBody = @OpenApiRequestBody(
                                        content = @OpenApiContent(from = CourseViewModel.class)),
                        responses = {@OpenApiResponse(status = "204")})
        public void registerCourse(Context ctx) throws Exception {
                var studentId = validateStudentId(ctx);
                var course = ctx.bodyValidator(CourseViewModel.class)
                                .check(x -> !StringHelper.isNullOrWhitespace(x.courseCode),
                                                "must have course code")
                                .get();
                this.studentService.registerCourse(studentId, course.courseCode);
        }

        @OpenApi(summary = "Unregister course", operationId = "unregisterCourse",
                        methods = HttpMethod.DELETE, tags = apiTag,
                        path = prefixRoute + "/course/register/{courseCode}",
                        headers = @OpenApiParam(name = "studentId", required = true,
                                        type = String.class, example = "S-10001"),
                        pathParams = {@OpenApiParam(name = "courseCode", type = String.class,
                                        required = true, description = "The course code")},
                        responses = {@OpenApiResponse(status = "204")})
        public void unregisterCourse(Context ctx) throws Exception {
                var studentId = validateStudentId(ctx);
                var code = ctx.pathParamAsClass("courseCode", String.class)
                                .check(c -> !StringHelper.isNullOrWhitespace(c), "must have code")
                                .get();
                this.studentService.unregisterCourse(studentId, code);
                ctx.status(204);
        }

        private String validateStudentId(Context ctx) {
                var id = ctx.headerAsClass("studentId", String.class)
                                .check(x -> !StringHelper.isNullOrWhitespace(x), "must have id")
                                .check(x -> x.length() > 6, "illegal student id").get();
                return id;

        }

}
