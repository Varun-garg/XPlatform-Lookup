package StudentManagementSystem.Controllers;

import StudentManagementSystem.Model.Field;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by varun on 29/11/2016.
 */
public class FormHelper {

    public static int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if (rowIndex != null) {
                    numRows = Math.max(numRows, rowIndex + 1);
                }
            }
        }
        return numRows;
    }

    public static void insertField(Field field, GridPane formGridPane) {
        field.titleText = new Text(field.title);
        field.titleText.setFont(Font.font(null, FontWeight.BOLD, 14));

        field.textField = new TextField();
        field.textField.setId("FIELD_" + field.name);
        field.textField.setFont(Font.font(null, FontWeight.LIGHT, 14));


        field.errorLabel = new Label();
        field.errorLabel.setId("ERROR_" + field.name);
        field.errorLabel.setTextFill(Color.web("#9d4024"));
        field.errorLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        int rowIndex = getRowCount(formGridPane);
        formGridPane.add(field.titleText, 0, rowIndex);
        formGridPane.add(field.textField, 1, rowIndex);
        formGridPane.add(field.errorLabel, 2, rowIndex);
    }
}
