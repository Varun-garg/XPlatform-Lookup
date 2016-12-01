package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.*;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by rishabh on 19/11/2016.
 */
public class ViewStudentExams implements Initializable {


    private String cookie;

    @FXML // fx:id="exams"
    private TableView<Exams> exams;

    @FXML // fx:id="subjectcode"
    private TableColumn<ExamsResult, String> subjectcode;

    @FXML // fx:id="grade"
    private TableColumn<ExamsResult, String> grade;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private ChoiceBox<String> cb;

    String rollNo = SessionManager.getInstance().getStudentRollNo();
    private String sid = getCookie();

    public String getCookie() {
        return SessionManager.getCookie();
    }

    int deletesem;
    public void displayExamsInfo(String n) {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/exam/" + SessionManager.getInstance().getStudentRollNo() + "/" + n + "");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Exams e = mapper.readValue(response, Exams.class);

            List<OverallResult> overallResult = e.getOverallResult();
            List<MarksSummary> marksSummary = e.getMarksSummary();

            deletesem = overallResult.get(0).getSemester();
            l3.setText(String.valueOf("SEMESTER-" + overallResult.get(0).getSemester()));

            PropertyValueFactory<ExamsResult, String> subjectcodeProperty =
                    new PropertyValueFactory<ExamsResult, String>("subjectcode");

            PropertyValueFactory<ExamsResult, String> gradeProperty =
                    new PropertyValueFactory<ExamsResult, String>("grade");

            subjectcode.setCellValueFactory(subjectcodeProperty);
            grade.setCellValueFactory(gradeProperty);

            ObservableList<Exams> data =
                    FXCollections.observableArrayList();
            for (int i = 0; i < marksSummary.size(); i++) {
                data.add(new ExamsResult(marksSummary.get(i).getSubjectCode(), marksSummary.get(i).getGrade()));
            }
            exams.setItems(data);
            l1.setText(String.valueOf(overallResult.get(0).getSgpa()));
            String l2 = String.valueOf(overallResult.get(0).getResult());
            this.l2.setText(l2.toUpperCase(Locale.forLanguageTag(l2)));
            cb.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> this.displayExamsInfo(newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalSemesters() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/semesters/"+rollNo+"/");
        //clientTarget = client.target("https://studentmanagementsystem.pythonanywhere.com/data/student/semesters/13ICS047/");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        ExamSemesterNum num ;

        try {
            num = mapper.readValue(response, ExamSemesterNum.class);
            System.out.println(response);

            List<Integer> semesters = num.getSemesters();
            System.out.println(semesters.get(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField Subjectcode;
    @FXML
    private TextField Grade;
    @FXML
    private TextField Semester;

    public void addExams(ActionEvent event) throws IOException {
        Form newExamForm = new Form();

        String sem= Semester.getText();
        newExamForm.param("roll_no", rollNo);
        newExamForm.param("subject_code", Subjectcode.getText());
        newExamForm.param("grade", Grade.getText());
        newExamForm.param("semester", sem);
        newExamForm.param("tc", "0");
        newExamForm.param("tgp","0");
        newExamForm.param("sgpa", "8");
        newExamForm.param("result", "Promoted");

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/new/exam/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).
                post(Entity.entity(newExamForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        PostResponse addExamResponse = mapper.readValue(response, PostResponse.class);

        System.out.println("got message " + addExamResponse.getMessage());
        if (addExamResponse.getMessage().equals("success")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setContentText("Result has been successfully added");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
            displayExamsInfo(sem);


        } else {
            System.out.println("Error: Student with could not be added");
            System.out.println(response);
        }
    }

    public void deleteStudentExam(ActionEvent event) throws IOException {
        System.out.println("inside delete hostel"+deletesem);
        Form newExamForm = new Form();
        String rollNo = SessionManager.getInstance().getStudentRollNo();
        newExamForm.param("roll_no", rollNo);
        newExamForm.param("semeste", String.valueOf(deletesem));

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/exam/delete/"+rollNo+"/"+deletesem+"/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).
                post(Entity.entity(newExamForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        System.out.println(response);
        ObjectMapper mapper = new ObjectMapper();
        PostResponse deleteExamResponse = mapper.readValue(response, PostResponse.class);

        System.out.println("got message " + deleteExamResponse.getMessage());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Here...");
        if (deleteExamResponse.getMessage().equals("success")) {

            alert.setContentText("Result has been successfully Deleted");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
            System.out.print("Result successfully deleted");

        } else {
            alert.setContentText("Result could not be Deleted");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });
            System.out.println(response);
        }
    }


    public void choicebox() {
        for (int i = 1; i <= 7; i++)
            cb.getItems().add("" + i);
        cb.setValue("6");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayExamsInfo("6");
        this.choicebox();
        //totalSemesters();
    }
}
