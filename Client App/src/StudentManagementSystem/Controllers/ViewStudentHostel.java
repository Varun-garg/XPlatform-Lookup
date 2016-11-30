package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Hostel;
import StudentManagementSystem.Model.PostResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.effect.BlendMode;
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
 * Created by varun on 17/11/2016.
 */
public class ViewStudentHostel implements Initializable {

    @FXML
    private VBox hostel_info_vbox;

    @FXML
    private JFXButton button;

    boolean isAvailable = false;
    Hostel hostel;


    public void displayHostelInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        String rollNo = SessionManager.getInstance().getStudentRollNo();
        clientTarget = client.target(Configuration.API_HOST + "data/student/hostel/" + rollNo + "/?format=json");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            hostel_info_vbox.getChildren().clear();
            hostel = mapper.readValue(response, Hostel.class);
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Roll No:", hostel.getRollNum(), 0, 300));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Room No:", hostel.getRoomNum(), 1, 300));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Warden Name:", hostel.getWardenName(), 2, 300));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Warden Mobile No.:", hostel.getWardenMob(), 3, 300));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Caretaker Name:", hostel.getCaretakerName(), 4, 300));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Caretaker Mobile No:", hostel.getCaretakerNum(), 5, 300));
            isAvailable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentHostel(ActionEvent event) throws IOException {
        System.out.println("inside delete hostel");
        Form newHostelForm = new Form();
        String rollNo = SessionManager.getInstance().getStudentRollNo();
        newHostelForm.param("roll_no", rollNo);

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/hostel/delete/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).
                post(Entity.entity(newHostelForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        System.out.println(response);
        ObjectMapper mapper = new ObjectMapper();
        PostResponse deleteHostelResponse = mapper.readValue(response, PostResponse.class);

        System.out.println("got message " + deleteHostelResponse.getMessage());
        if (deleteHostelResponse.getMessage().equals("success")) {
            System.out.print("Student successfully deleted");
            displayHostelInfo();
        } else {
            System.out.println("Error: in deleting the student");
            System.out.println(response);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hostel_info_vbox.setSpacing(10);
        hostel_info_vbox.getChildren().clear();
        this.displayHostelInfo();

        button.setText("Add / Update");
        button.setAlignment(Pos.CENTER);
        button.setBlendMode(BlendMode.SRC_ATOP);
        button.getStyleClass().add("windows7-default");
        button.setOnAction(e -> {
            Utility.DisplayForm("Update Hostel Info", "HostelForm.fxml", 600, 600, this);
        });
    }
}
