package constants;

import io.restassured.http.ContentType;

public interface Constants {
    String APP_URL = "https://barrigarest.wcaquino.me";
    Integer APP_PORT = 443; // http => 80, https => 443
    ContentType APP_CONTENT_TYPE = ContentType.JSON;

    String USER_EMAIL = "12341323@gmail.com";
    String USER_PASSWORD = "123456";

    Integer USER_ID = 28405;
    String USER_NAME = "Ednaldo Pereca";

    String INSERT_ACCOUNT_RESETED = "Conta inserida";
    String CHANGE_ACCOUNT_RESETED = "Conta para alterar";
    String SAME_NAME_ACCOUNT_RESETED = "Conta mesmo nome";
    String ACCOUNT_TO_TRANSACTION_RESETED = "Conta para movimentacoes";
    String ACCOUNT_WITH_TRANSACTION_RESETED = "Conta com movimentacao";
    String ACCOUNT_TO_BALANCE_RESETED = "Conta para saldo";

    String TRANSACTION_TO_EXCLUDE_RESETED = "Movimentacao para exclusao";
    String EXPECTED_BALANCE = "534.00";



    String PATH_SIGNIN = "/signin";
    String PATH_BILLS_ACCOUNT = "/contas";
    String PATH_TRANSACTIONS = "/transacoes";
    String PATH_BILLS_BALANCE = "/saldo";
    String PATH_RESET = "/reset";

    Long MAX_TIMOUT = 5000L;

    Short STATUS_SUCCEED = 200;
    Short STATUS_SUCCEED_RESOURCE_CREATED = 201;
    Short STATUS_NO_CONTENT = 204;
    Short STATUS_BAD_REQUEST = 400;
    Short STATUS_UNAUTHORIZED_ACESS = 401;
    Short STATUS_SERVER_CANT_HANDLE = 500;
}
