package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Exams;
import StudentManagementSystem.Model.ExamsResult;
import StudentManagementSystem.Model.MarksSummary;
import StudentManagementSystem.Model.OverallResult;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URL;
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

    private String sid = getCookie();

    public String getCookie() {
        return SessionManager.getCookie();
    }

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

    public void choicebox() {
        for (int i = 1; i <= 7; i++)
            cb.getItems().add("" + i);
        cb.setValue("7");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayExamsInfo("7");
        this.choicebox();
    }
}
