package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.VBox;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by varun on 17/11/2016.
 */
public class ViewStudent implements Initializable {

    boolean isAvailable = false;
    Student student;
    @FXML
    private VBox personal_info_vbox;
    @FXML
    private JFXButton button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personal_info_vbox.setSpacing(10);
        personal_info_vbox.getChildren().clear(); //clear previous data
        this.DisplayPersonalInfo();

        button.setText("Update");
        button.setAlignment(Pos.CENTER);
        button.setBlendMode(BlendMode.SRC_ATOP);
        button.getStyleClass().add("windows7-default");
        button.setOnAction(e -> {
            Utility.DisplayForm("Update Student Info", "StudentForm.fxml", 600, 600, this);
        });
    }

    private void DisplayPersonalInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        // client.register(MenuController.class);

        clientTarget = client.target(Configuration.API_HOST + "data/admin/" + SessionManager.getInstance().getStudentRollNo() + "/?format=json");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            student = mapper.readValue(response, Student.class);

            personal_info_vbox.getChildren().add(Utility.GenerateRow("Student Name", student.getFullName(), 0, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Enrollment", student.getEnrollNo(), 1, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Roll number", student.getRollNo(), 2, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Email", student.getEmail(), 3, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("School", student.getSchool(), 4, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Mobile number", student.getPhone(), 5, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Course", student.getProgramName(), 6, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Date of Birth", student.getDob(), 7, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Gender", student.getSex(), 8, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Father's Name", student.getFatherName(), 9, 300));
            personal_info_vbox.getChildren().add(Utility.GenerateRow("Mother's Name", student.getMotherName(), 10, 300));
            isAvailable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
