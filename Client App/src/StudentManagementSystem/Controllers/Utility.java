package StudentManagementSystem.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by varun on 17/11/2016.
 */
public class Utility {

    public static final int HBoxWidth = 300;

    public static HBox GenerateRow(String field1, String field2, int color_id) {
        Text text1 = new Text(field1.trim());
        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Text text2 = new Text(field2.trim());
        text2.minWidth(100);
        String color;

        if (color_id % 2 == 0)
            color = "#cfd8dc";
        else
            color = "#e6ee9c";

        HBox hBox = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.setMinWidth(HBoxWidth);
        hBox.setMaxWidth(HBoxWidth);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: " + color + ";-fx-border-color: #263238;-fx-border-radius: 3");
        hBox.getChildren().add(text1);
        HBox InnerRightHbox = new HBox();
        InnerRightHbox.setMinWidth(170);
        //  InnerRightHbox.getChildren().add(text2);
        hBox.getChildren().add(spacer);
        hBox.getChildren().add(text2);

        return hBox;
    }

    public static VBox WarningLabel(String field1, int color_id) {
        Text text = new Text(field1.trim());
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        String color;

        if (color_id % 2 == 0)
            color = "#cfd8dc";
        else
            color = "#e6ee9c";

        VBox vBox = new VBox();
        vBox.setMinWidth(HBoxWidth);
        vBox.setMaxWidth(HBoxWidth);
        vBox.setPadding(new Insets(15, 15, 15, 15));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: " + color + ";-fx-border-color: #263238;-fx-border-radius: 3");

        Image image = new Image (Utility.class.getClassLoader().getResourceAsStream("StudentManagementSystem/Assets/warning-icon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(text);

        return vBox;
    }

    public static void DisplayForm(String title, String fxmlFileName,int height,int width,Object currentClass) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Utility.class.getClassLoader().getResource("StudentManagementSystem/Layout/" + fxmlFileName));
            Parent parent = fxmlLoader.load();

            StudentForm studentForm = fxmlLoader.getController();
            studentForm.setMemberHome((MemberHome) currentClass);

            window.setScene(new Scene(parent, height, width));
        } catch (Exception e) {
            System.out.println(Utility.class.getSimpleName());
            e.printStackTrace();
        }
        window.getIcons().add(new Image(Utility.class.getClassLoader().getResourceAsStream("StudentManagementSystem/Assets/logo.png")));
        window.showAndWait();
    }
}
