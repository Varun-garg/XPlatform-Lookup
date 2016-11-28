package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.MainApplication;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by varun on 17/11/2016.
 */
public class MemberHome implements Initializable {

    static final String FETCH_SUCCESS = "success";
    static final String FETCH_FAIL = "fail";
    @FXML
    AnchorPane anchor_pane;
    @FXML
    JFXDrawer navigation_drawer, students_drawer;
    //@FXML
    //TabPane menu_fx;
    @FXML
    VBox NavigationVBox;
    @FXML
    AnchorPane content;

    TabPane tabPane = null;

    Student students[];
    Task fetchStudentsTask = new Task() {
        @Override
        protected String call() throws Exception {
            try {
                WebTarget clientTarget;
                Client client = ClientBuilder.newClient();
                updateMessage("Fetching students list");
                clientTarget = client.target(Configuration.API_HOST + "data/admin" + "/?format=json");
                javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
                String response = rawResponse.readEntity(String.class);
                ObjectMapper mapper = new ObjectMapper();

                students = mapper.readValue(response, Student[].class);

            } catch (Exception e) {
                System.out.println("got exception in fetchStudentsTask");
                e.printStackTrace();
                return FETCH_FAIL;
            }
            return FETCH_SUCCESS;
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox StudentsVBox;
        try {
            //NavigationVBox = FXMLLoader.load(getClass().getResource("../Layout/NavigationDrawer.fxml"));
            StudentsVBox = FXMLLoader.load(getClass().getResource("../Layout/StudentsDrawer.fxml"));
            //navigation_drawer.setSidePane(NavigationVBox);
//            navigation_drawer.open();
            students_drawer.setSidePane(StudentsVBox);
            //    students_drawer.open();
        } catch (Exception e) {
            System.out.println("caught exception in " + this.getClass().getSimpleName());
            e.printStackTrace();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));
        MenuController menuController;

        try {
            content.getChildren().clear();
            tabPane = fxmlLoader.load();
            content.getChildren().setAll(tabPane);
            //   menuController = fxmlLoader.getController();
        } catch (Exception e) {
            System.out.println(getClass().getSimpleName());
            e.printStackTrace();
        }

//        navigation_drawer.toFront();
        NavigationVBox.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5);");
        students_drawer.toBack();
        StudentsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        tabPane.toBack();

        JFXButton studentsButton = new JFXButton();
        studentsButton.setText("Students");
        studentsButton.setPrefWidth(190);
        studentsButton.setPrefHeight(44);
        studentsButton.setOnAction(e -> {
            if (students_drawer.isShown()) {
                students_drawer.close();

                tabPane.toFront();
            } else {
                students_drawer.open();
                students_drawer.toFront();
                tabPane.toBack();
            }
        });
        NavigationVBox.getChildren().add(studentsButton);

        JFXButton newStudentButton = new JFXButton();
        newStudentButton.setText("New Student");
        newStudentButton.setPrefWidth(190);
        newStudentButton.setPrefHeight(44);
        newStudentButton.setOnAction(e ->
        {
            Utility.DisplayForm("New Student", "StudentForm.fxml");
        });
        NavigationVBox.getChildren().add(newStudentButton);

        JFXButton logoutButton = new JFXButton();
        logoutButton.setText("Log Out");
        logoutButton.setPrefWidth(190);
        logoutButton.setPrefHeight(44);
        logoutButton.setOnAction(e -> {
            Form form = new Form();
            WebTarget clientTarget;
            Client client = ClientBuilder.newClient();
            clientTarget = client.target(Configuration.API_HOST + "user/logout/");

            javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            String response = rawResponse.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            LoginResponse addResponse = null;
            try {
                addResponse = mapper.readValue(response, LoginResponse.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.out.println("got message " + addResponse.getMessage());
            if (addResponse.getMessage().equals("success")) {
                SessionManager sessionManager = SessionManager.getInstance();
                sessionManager.setFullName(null);
                sessionManager.setLoginStatus(0);
                sessionManager.setPassword(null);
                sessionManager.setRollNumber(null);
                sessionManager.setStudentRollNo(null);
                sessionManager.setUserType(null);
                sessionManager.setUsername(null);
                Stage CurrentStage = (Stage) anchor_pane.getScene().getWindow();
                DisplayMethods displayMethods = DisplayMethods.getInstance();
                try {
                    displayMethods.LoginDisplay(CurrentStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } else {
                System.out.println("Cannot Logout at this stage");
                System.out.println(response);
            }
        });
        NavigationVBox.getChildren().add(logoutButton);



        /*HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask.setRate(-1);

        hamburger.addEventFilter(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();

            if (navigation_drawer.isShown()) {
                navigation_drawer.close();
                students_drawer.close();
                menu_fx.toFront();
            } else {
                navigation_drawer.open();
                menu_fx.toBack();
            }
        }); */

        fetchStudentsTask.setOnSucceeded(e -> {

            for (int i = 0; i < students.length; i++) {
                JFXButton student_button = new JFXButton();
                student_button.setText(students[i].getFullName());
                student_button.setPrefWidth(190);
                student_button.setPrefHeight(44);
                final int CurrentIndex = i;
                student_button.setOnAction(f -> {
                    try {
                        SessionManager.getInstance().setFullName(students[CurrentIndex].getFullName());
                        SessionManager.getInstance().setRollNumber(students[CurrentIndex].getRollNo());
                        content.getChildren().clear();
                        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));
                        tabPane = fxmlLoader2.load();
                        content.getChildren().setAll(tabPane);
                        studentsButton.fire();
                    } catch (Exception exception) {
                        System.out.println(getClass().getSimpleName());
                        exception.printStackTrace();
                    }
                });

                StudentsVBox.getChildren().add(student_button);
            }
        });

        Thread thread = new Thread(fetchStudentsTask);
        thread.setDaemon(true);
        thread.start();
    }
}
