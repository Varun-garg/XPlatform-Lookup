package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Logs;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rishabh on 11/30/2016.
 */
public class LogsController implements Initializable {

    @FXML
    private VBox logs_info_vb;

    public void displayLogs() {
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        clientTarget = client.target(Configuration.API_HOST + "logs/");
        javax.ws.rs.core.Response rawResponse = clientTarget.request("application/json").get();
        String response = rawResponse.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(response);

            Logs logs[] = mapper.readValue(response, Logs[].class);
            for (int i = 0; i < logs.length; i++) {

                logs_info_vb.getChildren().add(Utility.GenerateRow("Name", logs[i].getUname(), i, 642));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Group", logs[i].getUgroup(), i, 642));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Action", logs[i].getAction(), i, 642));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Date-Time", logs[i].getDatetime(), i, 642));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Ip-Address", logs[i].getIpAddress(), i, 642));
                logs_info_vb.getChildren().add(Utility.GenerateRow("System", logs[i].getSystem(), i, 642));
                Separator separator1 = new Separator();
                Separator separator2 = new Separator();
                logs_info_vb.getChildren().add(separator1);
                logs_info_vb.getChildren().add(separator2);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayLogs();
    }
}
