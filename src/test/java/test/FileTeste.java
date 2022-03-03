package test;

import org.junit.Test;
import java.io.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FileTeste {
    @Test
    public void obrigarEnvioArquivo() {
        given()
                .log().all()
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(404)
                .body("error", is("Arquivo não enviado"));
    }

    @Test
    public void uploadArquivoNormal() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/test/resources/arquivoTeste.pdf"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(5000L))// neste caso o upload deve levar até 5 segundos
                .body("name", is("arquivoTeste.pdf"));
    }

    /** Como não tenhum um arquivo enorme deixarei aqui apenas um exemplo de código,
    * pois aqui também há o exemplo de implementação de tempo limite de tentativas

    @Test
    public void barrandoUploadArquivoEnorme() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/test/resources/arquivoGrande.pdf"))
                .when()
                .post("https://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .time(lessThan(5000L))// neste caso o upload deve levar até 5 segundos
                .statusCode(413);
    }

    **********************************************************************************/

    @Test
    public void downloadArquivoNormal() throws IOException {
        byte[] image = given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/download")
                .then()
                .log().all()
                .statusCode(200)
                .extract().asByteArray();

        File imagem = new File("src/test/resources/img.jpeg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();
        assertTrue(100000L > imagem.length());
    }


}
