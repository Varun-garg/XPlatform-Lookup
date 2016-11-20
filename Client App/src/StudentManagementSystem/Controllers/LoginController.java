package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    final static String LOGIN_SUCCESS = "succeeded";
    final static String LOGIN_FAIL = "failed";
    final static String LOGIN_EXCEPTION = "exception_error";
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private VBox fxml_root, vbox_auth_options, vbox_auth_details;
    @FXML
    private Label current_status;
    @FXML
    private JFXButton login_button;

    public void LoginEvent(ActionEvent event) {

        String username = usernameField.getText();
        String password = passwordField.getText();
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        login_button.setDisable(true);

        Task loginTask = new Task() {
            @Override
            protected String call() throws Exception {
                try {
                    updateMessage("Sending Authentication details to API");
                    WebTarget clientTarget;
                    Client client = ClientBuilder.newClient();
                    clientTarget = client.target(Configuration.API_HOST + "user/login");//?email=" + username + "&password=" + password

                    Form auth_form = new Form();
                    auth_form.param("email", username);
                    auth_form.param("password", password);

                    javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").
                            post(Entity.entity(auth_form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));


                    String response = rawResponse.readEntity(String.class);
                    ObjectMapper mapper = new ObjectMapper();
                    LoginResponse loginResponse = mapper.readValue(response, LoginResponse.class);

                    System.out.println("got message " + loginResponse.getMessage());
                    updateMessage("Receiving data from API");
                    if (loginResponse.getMessage().equals("success")) {
                        SessionManager sessionManager = SessionManager.getInstance();
                        sessionManager.setFullName(username);
                        sessionManager.setLoginStatus(sessionManager.LOGGED_IN);
                        sessionManager.setPassword(password);
                        if (loginResponse.getRollNo().length() > 0)
                            sessionManager.setRollNumber(loginResponse.getRollNo());
                        sessionManager.setUserType(loginResponse.getType());

                        Map<String, NewCookie> tempCookie = rawResponse.getCookies();
                        String responseCookie = tempCookie.toString();
                        responseCookie = responseCookie.substring(11, 54);
                        sessionManager.setCookie(responseCookie);
                        sessionManager.setStudentRollNo(null);

                        updateMessage("Authentication successful");
                        return LOGIN_SUCCESS;
                    } else {
                        updateMessage("Authentication failed, username/password mismatch");
                        return LOGIN_FAIL;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    updateMessage("Login failed, an exception occurred ");
                    return LOGIN_EXCEPTION;
                }

            }
        };

        loginTask.setOnSucceeded(e ->
        {
            System.out.println("Task Succeeded   " + loginTask.getValue());
            String login_state = (String) loginTask.getValue();
            if (login_state == LOGIN_SUCCESS) // here we can use == over equals as when
            // equal both point to same string in memory
            {
                Stage CurrentStage = (Stage) fxml_root.getScene().getWindow();
                DisplayMethods displayMethods = DisplayMethods.getInstance();
                try {
                    displayMethods.MemberHome(CurrentStage);
                } catch (IOException ioException) {
                    throw new UncheckedIOException(ioException);
                }
            }
            usernameField.setDisable(false);
            passwordField.setDisable(false);
            login_button.setDisable(false);
        });

        loginTask.setOnFailed(e ->
        {
            usernameField.setDisable(false);
            passwordField.setDisable(false);
            login_button.setDisable(false);
        });
        current_status.textProperty().bind(loginTask.messageProperty());

        Thread thread = new Thread(loginTask);
        thread.setDaemon(true);
        thread.start();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbox_auth_details.setManaged(false);
        vbox_auth_details.setVisible(false);
    }

    public void show_details(ActionEvent event) {
        vbox_auth_options.setManaged(false);
        vbox_auth_options.setVisible(false);

        vbox_auth_details.setManaged(true);
        vbox_auth_details.setVisible(true);

    }

}
