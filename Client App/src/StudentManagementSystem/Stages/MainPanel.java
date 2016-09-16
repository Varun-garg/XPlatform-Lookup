package StudentManagementSystem.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by varun on 15/09/2016.
 */
public class MainPanel {

    public void display(Stage parentStage) throws IOException
    {
        if (parentStage == null) parentStage = new Stage();
        parentStage.setTitle("Login to Student Management System");
        Parent LoginLayout = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/MainPanel.fxml"));
        parentStage.setScene(new Scene(LoginLayout, 400, 400));
        parentStage.show();
    }
}
