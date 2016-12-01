package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Student;
import StudentManagementSystem.Model.StudentSearch;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    @FXML
    TextField SearchBox;

    MemberHome memberHome;

    VBox StudentsVBox;


    public void setMemberHome(MemberHome memberHome) {
        this.memberHome = memberHome;
    }

    public class SearchService extends Service<String> {
        private StringProperty url = new SimpleStringProperty(this, "url");

        public final void setUrl(String value) {
            url.set(value);
        }

        public final String getUrl() {
            return url.get();
        }

        public final StringProperty urlProperty() {
            return url;
        }

        protected Task createTask() {
            final String _url = getUrl();
            return new Task() {
                @Override
                protected String call() throws Exception {
                    try {
                        WebTarget clientTarget;
                        Client client = ClientBuilder.newClient();
                        updateMessage("Fetching students list");
                        clientTarget = client.target(_url);
                        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
                        String response = rawResponse.readEntity(String.class);
                        ObjectMapper mapper = new ObjectMapper();

                        if (!_url.contains("search")) {
                            students = mapper.readValue(response, Student[].class);
                            students = (Student[]) Arrays.stream(students).sorted((s1, s2) -> s1.getFullName().compareTo(s2.getFullName())).toArray(Student[]::new);
                            System.out.println(response);
                        } else {
                            StudentSearch searchResults = mapper.readValue(response, StudentSearch.class);
                            System.out.println();
                            System.out.println(searchResults.getSearch());
                            students = searchResults.getSearch().toArray(students);
                        }
                    } catch (Exception e) {
                        System.out.println("got exception in fetchStudentsTask");
                        e.printStackTrace();
                        return FETCH_FAIL;
                    }
                    return FETCH_SUCCESS;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    StudentsVBox.getChildren().clear();
                    for (int i = 0; i < students.length; i++) {
                        JFXButton student_button = new JFXButton();
                        student_button.setText(students[i].getFullName());
                        student_button.setPrefWidth(255);
                        student_button.setMinHeight(44);
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
                }
            };
        }
    }

    SearchService service;

    public void Search(ActionEvent e) {
        service.setUrl(Configuration.API_HOST + "data/student/search/?query=" + SearchBox.getText());
        System.out.println(service.getUrl());
        if (!service.isRunning()) {
            service.reset();
            service.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SearchBox.setFont(Font.font(null, FontWeight.LIGHT, 14));
        SearchBox.setOnAction(e -> Search(e));
        service = new SearchService();
        service.setUrl(Configuration.API_HOST + "data/admin" + "/?format=json");
        service.start();
        try {
            StudentsVBox = FXMLLoader.load(getClass().getResource("../Layout/StudentsDrawer.fxml"));
            ListScrollPane.setContent(StudentsVBox);
        } catch (Exception e) {
            System.out.println("caught exception in " + this.getClass().getSimpleName());
            e.printStackTrace();
            return;
        }
    }
}
