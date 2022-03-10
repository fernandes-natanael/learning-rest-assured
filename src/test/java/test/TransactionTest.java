package test;

import base.Core;
import constants.DataTransaction;
import models.Transaction;
import org.junit.Test;
import utils.BarrigaUtils;
import utils.DateUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TransactionTest extends Core implements DataTransaction {

    @Test
    public void insertTransactionSuccessfully() {
        Transaction transaction = getValidTransaction();

        given()
                .body(transaction)
                .when()
                .post(PATH_TRANSACTIONS)
                .then()
                .statusCode(STATUS_SUCCEED_RESOURCE_CREATED)
                .body("conta_id", is(transaction.getConta_id()))
                .body("valor", is("100.00"))
                .body("usuario_id", is(transaction.getUsuario_id()))
                .body("tipo", is(transaction.getTipo()))
                .body("envolvido", is(transaction.getEnvolvido()))
                .body("descricao", is(transaction.getDescricao()));
    }

    @Test
    public void denyCreateEmptyTransaction() {

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
    public void insertInvalidDateTransaction() {
        Transaction transaction = getValidTransaction();
        transaction.setData_transacao(DateUtil.getDataDaysDifference(15));
        given()
                .body(transaction)
                .when()
                .post(PATH_TRANSACTIONS)
                .then()
                .statusCode(STATUS_BAD_REQUEST)
                .body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    public void denyDeleteBillAccountWithActiveTransaction() {

        given()
                .when()
                .delete(PATH_BILLS_ACCOUNT +"/" + BarrigaUtils.getIdAccountByName(ACCOUNT_WITH_TRANSACTION_RESETED))
                .then()
                .statusCode(STATUS_SERVER_CANT_HANDLE)
                .body("constraint", is("transacoes_conta_id_foreign"));
    }

    @Test
    public void deleteTransaction() {
        given()
                .when()
                .delete(PATH_TRANSACTIONS + "/" + BarrigaUtils.getIdTransactionByName(TRANSACTION_TO_EXCLUDE_RESETED))
                .then()
                .statusCode(STATUS_NO_CONTENT);
    }

    private Transaction getValidTransaction() {
        Transaction transaction = new Transaction();
        transaction.setUsuario_id(USER_ID);
        transaction.setConta_id(BarrigaUtils.getIdAccountByName(ACCOUNT_TO_TRANSACTION_RESETED));
        transaction.setDescricao(DESCRICAO);
        transaction.setEnvolvido(INVOLVED);
        transaction.setTipo(TYPE);
        transaction.setData_transacao(DateUtil.getDataDaysDifference(-1));
        transaction.setData_pagamento(DateUtil.getDataDaysDifference(5));
        transaction.setValor(VALUE);
        transaction.setStatus(true);
        return transaction;
    }
}
