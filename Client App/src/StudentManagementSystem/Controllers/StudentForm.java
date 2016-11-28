package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by varun on 28/11/2016.
 */
public class StudentForm {

    @FXML
    private GridPane new_student_form_grid;
    @FXML
    private JFXTextField full_name;
    @FXML
    private JFXTextField enroll_no;
    @FXML
    private JFXTextField program_name;
    @FXML
    private JFXTextField school;
    @FXML
    private JFXTextField roll_no;
    @FXML
    private JFXTextField father_name;
    @FXML
    private JFXTextField mother_name;
    @FXML
    private JFXTextField dob;
    @FXML
    private JFXTextField sex;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;

    public void AddStudent(ActionEvent event) throws IOException {
        Form newStudentForm = new Form();
        newStudentForm.param("full_name", full_name.getText());
        newStudentForm.param("enroll_no", enroll_no.getText());
        newStudentForm.param("program_name", program_name.getText());
        newStudentForm.param("school", school.getText());
        newStudentForm.param("roll_no", roll_no.getText());
        newStudentForm.param("father_name", father_name.getText());
        newStudentForm.param("mother_name", mother_name.getText());
        newStudentForm.param("dob", dob.getText());
        newStudentForm.param("sex", sex.getText());
        newStudentForm.param("email", email.getText());
        newStudentForm.param("phone", phone.getText());

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/new/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie())
                .post(Entity.entity(newStudentForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse AddStudentResponse = mapper.readValue(response, LoginResponse.class);

        System.out.println("got message " + AddStudentResponse.getMessage());
        if (AddStudentResponse.getMessage().equals("success")) {
            System.out.print("Student with roll no " + roll_no.getText() + " successfully added");
        } else {
            System.out.print("Error: Student with roll no " + roll_no.getText() + " could not be added");
            System.out.print(response);
        }
    }


}
