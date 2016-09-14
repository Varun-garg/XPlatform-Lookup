package StudentManagementSystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by varun on 14/09/2016.
 */
public class Login {

    public void DisplayView(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login to Student Management System");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}
