package StudentManagementSystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void closeProgram() {
        boolean value = ConfirmationBox.display("Student Management System", "Are you sure you want to exit?");
        if (value) {
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

     //   System.setProperty("http.proxyHost", "127.0.0.1");
     //   System.setProperty("https.proxyHost", "127.0.0.1");
     //   System.setProperty("http.proxyPort", "8888");
     //   System.setProperty("https.proxyPort", "8888");

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        DisplayMethods displayMethods = DisplayMethods.getInstance();
        displayMethods.SplashScreenDisplay(primaryStage);


    }
}
