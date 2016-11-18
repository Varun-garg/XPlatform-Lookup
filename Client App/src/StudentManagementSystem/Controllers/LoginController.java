package StudentManagementSystem.Controllers;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sun.rmi.runtime.Log;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginController  implements Initializable {

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

    final static String LOGIN_SUCCESS = "succeeded";
    final static String LOGIN_FAIL = "failed";
    final static String LOGIN_EXCEPTION = "exception_error";

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
                    displayMethods.MenuDisplay(CurrentStage);
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
