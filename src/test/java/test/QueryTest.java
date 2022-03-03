package test;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QueryTest {

    @Test
    public void enviandoValorViaQuery_XML() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/v2/users?format=xml")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(containsString("utf-8"));

    }

    @Test
    public void enviandoValorViaQuery_JSON() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/v2/users?format=json")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentType(containsString("utf-8"));

    }

    @Test
    public void enviandoValorViaQuery_XMLParametrizado() {
        given()
                .log().all()
                .queryParam("format", "xml")
                .when()
                .get("https://restapi.wcaquino.me/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(containsString("utf-8"));
    }

    @Test
    public void enviandoValorViaHeader() {
        /*Diferenca entre .contentType vs .accept
         * .accept: usado quando eu quero dizer exatamente o que virá da resposta.
         * */
        given()
                .log().all()
                .accept(ContentType.JSON)
                .when()
                .get("https://restapi.wcaquino.me/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentType(containsString("utf-8"));
    }

    @Test
    public void buscandoPorHTML() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/v2/users")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.body.div.table.tbody.tr.size()", is(3))
                .body("html.body.div.table.tbody.tr[1].td[2]", is("25"));
    }

    @Test
    public void buscandoPorXpath_HTML() {
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/v2/users?format=clean")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(hasXPath("count(//table/tr)", is("4")))// 4 pois é contado a header
                .body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")));
    }
}
