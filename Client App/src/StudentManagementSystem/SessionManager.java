package StudentManagementSystem;

/**
 * Created by varun on 27/09/2016.
 */
public class SessionManager {
    public static final int LOGGED_IN = 1;
    public static final int LOGGED_OUT = 0;

    private static SessionManager ourInstance = new SessionManager();

    private static String cookie;
    private String Username;
    private String Password;
    private String UserType;
    private String FullName;
    private String RollNumber;
    private String StudentRollNo;
    private String StudentFullName;
    private int LoginStatus = 0;

    private SessionManager() {
        LoginStatus = LOGGED_OUT;
        StudentRollNo = null;
    }

    public int getLoginStatus() {
        return LoginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        LoginStatus = loginStatus;
    }

    public static SessionManager getInstance() {
        return ourInstance;
    }

    public static String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        SessionManager.cookie = cookie;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public void setRollNumber(String rollNumber) {
        RollNumber = rollNumber;
    }

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

    public String getStudentRollNo() {
        return StudentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        StudentRollNo = studentRollNo;
    }

    public String getStudentFullName() {
        return StudentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }
}
