package StudentManagementSystem.Controllers;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import StudentManagementSystem.DisplayMethods;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SplashFXMLController implements Initializable {

    @FXML
    private StackPane rootPane;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        new SplashScreen().start();
    }
    class SplashScreen extends Thread{

        public void run(){

            try {
                Thread.sleep(2000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            //root = FXMLLoader.load(getClass().getResource("StudentManagementSystem/Layout/Login.fxml"));

                            root = FXMLLoader.load(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Login.fxml"));

                        } catch (IOException e) {
                            Logger.getLogger(SplashFXMLController.class.getName()).log(Level.SEVERE,null,e);
                        }
                        Stage CurrentStage = (Stage) rootPane.getScene().getWindow();
                        DisplayMethods displayMethods = DisplayMethods.getInstance();
                        try {
                            displayMethods.LoginDisplay(CurrentStage);
                        } catch (IOException ioException) {
                            throw new UncheckedIOException(ioException);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*Stage primaryStage = new Stage();
                        primaryStage.setScene(new Scene(root, 400, 400));
                        primaryStage.show();
                        //System.out.println(System.getProperties());

                        rootPane.getScene().getWindow().hide();*/

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }

        }
    }


