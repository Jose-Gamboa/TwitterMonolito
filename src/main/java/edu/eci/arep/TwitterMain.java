package edu.eci.arep;

import edu.eci.arep.collection.LogServiceImpl;

import static spark.Spark.*;

public class TwitterMain {


    public static void main(String... args){
        port(getPort());
        staticFileLocation("/public");
        post("/twitter", (request, response) -> "hola");


    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static void getFront(spark.Request request, spark.Response response) throws Exception {
        String cadena = request.queryParams("tweet");
        if(cadena.length() >= 140){
            throw new Exception("Uso maximo de caracteres es 140");
        }else{

        }
    }


}
