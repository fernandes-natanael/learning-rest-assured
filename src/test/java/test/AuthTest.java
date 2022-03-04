package test;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.xml.XmlPath;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void getPessoa1SWAPI() {
        given()
                .log().all()
                .when()
                .get("https://swapi.dev/api/people/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke Skywalker"));
    }

    @Test
    public void obterClima() {
        given()
                .log().all()
                .queryParam("q", "London,UK")
                .queryParam("appid", "66403d31a943db96efb418fca0e5023b")
                .queryParam("units", "metric")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("London"))
                .body("coord.lon", is(-0.1257F))
                .body("coord.lat", is(51.5085F))
                .body("timezone", is(0));
    }


    @Test
    public void barrarAcessoSenhaInvalida() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void autenticacaoSenhaBasica() {
        given()
                .log().all()
                .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoSenhaBasica_Parametrizada_Challenged() {
        given()
                .log().all()
                .auth().basic("admin", "senha")
                .when()
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoSenhaBasica_Parametrizada() {
        given()
                .log().all()
                /* preemptive faz com que mesmo se a aplicação não requisitar
                 * explicitamente o proeenchimento ele fara o envio*/
                .auth().preemptive().basic("admin", "senha")
                .when()
                .get("https://restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void autenticacaoPorTokenJWT() {
        Map<String, String> login = new HashMap<>();
        login.put("email", "sei@la.com");
        login.put("senha", "123456");

        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("https://barrigarest.wcaquino.me/signin")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("token");
    }

    @Test
    public void obtendoContas_JWT() {
        Map<String, String> login = new HashMap<>();
        login.put("email", "sei@la.com");
        login.put("senha", "123456");

        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("https://barrigarest.wcaquino.me/signin")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("token");

        given()
                .log().all()
                .header("Authorization", "JWT " + token)
                .when()
                .get("https://barrigarest.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
                .body("nome", hasItem("Teste 1"));
    }

    @Test
    public void testandoAplicacaoWeb() {

        String cookie = given()
                .log().all()
                .formParam("email", "sei@la.com")
                .formParam("senha", "123456")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post("https://seubarriga.wcaquino.me/logar")
                .then()
                .log().all()
                .statusCode(200)
                .extract().header("set-cookie")
//                .split("=")[1].split(";")[0] // essa linha não faz parte do Rest Assured, e apenas tratamento da String cookie
                /** meio inutil esse tratamento de string pq ele readiciona logo abaixo novamente, entao deixei comentado */;

        String corpo = given()
                .log().all()
//                .cookie("connect.sid", cookie)
                .cookie(cookie)
                .when()
                .get("https://seubarriga.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
                .body("html.body.table.tbody.tr[0].td[0]", is("Teste 1"))
                .extract().body().asString();

        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, corpo);
        System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
    }

}
