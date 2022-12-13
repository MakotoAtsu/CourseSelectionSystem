package com.makoto.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.makoto.BadRequestException;
import com.makoto.models.CourseViewModel;
import com.makoto.services.TeacherService;
import com.makoto.utility.StringHelper;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.openapi.*;

@Singleton
public class TeacherController implements CrudHandler {
        public static final String prefixRoute = "/api/v1/teacher/course";
        private final String apiTag = "TeacherAPI";
        private TeacherService teacherService;

        @Inject
        public TeacherController(TeacherService service) {
                this.teacherService = service;
        }

        @OpenApi(summary = "Create new course", operationId = "createNewCourse",
                        methods = HttpMethod.POST, tags = apiTag, path = prefixRoute,
                        headers = @OpenApiParam(name = "teacherId", required = true,
                                        type = String.class, example = "T-10001"),
                        requestBody = @OpenApiRequestBody(
                                        content = @OpenApiContent(from = CourseViewModel.class)),
                        responses = {@OpenApiResponse(status = "201")})

        @Override
        public void create(Context ctx) {
                var course = validatCourseViewModel(ctx);
                var teacherId = validateTeacherId(ctx);
                try {
                        this.teacherService.createCourse(teacherId, course);
                        ctx.status(201);
                } catch (BadRequestException e) {
                        ctx.json(new Object() {
                                public String message = e.getMessage();
                        }).status(e.getStatusCode());
                } catch (Exception e) {
                        e.printStackTrace();
                        ctx.status(500).json(new Object() {
                                public String message = "Unknown Error";
                        });
                }
        }


        @OpenApi(summary = "delete a course", operationId = "deleteCourse",
                        methods = HttpMethod.DELETE, tags = apiTag,
                        path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        headers = @OpenApiParam(name = "teacherId", required = true,
                                        type = String.class, example = "T-10001"),
                        responses = {@OpenApiResponse(status = "204")})
        @Override
        public void delete(Context ctx, String courseCode) {
                var teacherId = validateTeacherId(ctx);
                try {
                        this.teacherService.deleteCourse(teacherId, courseCode);
                        ctx.status(204);
                } catch (BadRequestException e) {
                        ctx.json(new Object() {
                                public String message = e.getMessage();
                        }).status(e.getStatusCode());
                } catch (Exception e) {
                        e.printStackTrace();
                        ctx.status(500).json(new Object() {
                                public String message = "Unknown Error";
                        });
                }
        }


        @OpenApi(summary = "Get all course", operationId = "getAllCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute,
                        headers = @OpenApiParam(name = "teacherId", required = true,
                                        type = String.class, example = "T-10001"),
                        responses = {@OpenApiResponse(status = "200")})
        @Override
        public void getAll(Context ctx) {
                var teacherId = validateTeacherId(ctx);
                try {
                        ctx.json(this.teacherService.getAllCourseByOwner(teacherId));
                } catch (BadRequestException e) {
                        ctx.json(new Object() {
                                public String message = e.getMessage();
                        }).status(e.getStatusCode());
                } catch (Exception e) {
                        e.printStackTrace();
                        ctx.status(500).json(new Object() {
                                public String message = "Unknown Error";
                        });
                }
        }

        @OpenApi(summary = "get one course", operationId = "getOneCourse", methods = HttpMethod.GET,
                        tags = apiTag, path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = String.class,
                                        required = true, description = "The course code")},
                        headers = @OpenApiParam(name = "teacherId", required = true,
                                        type = String.class, example = "T-10001"),
                        responses = {@OpenApiResponse(status = "200")})
        @Override
        public void getOne(Context ctx, String courseCode) {
                try {
                        ctx.json(this.teacherService.getCourseInfo(courseCode));
                } catch (BadRequestException e) {
                        ctx.json(new Object() {
                                public String message = e.getMessage();
                        }).status(e.getStatusCode());
                } catch (Exception e) {
                        e.printStackTrace();
                        ctx.status(500).json(new Object() {
                                public String message = "Unknown Error";
                        });
                }
        }


        @OpenApi(summary = "update one course", operationId = "updateCourse",
                        methods = HttpMethod.PATCH, tags = apiTag,
                        path = prefixRoute + "/{courseCode}",
                        pathParams = {@OpenApiParam(name = "courseCode", type = Integer.class,
                                        required = true, description = "The course code")},
                        headers = @OpenApiParam(name = "teacherId", required = true,
                                        type = String.class, example = "T-10001"),
                        requestBody = @OpenApiRequestBody(
                                        content = @OpenApiContent(from = CourseViewModel.class)),
                        responses = {@OpenApiResponse(status = "204")})
        @Override
        public void update(io.javalin.http.Context ctx, String courseCode) {
                var teacherId = validateTeacherId(ctx);
                var courseInfo = validatCourseViewModel(ctx);
                // courseInfo
                try {
                        this.teacherService.updateCourse(teacherId, courseInfo);
                        ctx.status(204);
                } catch (BadRequestException e) {
                        ctx.json(new Object() {
                                public String message = e.getMessage();
                        }).status(e.getStatusCode());
                } catch (Exception e) {
                        e.printStackTrace();
                        ctx.status(500).json(new Object() {
                                public String message = "Unknown Error";
                        });
                }
        }


        private CourseViewModel validatCourseViewModel(Context ctx) {
                var validator = ctx.bodyValidator(CourseViewModel.class);
                validator.check(x -> !StringHelper.isNullOrWhitespace(x.name), "must have name")
                                .check(x -> x.amount > 0, "amount must > 0")
                                .check(x -> !StringHelper.isNullOrWhitespace(x.courseCode),
                                                "must have code")
                                .check(x -> !StringHelper.isNullOrWhitespace(x.description),
                                                "must have description")
                                .check(x -> !StringHelper.isNullOrWhitespace(x.semester),
                                                "must have semester");

                var course = validator.get();
                return course;
        }

        private String validateTeacherId(Context ctx) {
                var teacherId = ctx.headerAsClass("teacherId", String.class)
                                .check(x -> !StringHelper.isNullOrWhitespace(x),
                                                "must have teacher id")
                                .check(x -> x.length() > 6, "illegal teacher id").get();
                return teacherId;
        }

}
