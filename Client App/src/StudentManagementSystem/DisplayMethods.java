package StudentManagementSystem;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by varun on 27/09/2016.
 */
public class DisplayMethods {

    private static DisplayMethods singleDisplayMethods = new DisplayMethods();

    private DisplayMethods(){}

    public static DisplayMethods getInstance()
    {
        return singleDisplayMethods;
    }

    public void MenuDisplay(Stage parentStage) throws IOException
    {
        if (parentStage == null) parentStage = new Stage();
        parentStage.setTitle("Welcome Admin");
        Parent LoginLayout = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));
        parentStage.setScene(new Scene(LoginLayout, 800, 500));
        parentStage.show();
        //VBox vBox (VBox)
    }

    public void LoginDisplay(Stage parentStage) throws Exception {

        if (parentStage == null) parentStage = new Stage();
        parentStage.setTitle("Login to Student Management System");
        Parent LoginLayout = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Login.fxml"));
        parentStage.setScene(new Scene(LoginLayout, 400, 400));
        parentStage.show();
    }
}
