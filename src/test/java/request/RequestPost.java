package request;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestPost {

    @Test
    public void createUserTest() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";

        String user = "{\"userName\": \"rudolfu\", \"password\": \"ABCabc@123456\"}";


        //given()
        RequestSpecification httpRequest = RestAssured.given();
        //.header("Content-Type", ContentType.JSON);
        httpRequest.header("Content-Type", ContentType.JSON);
        httpRequest.body(user);
        //.then
        //.post("/Account/v1/User")
        Response response = httpRequest.request(Method.POST, "/Account/v1/User");

        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assertions.assertEquals(201,  response.getStatusCode());
        Assertions.assertEquals(7, response.getHeaders().size());
        Assertions.assertTrue(body.asString().contains("\"title\": \"Git Pocket Guide\""));
    }
}
