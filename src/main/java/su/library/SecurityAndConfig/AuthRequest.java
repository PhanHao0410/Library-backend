package su.library.SecurityAndConfig;

public class AuthRequest {

    private String userName;
    private String userPassword;

    public AuthRequest(String userName, String userPassword) {
        super();
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
