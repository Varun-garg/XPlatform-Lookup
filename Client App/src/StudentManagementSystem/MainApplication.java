package StudentManagementSystem;

import StudentManagementSystem.Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window=primaryStage;
        DisplayMethods displayMethods = DisplayMethods.getInstance();
        displayMethods.LoginDisplay(primaryStage);
        primaryStage.setOnCloseRequest(e->{
            e.consume();
            closeProgram();
        });
    }
    public void closeProgram(){
        boolean value = ConfirmationBox.display("CONFIRMATION","Are you sure you want to exit?");
        if(value){
            window.close();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
