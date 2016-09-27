package StudentManagementSystem;

/**
 * Created by varun on 27/09/2016.
 */
public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }

    private String Username;
    private String Password;
    private String UserType;
    private String FullName;

    private int LoginStatus = 0;
    public static final int LOGGED_IN = 1;
    public static final int LOGGED_OUT = 0;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


    public int getLoginStatus()
    {
        return LoginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        LoginStatus = loginStatus;
    }
}
