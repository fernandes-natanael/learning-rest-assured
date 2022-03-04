package test;

import base.Core;
import constants.DataTransaction;
import constants.Messages;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import models.Transaction;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import utils.Util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTest extends Core implements DataTransaction {
    public static String contaName = "Conta " + System.nanoTime();
    public static Integer idConta;
    public static Integer idTransaction;

    /**
     * Author: Natanael Filho
     * Description: Try to GET the Bills without send the necessary token crendentials, so the system needs to retur 401, unauthorized access
     */

    @Test
    public void tc01_insertBillSuccessfully() {
        idConta = given()
                .body("{ \"nome\": \"" + contaName +"\" }")
                .when()
                .post(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_SUCCEED_RESOURCE_CREATED)
                .extract().path("id");
    }

    @Test
    public void tc02_changeBillSuccessfully() {

        given()
                .body("{ \"nome\": \"" + contaName +" Alterada\" }")
                .when()
                .put(PATH_BILLS_ACCOUNT + "/" + idConta)
                .then()
                .statusCode(STATUS_SUCCEED)
                ;
    }

    @Test
    public void tc03_denyInsertBillAccountWithSameName() {

        given()
                .body("{ \"nome\": \"" + contaName +" Alterada\" }")
                .when()
                .post(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_BAD_REQUEST)
                .body("error", is(Messages.ACCOUT_BILL_ALREADY_EXISTS.getMessage()));
    }

    @Test
    public void tc04_insertTransactionSuccessfully() {
        Transaction transaction = getValidTransaction();

        idTransaction = given()
                .body(transaction)
                .when()
                .post(PATH_TRANSACTIONS)
                .then()
                .statusCode(STATUS_SUCCEED_RESOURCE_CREATED)
                .extract().path("id");
    }

    @Test
    public void tc05_denyCreateEmptyTransaction() {

        given()
                .body("{}")
                .when()
                .post(PATH_TRANSACTIONS)
                .then()
                .statusCode(STATUS_BAD_REQUEST)
                .body("$", hasSize(8))
                .body("msg", hasItems(
                        "Data da Movimentação é obrigatório",
                        "Data do pagamento é obrigatório",
                        "Descrição é obrigatório",
                        "Interessado é obrigatório",
                        "Valor é obrigatório",
                        "Valor deve ser um número",
                        "Conta é obrigatório",
                        "Situação é obrigatório"
                ));
    }

    @Test
    public void tc06_insertInvalidTransaction() {
        Transaction transaction = getValidTransaction();
        transaction.setData_transacao(Util.getDataDaysDifference(15));
        given()
                .body(transaction)
                .when()
                .post(PATH_TRANSACTIONS)
                .then()
                .statusCode(STATUS_BAD_REQUEST)
                .body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    public void tc07_denyDeleteBillAccountWithActiveTransaction() {

        given()
                .when()
                .delete(PATH_BILLS_ACCOUNT +"/" + idConta)
                .then()
                .statusCode(STATUS_SERVER_CANT_HANDLE)
                .body("constraint", is("transacoes_conta_id_foreign"));
    }

    @Test
    public void tc08_calculateAccountBalance() {

        given()
                .when()
                .get(PATH_BILLS_BALANCE)
                .then()
                .statusCode(STATUS_SUCCEED)
                .body("find{it.conta_id == " + idConta + " }.saldo", is("100.00"));
    }

    @Test
    public void tc09_deleteTransaction() {

        given()
                .when()
                .delete(PATH_TRANSACTIONS + "/" + idTransaction)
                .then()
                .statusCode(STATUS_NO_CONTENT);
    }

    @Test
    public void tc10_denyTokenAccessToAPI() {
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification;
        requestSpecification.removeHeader("Authorization");
        given()
                .when()
                .get(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_UNAUTHORIZED_ACESS);
    }

    private Transaction getValidTransaction() {
        Transaction transaction = new Transaction();
        transaction.setUsuario_id(USER_ID);
        transaction.setConta_id(idConta);
        transaction.setDescricao(DESCRICAO);
        transaction.setEnvolvido(INVOLVED);
        transaction.setTipo(TYPE);
        transaction.setData_transacao(Util.getDataDaysDifference(-1));
        transaction.setData_pagamento(Util.getDataDaysDifference(5));
        transaction.setValor(VALUE);
        transaction.setStatus(true);
        return transaction;
    }

}
