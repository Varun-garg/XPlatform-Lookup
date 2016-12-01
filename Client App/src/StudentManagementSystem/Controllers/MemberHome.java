package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.ConfirmationBox;
import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.Model.ClientStatus;
import StudentManagementSystem.Model.LoginResponse;
import StudentManagementSystem.Model.PostResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by varun on 17/11/2016.
 */
public class MemberHome implements Initializable {

    static final String FETCH_SUCCESS = "success";
    static final String FETCH_FAIL = "fail";

    @FXML
    AnchorPane anchor_pane;

    @FXML
    JFXDrawer students_drawer;

    @FXML
    VBox NavigationVBox;
    @FXML
    AnchorPane content;
    int permit;

    TabPane tabPane = null;

    public void getClientStatus() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "user/review-permit/");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).get();
        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        ClientStatus status;

        try {
            status = mapper.readValue(response, ClientStatus.class);
            System.out.println(response);

            permit = Integer.parseInt(status.getPermit());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HBox generateHBoxButton(String text, String fileName) {
        Text text1 = new Text(text.trim());
        text1.setFont(Font.font("Calibri", FontWeight.LIGHT, 19));

        HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        hBox.setMinWidth(180);
        hBox.setMaxWidth(180);
        // hBox.setMaxHeight(10);

        hBox.getChildren().add(text1);
        hBox.getChildren().add(spacer);

        ImageView imageView = new ImageView(
                new Image(getClass().getClassLoader().
                        getResourceAsStream("StudentManagementSystem/Assets/" + fileName))
        );
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        hBox.getChildren().add(imageView);
        return hBox;
    }

    public void refreshTabPane() {
        try {
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));
            tabPane = fxmlLoader2.load();
            content.getChildren().setAll(tabPane);
        } catch (Exception e) {
            System.out.println();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getClientStatus();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StudentManagementSystem/Layout/Menu.fxml"));

        if (SessionManager.getInstance().getStudentRollNo() != null) {
            try {
                content.getChildren().clear();
                tabPane = fxmlLoader.load();
                content.getChildren().setAll(tabPane);
            } catch (Exception e) {
                System.out.println(getClass().getSimpleName());
                e.printStackTrace();
            }
        } else {
            content.getChildren().clear();
            VBox NoStudentMessage = Utility.WarningLabel("Select a student first", 0, 300);
            NoStudentMessage.setLayoutX((content.getMinWidth() - 300) / 2);
            NoStudentMessage.setLayoutY((content.getMinHeight()) / 2);
            content.getChildren().add(NoStudentMessage);
        }

        NavigationVBox.setStyle("-fx-background-color: rgba(207,216,220,0.5);");
        students_drawer.toBack();

        if (tabPane != null)
            tabPane.toBack();

        JFXButton studentsButton = new JFXButton();
        studentsButton.setGraphic(generateHBoxButton("Students", "ic_people_black_24dp_2x.png"));
        studentsButton.setPrefWidth(190);
        studentsButton.setPrefHeight(44);
        studentsButton.setAlignment(Pos.BASELINE_RIGHT);
        studentsButton.setOnAction(e -> {
            Utility.DisplayForm("Students", "StudentsList.fxml", 300, 600, this);
        });
        NavigationVBox.getChildren().add(studentsButton);

        JFXButton newStudentButton = new JFXButton();
        newStudentButton.setGraphic(generateHBoxButton("New Student", "ic_person_add_black_24dp_2x.png"));
        newStudentButton.setPrefWidth(190);
        newStudentButton.setPrefHeight(44);
        newStudentButton.setOnAction(e ->
        {
            Utility.DisplayForm("New Student", "StudentForm.fxml", 600, 600, this);
        });
        NavigationVBox.getChildren().add(newStudentButton);

        JFXButton fileUpload = new JFXButton();
        fileUpload.setGraphic(generateHBoxButton("Upload CSV", "ic_file_upload_black_24dp_2x.png"));
        fileUpload.setPrefWidth(190);
        fileUpload.setPrefHeight(44);
        fileUpload.setOnAction(e ->
        {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload CSV for SMS");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV File", "*.csv")
            );
            File csv = fileChooser.showOpenDialog(window);
            if (csv != null) {
                try {
                    ClientConfig clientConfig = new ClientConfig();
                    clientConfig.register(MessageBodyWriter.class);

                    MultiPart multiPart = new MultiPart();

                    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("students.csv", csv,
                            MediaType.TEXT_PLAIN_TYPE);
                    multiPart.bodyPart(fileDataBodyPart);
                    final FormDataMultiPart part = new FormDataMultiPart();
                    final FormDataContentDisposition dispo = FormDataContentDisposition//
                            .name("stuList")//
                            .fileName("students.csv")//
                            .build();
                    final FormDataBodyPart bodyPart = new FormDataBodyPart(dispo, new FileInputStream(csv), MediaType.valueOf("application/vnd.ms-excel"));
                    part.bodyPart(bodyPart);

                    System.out.println("media type: " + multiPart.getMediaType());
                    WebResource resource = com.sun.jersey.api.client.Client.create().resource(Configuration.API_HOST + "data/excelUpload/");
                    String response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(String.class, part);

                    ObjectMapper mapper = new ObjectMapper();
                    PostResponse AddResponse = mapper.readValue(response, PostResponse.class);
                    System.out.println(response);

                    if (AddResponse.getMessage().equals("success")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Student Management System");
                        alert.setContentText("CSV successfully uploaded");
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                                alert.close();
                            }
                        });
                    } else {
                        System.out.println(response);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Student Management System");
                        alert.setContentText("Sorry, some error occurred");
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                                alert.close();
                            }
                        });
                    }

                } catch (Exception d) {
                    d.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Student Management System");
                    alert.setContentText("Sorry, some error occurred");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            alert.close();
                        }
                    });
                }
            } else
                System.out.println("no file uploaded");
        });
        NavigationVBox.getChildren().add(fileUpload);

        JFXButton ReviewButton = new JFXButton();
        ReviewButton.setGraphic(generateHBoxButton("Reviews", "ic_rate_review_black_24dp_2x.png"));
        ReviewButton.setPrefWidth(190);
        ReviewButton.setPrefHeight(44);
        if (permit == 0) {
            ReviewButton.setOnAction(e ->
            {
                Utility.DisplayForm("Reviews", "Comments.fxml", 700, 650, this);
            });
        } else {

            ReviewButton.setOnAction(e ->
            {
                Utility.DisplayForm("Reviews", "SubmitReview.fxml", 700, 650, this);
            });
        }
        NavigationVBox.getChildren().add(ReviewButton);

        JFXButton Logs = new JFXButton();
        Logs.setGraphic(generateHBoxButton("Logs", "ic_receipt_black_24dp_2x.png"));
        Logs.setPrefWidth(190);
        Logs.setPrefHeight(44);
        Logs.setOnAction(e ->
        {
            Utility.DisplayForm("Logs", "Logs.fxml", 700, 650, this);
        });
        NavigationVBox.getChildren().add(Logs);


        JFXButton logoutButton = new JFXButton();
        logoutButton.setGraphic(generateHBoxButton("Log out", "ic_exit_to_app_black_24dp_2x.png"));
        logoutButton.setPrefWidth(190);
        logoutButton.setPrefHeight(44);
        logoutButton.setOnAction(e -> {

            boolean userChoice = ConfirmationBox.display("Logout", "Are you sure");
            if (userChoice == false)
                return;

            Form form = new Form();
            WebTarget clientTarget;
            Client client = ClientBuilder.newClient();
            clientTarget = client.target(Configuration.API_HOST + "user/logout/");

            javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie()).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            String response = rawResponse.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            LoginResponse addResponse = null;
            try {
                addResponse = mapper.readValue(response, LoginResponse.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.out.println("got message " + addResponse.getMessage());
            if (addResponse.getMessage().equals("success")) {
                SessionManager sessionManager = SessionManager.getInstance();
                sessionManager.setFullName(null);
                sessionManager.setLoginStatus(SessionManager.LOGGED_OUT);
                sessionManager.setPassword(null);
                sessionManager.setRollNumber(null);
                sessionManager.setStudentRollNo(null);
                sessionManager.setUserType(null);
                sessionManager.setUsername(null);
                Stage CurrentStage = (Stage) anchor_pane.getScene().getWindow();
                DisplayMethods displayMethods = DisplayMethods.getInstance();
                try {
                    displayMethods.LoginDisplay(CurrentStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } else {
                System.out.println("Cannot Logout at this stage");
                System.out.println(response);
            }
        });
        NavigationVBox.getChildren().add(logoutButton);
    }
}
