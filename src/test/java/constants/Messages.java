package constants;

public enum Messages {
    ACCOUT_BILL_ALREADY_EXISTS("Já existe uma conta com esse nome!");

    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
