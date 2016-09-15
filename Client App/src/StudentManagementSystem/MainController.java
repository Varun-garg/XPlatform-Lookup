package StudentManagementSystem;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
	@FXML
	private JFXTextField top;

	public void event(ActionEvent event) {

		String name = top.getText();
		System.out.println(name);
		//String name2="admin";
		//if (name=="admin") {
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
				// System.out.println(jsonData);
				conn.disconnect();

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

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
					System.out.println("mname=" + mname);
					String dob = (String) person.get("dob");
					System.out.println("dob=" + dob);
					String gender = (String) person.get("gender");
					System.out.println("gender=" + gender);
					String cat = (String) person.get("cat");
					System.out.println("cat=" + cat);
					String state = (String) person.get("state");
					// System.out.println("state="+state);
					long rank = (long) person.get("rank");
					System.out.println("rank=" + rank);
					long mobile = (long) person.get("mobile");
					System.out.println("mobile=" + mobile);
					String emailid = (String) person.get("emailid");
					System.out.println("emailid=" + emailid);
					String ddbank = (String) person.get("ddbank");
					System.out.println("ddbank=" + ddbank);
					long ddno = (long) person.get("ddno");
					System.out.println("ddno=" + ddno);
					String dddate = (String) person.get("dddate");
					System.out.println("dddate=" + dddate);
					Boolean isdd = (Boolean) person.get("isdd");
					System.out.println("isdd=" + isdd);
					Boolean isfee = (Boolean) person.get("isfee");
					System.out.println("isfee=" + isfee);
					Boolean isreg = (Boolean) person.get("isreg");
					System.out.println("isreg=" + isreg);

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		/*} else {
			System.out.println("User does not exist");
		}*/
	}

}
