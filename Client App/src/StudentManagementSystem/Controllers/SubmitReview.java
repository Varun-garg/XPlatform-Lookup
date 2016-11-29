package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.Model.Review;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by varun on 19/11/2016.
 */
public class SubmitReview implements Initializable {

    @FXML
    private TextArea comment;

    @FXML
    private TextField rollnum;

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private RadioButton rb3;


    public void addReview(ActionEvent event) throws IOException {

        Form newReview = new Form();

        String message = "";
        if (rb1.isSelected()) {
            message += rb1.getText();
        }
        if (rb2.isSelected()) {
            message += rb2.getText();
        }
        if (rb3.isSelected()) {
            message += rb3.getText();
        }
        System.out.println(message);

        newReview.param("stu_roll", rollnum.getText());
        newReview.param("section", message);
        newReview.param("comment", comment.getText());
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/new/review/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).
                post(Entity.entity(newReview, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse addReviewResponse = mapper.readValue(response, LoginResponse.class);

        System.out.println("got message " + addReviewResponse.getMessage());
        if (addReviewResponse.getMessage().equals("success")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setContentText("Review has been successfully added");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });

        } else {
            System.out.println("Error: Student with roll no" + rollnum.getText() + "could not be added");
            System.out.println(response);
        }
    }
    @FXML
    private VBox personal_info_vb;
    public void displayReview() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/review/all");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(response);
            Review reviews[] = mapper.readValue(response, Review[].class);
            for(int i=0;i<reviews.length;i++) {
                personal_info_vb.getChildren().add(Utility.GenerateRow("Name", reviews[i].getStudent(), i,623));
                personal_info_vb.getChildren().add(Utility.GenerateRow("Section", reviews[i].getSection(), i,623));
                personal_info_vb.getChildren().add(Utility.GenerateRow("Comment", reviews[i].getComment(), i,623));
                personal_info_vb.getChildren().add(Utility.GenerateRow("Date", reviews[i].getDate(), i,623));
                Separator separator = new Separator();
                personal_info_vb.getChildren().add(separator);
            }
            /*PropertyValueFactory<Review, String> studentProperty =
                    new PropertyValueFactory<Review, String>("student");

            PropertyValueFactory<Review, String> sectionProperty =
                    new PropertyValueFactory<Review, String>("section");

            PropertyValueFactory<Review, String> comentProperty =
                    new PropertyValueFactory<Review, String>("coment");

            PropertyValueFactory<Review, String> dateProperty =
                    new PropertyValueFactory<Review, String>("date");

            studentColumn.setCellValueFactory(studentProperty);
            sectionColumn.setCellValueFactory(sectionProperty);
            comentColumn.setCellValueFactory(comentProperty);
            dateColumn.setCellValueFactory(dateProperty);

            ObservableList<Review> data =
                    FXCollections.observableArrayList();

            for(int i=0;i<reviews.length;i++) {
                data.add(new ReviewData(reviews[i].getStudent(), reviews[i].getSection(), reviews[i].getComment(), reviews[i].getDate()));
            }
            reviewtable.setItems(data);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*public void print(){

        String message="";
        if(rb1.isSelected()){
            message +=rb1.getText();
        }
        if(rb2.isSelected()){
            message +=rb2.getText();
        }
        if(rb3.isSelected()){
            message +=rb3.getText();
        }
        System.out.println(message);


    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayReview();
    }

}
