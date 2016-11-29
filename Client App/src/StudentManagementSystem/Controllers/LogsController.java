package StudentManagementSystem.Controllers;

import StudentManagementSystem.Configuration;
import StudentManagementSystem.Model.Logs;
import StudentManagementSystem.Model.Review;
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
public class LogsController  implements Initializable {

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
            for(int i=0;i<logs.length;i++) {

                logs_info_vb.getChildren().add(Utility.GenerateRow("Name", logs[i].getUname(), i,682));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Group", logs[i].getUgroup(), i,682));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Action", logs[i].getAction(), i,682));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Date-Time", logs[i].getDatetime(), i,682));
                logs_info_vb.getChildren().add(Utility.GenerateRow("Ip-Address", logs[i].getIpAddress(), i,682));
                logs_info_vb.getChildren().add(Utility.GenerateRow("System", logs[i].getSystem(), i,682));
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
