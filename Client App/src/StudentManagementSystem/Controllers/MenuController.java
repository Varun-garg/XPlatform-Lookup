package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.Model.Hostel;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;
import javax.xml.ws.WebEndpoint;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    @FXML
    private VBox personal_info_vbox;

    @FXML
    private VBox students_list_vbox;

    @FXML
    private VBox hostel_info_vbox;

    @FXML
    private TabPane tab_plane;

    private String RollNo; // roll_no for personal,hostel & exams info

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String RollNo) {
        System.out.println("got roll number " + RollNo);
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

    private void DisplayStudents() {


        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/admin" + "/?format=json");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Student[] students = mapper.readValue(response, Student[].class);
            for (int i = 0; i < students.length; i++) {
                System.out.println(students[i].getFullName() + " " + students[i].getRollNo());
                if (students[i].getRollNo() == null || students[i].getRollNo().length() == 0) continue;

                Button button = new Button(students[i].getFullName());
                button.setMinWidth(150);
                button.setPadding(new Insets(5, 5, 5, 5));//(top/right/bottom/left)
                final int final_iterator = i;
                button.setOnAction(e -> {
                    this.setRollNo(students[final_iterator].getRollNo());
                    tab_plane.getSelectionModel().select(0);
                    this.Display();
                });
                students_list_vbox.getChildren().add(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DisplayPersonalInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        // client.register(MenuController.class);

        clientTarget = client.target(Configuration.API_HOST + "data/admin/" + RollNo + "/?format=json");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();

        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Student student = mapper.readValue(response, Student.class);

            personal_info_vbox.getChildren().add(generateTextFlow("Student Name     :", student.getFullName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Enrollment           :", student.getEnrollNo()));
            personal_info_vbox.getChildren().add(generateTextFlow("Roll number         :", student.getRollNo()));
            personal_info_vbox.getChildren().add(generateTextFlow("Email                     :", student.getEmail()));
            personal_info_vbox.getChildren().add(generateTextFlow("School                  :", student.getSchool()));
            personal_info_vbox.getChildren().add(generateTextFlow("Mobile number   :", student.getPhone()));
            personal_info_vbox.getChildren().add(generateTextFlow("Course                  :", student.getProgramName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Date of Birth       :", student.getDob()));
            personal_info_vbox.getChildren().add(generateTextFlow("Gender                 :", student.getSex()));
            personal_info_vbox.getChildren().add(generateTextFlow("Father's Name     :", student.getFatherName()));
            personal_info_vbox.getChildren().add(generateTextFlow("Mother's Name   :", student.getMotherName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

    public void AddStudent(ActionEvent event) throws IOException{
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
        clientTarget = client.target(Configuration.API_HOST + "data/student/new_entry/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").
                post(Entity.entity(newStudentForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse AddStudentResponse = mapper.readValue(response, LoginResponse.class);

        System.out.println("got message " + AddStudentResponse.getMessage());
        if (AddStudentResponse.getMessage().equals("success")) {
            System.out.print("Student with roll no" + roll_no.getText() + "successfully added");

            this.setRollNo(roll_no.getText());
            tab_plane.getSelectionModel().select(0);
            this.Display();
        } else {
            System.out.print("Error: Student with roll no" + roll_no.getText() + "couold not be added");
        }
    }

    private void DisplayHostelInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();

        clientTarget = client.target(Configuration.API_HOST + "data/student/hostel/" + RollNo + "/?format=json");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Hostel hos = mapper.readValue(response, Hostel.class);

            hostel_info_vbox.getChildren().add(generateTextFlow("Roll No                         :", hos.getRollNum()));
            hostel_info_vbox.getChildren().add(generateTextFlow("Room No                      :", hos.getRoomNum()));
            hostel_info_vbox.getChildren().add(generateTextFlow("Warden Name              :", hos.getWardenName()));
            hostel_info_vbox.getChildren().add(generateTextFlow("Warden Mobile No.     :", hos.getWardenMob()));
            hostel_info_vbox.getChildren().add(generateTextFlow("Caretaker Name           :", hos.getCaretakerName()));
            hostel_info_vbox.getChildren().add(generateTextFlow("Caretaker Mobile No.  :", hos.getCaretakerNum()));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personal_info_vbox.setSpacing(10);
        students_list_vbox.setSpacing(10);
        hostel_info_vbox.setSpacing(10);
    }

    public void Display() {

        personal_info_vbox.getChildren().clear(); //clear previous data
        students_list_vbox.getChildren().clear();
        hostel_info_vbox.getChildren().clear();

        if (RollNo == null) return; // empty info????

        this.DisplayPersonalInfo();
        this.DisplayStudents();
        this.DisplayHostelInfo();
    }
}
