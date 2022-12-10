package com.makoto;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Javalin.create()
                .get("/", ctx -> ctx.result("OK"))
                .start(8000);
    }
}
