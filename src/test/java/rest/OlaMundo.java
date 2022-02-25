package rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class OlaMundo {
    public static void main(String[] args) {
        System.out.println(RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola").getBody().asString());
    }
}
