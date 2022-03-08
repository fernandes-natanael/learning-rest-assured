package request;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestGET {

    @Test
    public void get_TestRequest() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/BookStore/v1/Books/");

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);

        Headers header = response.getHeaders();
        System.out.println("\n\nHeader: " + header);
        ResponseBody body = response.getBody();
        body.prettyPrint();

        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals(7, header.size());
        Assertions.assertTrue(body.asString().contains("\"title\": \"Git Pocket Guide\""));
    }
}
