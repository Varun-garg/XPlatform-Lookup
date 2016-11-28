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


    private String RollNo; // roll_no for personal,hostel & exams info

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String RollNo) {
        System.out.println("got roll number " + RollNo);
        this.RollNo = RollNo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Display() {
        if (RollNo == null) return; // empty info????
    }
}
