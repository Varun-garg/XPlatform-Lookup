package StudentManagementSystem;

import StudentManagementSystem.Controllers.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setResizable(false);
        DisplayMethods displayMethods = DisplayMethods.getInstance();
        //displayMethods.LoginDisplay(primaryStage);

        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.setUserType("admin");
        sessionManager.setFullName("Varun Garg");
        sessionManager.setLoginStatus(1);
        sessionManager.setUsername("varun");
        sessionManager.setRollNumber("13ICS057");

        displayMethods.MemberHome(primaryStage);
    }
    public void closeProgram(){
        boolean value = ConfirmationBox.display("CONFIRMATION","Are you sure you want to exit?");
        if(value){
            Platform.exit();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
