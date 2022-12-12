package com.makoto.controllers;

import com.google.inject.Singleton;
import com.makoto.models.StudentViewModel;
import com.makoto.models.TeacherViewModel;
import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class AccountController {
    public static final String prefixRoute = "/api/v1";
    public static final String apiTag = "AccountAPI";

    @OpenApi(summary = "Get all teacher", operationId = "getAllTeachers", methods = HttpMethod.GET,
            tags = apiTag, path = prefixRoute + "/teacher",
            responses = {@OpenApiResponse(status = "200", description = "All teachers info",
                    content = @OpenApiContent(from = StudentViewModel[].class))})
    public void getAllTeachers(Context ctx) {

    }

    @OpenApi(summary = "create teacher", operationId = "createTeacher", methods = HttpMethod.POST,
            tags = apiTag, path = prefixRoute + "/teacher",
            requestBody = @OpenApiRequestBody(
                    content = {@OpenApiContent(from = TeacherViewModel.class)}),
            responses = {@OpenApiResponse(status = "204")})
    public void createTeacherAccount(Context ctx) {
        var body = ctx.bodyAsClass(TeacherViewModel.class);

    }

    @OpenApi(summary = "Get all students", operationId = "getAllStudents", methods = HttpMethod.GET,
            tags = apiTag, path = prefixRoute + "/teacher",
            responses = {@OpenApiResponse(status = "200", description = "All students info",
                    content = @OpenApiContent(from = StudentViewModel[].class))})
    public void getAllStudents(Context ctx) {

    }

    @OpenApi(summary = "create student", operationId = "createStudent", methods = HttpMethod.POST,
            tags = apiTag, path = prefixRoute + "/student",
            requestBody = @OpenApiRequestBody(
                    content = {@OpenApiContent(from = StudentViewModel.class)}),
            responses = {@OpenApiResponse(status = "204")})
    public void createStudentAccount(Context ctx) {


    }
}
