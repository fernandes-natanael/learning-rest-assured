package test;

import base.Core;
import constants.DataTransaction;
import constants.Messages;
import org.junit.Test;
import utils.BarrigaUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AccountTest extends Core implements DataTransaction {

    @Test
    public void insertBillSuccessfully() {
        given()
                .body("{ \"nome\": \"" + INSERT_ACCOUNT_RESETED + "\" }")
                .when()
                .post(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_SUCCEED_RESOURCE_CREATED);
    }

    @Test
    public void changeBillSuccessfully() {

        given()
                .body("{ \"nome\": \"Conta Alterada\" }")
                .when()
                .put(PATH_BILLS_ACCOUNT + "/" + BarrigaUtils.getIdAccountByName(CHANGE_ACCOUNT_RESETED))
                .then()
                .statusCode(STATUS_SUCCEED)
        ;
    }

    @Test
    public void denyInsertBillAccountWithSameName() {

        given()
                .body("{ \"nome\": \"" + SAME_NAME_ACCOUNT_RESETED + "\" }")
                .when()
                .post(PATH_BILLS_ACCOUNT)
                .then()
                .statusCode(STATUS_BAD_REQUEST)
                .body("error", is(Messages.ACCOUT_BILL_ALREADY_EXISTS.getMessage()));
    }

}
