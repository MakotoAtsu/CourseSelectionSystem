package com.makoto.controllers;

import com.google.inject.Singleton;

import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class StudentController {
        public static final String prefixRoute = "/api/v1/student";
        private final String apiTag = "StudentAPI";

        @OpenApi(summary = "Get all course", operationId = "getAllCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute + "/course",
                        responses = {@OpenApiResponse(status = "200")})
        // responses = {
        // @OpenApiResponse(status = "200", content = { @OpenApiContent(from =
        // UserViewModel[].class) }) })
        public void getAllCourse(Context ctx) {

        }

        @OpenApi(summary = "Register course", operationId = "registerCourse",
                        methods = HttpMethod.POST, tags = apiTag,
                        path = prefixRoute + "/course/register",
                        responses = {@OpenApiResponse(status = "204")})
        public void registerCourse(Context ctx) {
                ctx.result("register course");
        }

        @OpenApi(summary = "Unregister course", operationId = "unregisterCourse",
                        methods = HttpMethod.DELETE, tags = apiTag,
                        path = prefixRoute + "/course/register/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        responses = {@OpenApiResponse(status = "204")})
        public void unregisterCourse(Context ctx) {
                var code = ctx.pathParamAsClass("courseCode", Integer.class)
                                .check(c -> c > 0, "Course must greater 0")
                                .check(c -> c != null, "must have course code").get();

                ctx.result("unregister course , code : " + code);

        }

}
