package fr.irit.smac.amasrenderer.training;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

	static boolean answer;
	
	public static boolean display(String title, String message){
		Stage fen = new Stage();
		fen.setTitle(title);
		fen.initModality(Modality.APPLICATION_MODAL);
		fen.setMinWidth(150);
		Label label = new Label(message);
		
		// Creation de deux boutons
		Button yes = new Button("Oui");
		Button no = new Button("No");
		
		yes.setOnAction(e -> {
			answer = true;
			fen.close();
		});
		
		no.setOnAction(e -> {
			answer = false;
			fen.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yes, no);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		fen.setScene(scene);
		fen.showAndWait();
		
		return answer;
	}
	
}
