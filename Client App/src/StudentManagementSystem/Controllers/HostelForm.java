package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Field;
import StudentManagementSystem.Model.PostResponse;
import StudentManagementSystem.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by varun on 29/11/2016.
 */
public class HostelForm implements Initializable {
    ArrayList<Field> studentFields = new ArrayList<>();
    Label OtherErrorsLabel;

    ViewStudentHostel hostelHome;

    @FXML
    private GridPane FormGridPane;


    public void AddHostel(ActionEvent event) {

        Form form = new Form();

        OtherErrorsLabel.setText(""); //clear previous errors

        form.param("roll_no", SessionManager.getInstance().getStudentRollNo());
        for (int i = 0; i < studentFields.size(); i++) {
            studentFields.get(i).errorLabel.setText(""); //clear previous errors
            form.param(studentFields.get(i).name, studentFields.get(i).textField.getText());
        }

        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "data/student/new/hostel/");

        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").header("Cookie", SessionManager.getCookie())
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();

        try {
            PostResponse AddResponse = mapper.readValue(response, PostResponse.class);

            if (AddResponse.getMessage().equals("success")) {
                hostelHome.DisplayHostelInfo();
                FormGridPane.getScene().getWindow().hide();
            } else {
                System.out.println(AddResponse.getErrors());

                for (String error : AddResponse.getErrors()) {
                    String name = error.replaceFirst("(.*)(:)(.*)", "$1").trim();
                    String message = error.replaceFirst("(.*)(:)(.*)", "$3").trim();
                    Field errorField = studentFields.stream().filter(o -> o.name.equals(name)).findFirst().orElse(null);
                    if (errorField == null) {
                        OtherErrorsLabel.setText(OtherErrorsLabel.getText() + message + "\n");
                        continue;
                    }
                    errorField.errorLabel.setText(message);
                }

                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setHostelHome(ViewStudentHostel hostelHome) {
        this.hostelHome = hostelHome;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentFields.add(new Field("Hostel Name:", "hostel_name", "text"));
        studentFields.add(new Field("Room Number:", "room_num", "text"));
        studentFields.add(new Field("Warden Name:", "warden_name", "text"));
        studentFields.add(new Field("Warden mobile:", "warden_mob", "text"));
        studentFields.add(new Field("Caretaker Name:", "caretaker_name", "text"));
        studentFields.add(new Field("Caretaker Number :", "caretaker_num", "text"));

        for (int i = 0; i < studentFields.size(); i++) {
            FormHelper.insertField(studentFields.get(i), FormGridPane);
        }

        OtherErrorsLabel = new Label();
        OtherErrorsLabel.setTextFill(Color.web("#9d4024"));
        OtherErrorsLabel.setFont(Font.font(null, FontWeight.BOLD, 14));
        FormGridPane.add(OtherErrorsLabel, 0, FormHelper.getRowCount(FormGridPane));


        JFXButton button = new JFXButton("Add Student");
        button.setAlignment(Pos.CENTER);
        button.setBlendMode(BlendMode.SRC_ATOP);
        button.getStyleClass().add("rich-blue");
        button.setOnAction(e -> AddHostel(e));
        FormGridPane.add(button, 0, FormHelper.getRowCount(FormGridPane));
    }
}
