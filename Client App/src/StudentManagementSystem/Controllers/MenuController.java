package StudentManagementSystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

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
