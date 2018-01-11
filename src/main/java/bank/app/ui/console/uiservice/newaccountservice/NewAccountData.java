package bank.app.ui.console.uiservice.newaccountservice;

public class NewAccountData {
    private String username = "";
    private String password = "";
    private double privateBalance = 0.0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPrivateBalance() {
        return privateBalance;
    }

    public void setPrivateBalance(double privateBalance) {
        this.privateBalance = privateBalance;
    }

}
