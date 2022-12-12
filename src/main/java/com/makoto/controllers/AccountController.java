package com.makoto.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.models.StudentViewModel;
import com.makoto.models.TeacherViewModel;
import com.makoto.services.AccountService;
import com.makoto.utility.StringHelper;
import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class AccountController {
        public static final String prefixRoute = "/api/v1";
        public static final String apiTag = "AccountAPI";
        private AccountService accountService;

        @Inject
        public AccountController(AccountService service) {
                this.accountService = service;
        }

        @OpenApi(summary = "Get all teacher", operationId = "getAllTeachers",
                        methods = HttpMethod.GET, tags = apiTag, path = prefixRoute + "/teacher",
                        responses = {@OpenApiResponse(status = "200",
                                        description = "All teachers info",
                                        content = @OpenApiContent(
                                                        from = StudentViewModel[].class))})
        public void getAllTeachers(Context ctx) throws Exception {
                ctx.json(this.accountService.getAllTeacher());
        }

        @OpenApi(summary = "create teacher", operationId = "createTeacher",
                        methods = HttpMethod.POST, tags = apiTag, path = prefixRoute + "/teacher",
                        requestBody = @OpenApiRequestBody(
                                        content = {@OpenApiContent(from = TeacherViewModel.class)}),
                        responses = {@OpenApiResponse(status = "201")})
        public void createTeacherAccount(Context ctx) throws Exception {
                var teacher = validateTeacherViewModel(ctx);
                this.accountService.createTeacher(teacher);
                ctx.status(201);
        }

        @OpenApi(summary = "Get all students", operationId = "getAllStudents",
                        methods = HttpMethod.GET, tags = apiTag, path = prefixRoute + "/student",
                        responses = {@OpenApiResponse(status = "200",
                                        description = "All students info",
                                        content = @OpenApiContent(
                                                        from = StudentViewModel[].class))})
        public void getAllStudents(Context ctx) throws Exception {
                ctx.json(this.accountService.getAllStudent());
        }

        @OpenApi(summary = "create student", operationId = "createStudent",
                        methods = HttpMethod.POST, tags = apiTag, path = prefixRoute + "/student",
                        requestBody = @OpenApiRequestBody(
                                        content = {@OpenApiContent(from = StudentViewModel.class)}),
                        responses = {@OpenApiResponse(status = "201")})
        public void createStudentAccount(Context ctx) throws Exception {
                var student = validateStudentViewModel(ctx);
                this.accountService.createStudent(student);
                ctx.status(201);
        }

        public TeacherViewModel validateTeacherViewModel(Context ctx) {
                var validator = ctx.bodyValidator(TeacherViewModel.class);
                validator.check(x -> !StringHelper.isNullOrWhitespace(x.teacher_id),
                                "must have teacher id")
                                .check(x -> x.teacher_id.length() > 6, "illegal teacher id")
                                .check(x -> !StringHelper.isNullOrWhitespace(x.name),
                                                "must have name");
                return validator.get();
        }

        public StudentViewModel validateStudentViewModel(Context ctx) {
                var validator = ctx.bodyValidator(StudentViewModel.class);
                validator.check(x -> !StringHelper.isNullOrWhitespace(x.student_id),
                                "must have student id")
                                .check(x -> x.student_id.length() > 6, "illegal student id")
                                .check(x -> !StringHelper.isNullOrWhitespace(x.name),
                                                "must have name");
                return validator.get();
        }
}
