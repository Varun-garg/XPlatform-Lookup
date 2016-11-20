package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Hostel;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private JFXTextField roll_no1;
    @FXML
    private JFXTextField hostel_name;
    @FXML
    private JFXTextField room_num;
    @FXML
    private JFXTextField warden_name;
    @FXML
    private JFXTextField warden_mob;
    @FXML
    private JFXTextField caretaker_name;
    @FXML
    private JFXTextField caretaker_num;

    private void DisplayHostelInfo() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();

        String rollNo;

        if (SessionManager.getInstance().getStudentRollNo() != null)
            rollNo = SessionManager.getInstance().getStudentRollNo();
        else
            rollNo = SessionManager.getInstance().getRollNumber();

        clientTarget = client.target(Configuration.API_HOST + "data/student/hostel/" + rollNo + "/?format=json");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Hostel hos = mapper.readValue(response, Hostel.class);
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Roll No:", hos.getRollNum(), 0));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Room No:", hos.getRoomNum(), 1));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Warden Name:", hos.getWardenName(), 2));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Warden Mobile No.:", hos.getWardenMob(), 3));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Caretaker Name:", hos.getCaretakerName(), 4));
            hostel_info_vbox.getChildren().add(Utility.GenerateRow("Caretaker Mobile No:", hos.getCaretakerNum(), 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHostel(ActionEvent event) throws IOException {
        Form newHostelForm = new Form();

        newHostelForm.param("roll_no", roll_no1.getText());
        newHostelForm.param("hostel_name", hostel_name.getText());
        newHostelForm.param("room_num", room_num.getText());
        newHostelForm.param("warden_name", warden_name.getText());
        newHostelForm.param("warden_mob", warden_mob.getText());
        newHostelForm.param("caretaker_name", caretaker_name.getText());
        newHostelForm.param("caretaker_num", caretaker_num.getText());

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/new/hostel/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).
                post(Entity.entity(newHostelForm, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse addHostelResponse = mapper.readValue(response, LoginResponse.class);

        System.out.println("got message " + addHostelResponse.getMessage());
        if (addHostelResponse.getMessage().equals("success")) {
            System.out.print("Student with roll no" + roll_no1.getText() + "successfully added");

            //   this.setRollNo(roll_no1.getText());
            //   tab_plane.getSelectionModel().select(0);
            //   this.Display();
        } else {
            System.out.println("Error: Student with roll no" + roll_no1.getText() + "could not be added");
            System.out.println(response);
        }
    }

    public void refresh()
    {
        System.out.println("refresh called");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hostel_info_vbox.setSpacing(10);
        hostel_info_vbox.getChildren().clear();
        this.DisplayHostelInfo();
    }

}
