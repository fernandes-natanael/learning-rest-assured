package utils;

import io.restassured.RestAssured;


public class BarrigaUtils {
    public static Integer getIdAccountByName(String name) {
        return RestAssured.get("/contas?nome=" + name).then().extract().path("id[0]");
    }

    public static Integer getIdTransactionByName(String description) {
        return RestAssured.get("/transacoes?descricao=" + description).then().extract().path("id[0]");
    }
}
