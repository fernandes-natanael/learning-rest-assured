package test;

import base.Core;
import constants.DataTransaction;
import org.junit.Test;
import utils.BarrigaUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AccountBalanceTest extends Core implements DataTransaction {

    @Test
    public void calculateAccountBalance() {

        given()
                .when()
                .get(PATH_BILLS_BALANCE)
                .then()
                .statusCode(STATUS_SUCCEED)
                .body("find{it.conta_id == " + BarrigaUtils.getIdAccountByName(ACCOUNT_TO_BALANCE_RESETED) + " }.saldo", is(EXPECTED_BALANCE));
    }


}
