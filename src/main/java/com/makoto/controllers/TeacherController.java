package com.makoto.controllers;

import com.google.inject.Singleton;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class TeacherController implements CrudHandler {
        public static final String prefixRoute = "/api/v1/teacher/course";
        private final String apiTag = "TeacherAPI";

        @OpenApi(summary = "Create new course", operationId = "createNewCourse",
                        methods = HttpMethod.POST, tags = apiTag, path = prefixRoute,
                        responses = {@OpenApiResponse(status = "201")})
        @Override
        public void create(Context ctx) {
                // TODO Auto-generated method stub
                ctx.result("create course");
        }


        @OpenApi(summary = "delete a course", operationId = "deleteCourse",
                        methods = HttpMethod.DELETE, tags = apiTag,
                        path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        responses = {@OpenApiResponse(status = "204")})
        @Override
        public void delete(Context ctx, String courseCode) {
                // TODO Auto-generated method stub
                ctx.result("delete course , code : " + courseCode);
        }


        @OpenApi(summary = "Get all course", operationId = "getAllCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute,
                        responses = {@OpenApiResponse(status = "200")})
        @Override
        public void getAll(Context ctx) {
                // TODO Auto-generated method stub
                ctx.result("Get All Coures");
        }

        @OpenApi(summary = "get one course", operationId = "getOneCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        responses = {@OpenApiResponse(status = "200")})
        @Override
        public void getOne(Context ctx, String courseCode) {
                // TODO Auto-generated method stub
                ctx.result("get course detail , code : " + courseCode);
        }


        @OpenApi(summary = "update one course", operationId = "updateCourse",
                        methods = HttpMethod.PATCH, tags = apiTag,
                        path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        responses = {@OpenApiResponse(status = "204")})
        @Override
        public void update(io.javalin.http.Context ctx, String courseCode) {
                // TODO Auto-generated method stub
                ctx.result("Update course , code : " + courseCode);
        }
}
