package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static StudentManagementSystem.Controllers.MemberHome.FETCH_FAIL;
import static StudentManagementSystem.Controllers.MemberHome.FETCH_SUCCESS;

/**
 * Created by varun on 30/11/2016.
 */
public class StudentList implements Initializable {

    Student students[];

    @FXML
    ScrollPane ListScrollPane;

    MemberHome memberHome;

    public void setMemberHome(MemberHome memberHome) {
        this.memberHome = memberHome;
    }


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
                students = (Student[]) Arrays.stream(students).sorted((s1,s2)-> s1.getFullName().compareTo(s2.getFullName())).toArray(Student[]::new);

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
            StudentsVBox = FXMLLoader.load(getClass().getResource("../Layout/StudentsDrawer.fxml"));
            ListScrollPane.setContent(StudentsVBox);
        } catch (Exception e) {
            System.out.println("caught exception in " + this.getClass().getSimpleName());
            e.printStackTrace();
            return;
        }

        fetchStudentsTask.setOnSucceeded(e -> {

            for (int i = 0; i < students.length; i++) {
                JFXButton student_button = new JFXButton();
                student_button.setText(students[i].getFullName());
                student_button.setPrefWidth(245);
                student_button.setPrefHeight(44);
                final int CurrentIndex = i;
                student_button.getStyleClass().add("lion-default-square");
                student_button.setOnAction(f -> {
                    try {
                        SessionManager.getInstance().setStudentFullName(students[CurrentIndex].getFullName());
                        SessionManager.getInstance().setStudentRollNo(students[CurrentIndex].getRollNo());
                        memberHome.refreshTabPane();
                        ListScrollPane.getScene().getWindow().hide();
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
