package StudentManagementSystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by varun on 19/11/2016.
 */
public class SubmitReview implements Initializable {

    @FXML
    private TextArea ta;
    @FXML
    private Label lab;

    public void displayReview(){

        lab.setText("1) "+ta.getText());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
