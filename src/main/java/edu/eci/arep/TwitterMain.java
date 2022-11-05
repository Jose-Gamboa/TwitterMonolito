package edu.eci.arep;

import edu.eci.arep.collection.LogServiceImpl;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

import static spark.Spark.*;

public class TwitterMain {

    private static LogServiceImpl logService = new LogServiceImpl();

    public static void main(String... args) {
        port(getPort());
        staticFileLocation("/public");
        post("/tweet", (request, response) -> getFront(request,response) );

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static String getFront(spark.Request request, spark.Response response) throws Exception {
        String tweet = request.queryParams("tweet");
        System.out.println("Este es el tweet " + tweet);
        if (tweet.length() >= 140) {
            throw new Exception("Uso maximo de caracteres es 140");
        } else {
            logService.insertDocument(tweet);
            return logService.getCurrent();
        }
    }

    public static String render(String model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
