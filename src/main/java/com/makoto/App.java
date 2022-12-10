package com.makoto;

import io.javalin.Javalin;
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
        Javalin.create(config -> {
            var openApiConfiguration = new OpenApiConfiguration();
            openApiConfiguration.getInfo().setTitle("Javalin OpenAPI example");
            config.plugins.register(new OpenApiPlugin(openApiConfiguration));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
        })
                .get("/", ctx -> ctx.redirect("/swagger")) // redirect to swagger-ui
                .start(8000);
    }
}
