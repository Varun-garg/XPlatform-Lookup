package StudentManagementSystem.Controllers;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import StudentManagementSystem.DisplayMethods;
import StudentManagementSystem.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

	@FXML
	private JFXTextField LoginField;

    @FXML
    private VBox fxml_root;

    public void event(ActionEvent event) throws IOException {

		String name = LoginField.getText();
		System.out.println(name);
		if (name.equals("admin")) {
			String jsonData = "";
			try {

				URL url = new URL("http://localhost/sms/students.json");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					jsonData += output + "\n";
				}
				 System.out.println(jsonData);
				conn.disconnect();

			} catch (IOException e) { //MalformedURLException is subclass of IOException, so not needed separately
				e.printStackTrace();
			}
			JSONParser parser = new JSONParser();

			try {
				JSONArray a = (JSONArray) parser.parse(jsonData);

				for (Object o : a) {
					JSONObject person = (JSONObject) o;

					String cd = (String) person.get("cd");
					System.out.println("cd=" + cd);
					String cdname = (String) person.get("cdname");
					System.out.println("cdname=" + cdname);
					String cname = (String) person.get("cname");
					System.out.println("cname=" + cname);
					String roll = (String) person.get("roll");
					System.out.println("roll=" + roll);
					String fname = (String) person.get("fname");
					System.out.println("fname=" + fname);
					String mname = (String) person.get("mname");
					System.out.println("mname=" + mname); //removed extra values

					//assuming logging is done
                    // in some method block
					SessionManager sessionManager = SessionManager.getInstance();
					sessionManager.setFullName("bitches! session is working!!!!");
                    Stage CurrentStage = (Stage) fxml_root.getScene().getWindow();
					DisplayMethods displayMethods = DisplayMethods.getInstance();
					displayMethods.MenuDisplay(CurrentStage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("User does not exist");
		}
	}

}
