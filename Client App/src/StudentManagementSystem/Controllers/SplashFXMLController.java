package StudentManagementSystem.Controllers;

import StudentManagementSystem.DisplayMethods;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashFXMLController implements Initializable {

    @FXML
    private StackPane rootPane;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        new SplashScreen().start();
    }

    class SplashScreen extends Thread {

        public void run() {

            try {
                Thread.sleep(3500);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        rootPane.getScene().getWindow().hide();
                        try {
                            DisplayMethods.getInstance().LoginDisplay(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


