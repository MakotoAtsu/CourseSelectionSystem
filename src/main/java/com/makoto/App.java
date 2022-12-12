package com.makoto;

import java.sql.SQLException;
import com.google.inject.Guice;
import com.makoto.controllers.AccountController;
import com.makoto.controllers.StudentController;
import com.makoto.controllers.TeacherController;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        var injector = Guice.createInjector();
        Javalin.create(config -> {
            var openApiConfiguration = new OpenApiConfiguration();
            openApiConfiguration.getInfo().setTitle("Javalin OpenAPI example");
            config.plugins.register(new OpenApiPlugin(openApiConfiguration));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
        }).routes(() -> {
            var controller = injector.getInstance(TeacherController.class);
            ApiBuilder.crud(TeacherController.prefixRoute + "/{courseCode}", controller);
        }).routes(() -> {
            var controller = injector.getInstance(StudentController.class);
            ApiBuilder.path("/api/v1/student/course", () -> {
                ApiBuilder.get(controller::getAllCourse);
                ApiBuilder.path("/register", () -> {
                    ApiBuilder.post(controller::registerCourse);
                    ApiBuilder.delete("/{courseCode}", controller::unregisterCourse);
                });
            });
        }).routes(() -> {
            var controller = injector.getInstance(AccountController.class);
            ApiBuilder.path(AccountController.prefixRoute, () -> {
                ApiBuilder.get("/teacher", controller::getAllTeachers);
                ApiBuilder.get("/student", controller::getAllStudents);
                ApiBuilder.post("/teacher", controller::createTeacherAccount);
                ApiBuilder.post("/student", controller::createStudentAccount);
            });
        }).exception(BadRequestException.class, (e, ctx) -> {
            ctx.status(e.getStatusCode()).json(new Object() {
                public String message = e.getMessage();
            });
        }).exception(SQLException.class, (e, ctx) -> {
            ctx.status(503).json(new Object() {
                public String message = "Database is not avaliable now";
            });
        }).exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json(new Object() {
                public String message = "Unknown Error";
            });
            e.printStackTrace();
        }).get("/", ctx -> ctx.redirect("/swagger")) // redirect to swagger-ui
                .start(8000);
    }
}
