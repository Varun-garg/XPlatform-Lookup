package StudentManagementSystem;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by rishabh on 4/11/16.
 */
public class ConfirmationBox {

    public static boolean answer;

    public static boolean display(String title,String message){
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label l = new Label(message);
        l.setFont(Font.font ("Bold", 20));

        Button b1 = new Button("   Yes   ");
        b1.setOnAction(e1->{
            answer=true;
            window.close();
        });

        Button b2 = new Button("   No   ");
        b2.setOnAction(e2->{
            answer=false;
            window.close();
        });

        VBox v = new VBox(20);
        HBox h = new HBox(20);
        h.getChildren().addAll(b1,b2);
        h.setAlignment(Pos.CENTER);
        v.getChildren().addAll(l,h);

        v.setAlignment(Pos.CENTER);

        Scene s = new Scene(v,300,150);
        window.setScene(s);
        window.showAndWait();

        return answer;

    }
}

