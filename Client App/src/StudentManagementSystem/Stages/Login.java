package StudentManagementSystem.Stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

public class Login {

    public void display(Stage parentStage) throws Exception {

        if (parentStage == null) parentStage = new Stage();
        parentStage.setTitle("Login to Student Management System");
        Parent LoginLayout = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Login.fxml"));
        parentStage.setScene(new Scene(LoginLayout, 400, 400));
        parentStage.show();


    }

}
