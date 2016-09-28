package StudentManagementSystem;


import StudentManagementSystem.Controllers.MenuController;
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
        SessionManager sessionManager = SessionManager.getInstance();
        parentStage.setTitle("Welcome " + sessionManager.getFullName()) ;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));
        Parent LoginLayout = fxmlLoader.load();

        MenuController menuController = fxmlLoader.<MenuController>getController();
        menuController.setRollNo(sessionManager.getRollNumber());

        parentStage.setScene(new Scene(LoginLayout, 800, 500));
        parentStage.show();
        menuController.Display();
    }

    public void LoginDisplay(Stage parentStage) throws Exception {

        if (parentStage == null) parentStage = new Stage();
        parentStage.setTitle("Login to Student Management System");
        Parent LoginLayout = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Login.fxml"));
        parentStage.setScene(new Scene(LoginLayout, 400, 400));
        parentStage.show();
    }
}
