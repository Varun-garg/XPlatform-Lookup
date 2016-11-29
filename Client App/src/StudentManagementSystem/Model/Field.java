package StudentManagementSystem.Model;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by varun on 29/11/2016.
 */
public class Field {
    public String title;
    public String name;
    public String type;
    public Text titleText;
    public TextField textField;
    public Label errorLabel;

    public Field(String title, String name, String type) {
        this.title = title;
        this.name = name;
        this.type = type;
    }
}