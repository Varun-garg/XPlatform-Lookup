package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;
import javax.xml.ws.WebEndpoint;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    @FXML
    private VBox personal_info_vbox;

    private String RollNo; // roll_no for personal,hostel & exams info

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String RollNo) {
        this.RollNo = RollNo;
    }

    TextFlow generateTextFlow(String field1, String field2) {
        TextFlow flow = new TextFlow();

        Text text1 = new Text(field1);
        text1.setStyle("-fx-font-weight: bold");

        Text text2 = new Text(field2);
        text2.setStyle("-fx-font-weight: regular");

        flow.getChildren().addAll(text1, text2);
        return flow;
    }

    private void DisplayPersonalInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        // client.register(MenuController.class);

        if (RollNo == null) RollNo = "13ICS057";
        clientTarget = client.target(Configuration.API_HOST + "data/admin/" + RollNo + "/?format=json");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();

        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Student student = mapper.readValue(response, Student.class);

            personal_info_vbox.getChildren().add(generateTextFlow("Student Name   :", student.getFullName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Enrollment         :", student.getEnrollNo()));
            personal_info_vbox.getChildren().add(generateTextFlow("Roll number       :", student.getRollNo()));
            personal_info_vbox.getChildren().add(generateTextFlow("Email                   :", student.getEmail()));
            personal_info_vbox.getChildren().add(generateTextFlow("School                :", student.getSchool()));
            personal_info_vbox.getChildren().add(generateTextFlow("Mobile number :", student.getPhone()));
            personal_info_vbox.getChildren().add(generateTextFlow("Course                :", student.getProgramName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Date of Birth     :", student.getDob()));
            personal_info_vbox.getChildren().add(generateTextFlow("Gender               :", student.getSex()));
            personal_info_vbox.getChildren().add(generateTextFlow("Father's Name   :", student.getFatherName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Mother's Name :", student.getMotherName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.DisplayPersonalInfo();
    }
}
