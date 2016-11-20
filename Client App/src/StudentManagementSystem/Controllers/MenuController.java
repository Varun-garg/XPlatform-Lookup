package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    @FXML
    private TabPane tab_plane;

    @FXML
    private GridPane new_student_form_grid;

    private String RollNo; // roll_no for personal,hostel & exams info
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

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String RollNo) {
        System.out.println("got roll number " + RollNo);
        this.RollNo = RollNo;
    }

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

            this.setRollNo(roll_no.getText());
            tab_plane.getSelectionModel().select(0);
            this.Display();
        } else {
            System.out.print("Error: Student with roll no " + roll_no.getText() + " could not be added");
            System.out.print(response);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Display() {
        if (RollNo == null) return; // empty info????
    }
}
