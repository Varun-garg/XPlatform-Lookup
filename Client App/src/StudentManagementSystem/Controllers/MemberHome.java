package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.MainApplication;
import StudentManagementSystem.Model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
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
    @FXML
    TabPane menu_fx;
    @FXML
    VBox NavigationVBox;
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

//        navigation_drawer.toFront();
        NavigationVBox.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5);");
        students_drawer.toBack();
        StudentsVBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        menu_fx.toBack();

        JFXButton studentsButton = new JFXButton();
        studentsButton.setText("Students");
        studentsButton.setPrefWidth(190);
        studentsButton.setPrefHeight(44);
        studentsButton.setOnAction(e -> {
            if (students_drawer.isShown())
                students_drawer.close();
            else
                students_drawer.open();
        });
        NavigationVBox.getChildren().add(studentsButton);

        JFXButton logoutButton = new JFXButton();
        logoutButton.setText("Log Out");
        logoutButton.setPrefWidth(190);
        logoutButton.setPrefHeight(44);
        logoutButton.setOnAction(e -> {
            MainApplication.closeProgram();
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
                StudentsVBox.getChildren().add(student_button);
            }
        });

        Thread thread = new Thread(fetchStudentsTask);
        thread.setDaemon(true);
        thread.start();
    }
}
