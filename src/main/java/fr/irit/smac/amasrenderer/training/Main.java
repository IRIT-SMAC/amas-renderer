package fr.irit.smac.amasrenderer.training;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	Stage window;
	Button button, buttonClose;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		button = new Button("Alert !");
		button.setOnAction(e -> {
			System.out.println(ConfirmBox.display("ATTENTION", "Tu veux vraiment manger ça ?"));
		});
		
		buttonClose = new Button("quitter");
		buttonClose.setOnAction(e -> closeProgram());
		
		VBox layout = new VBox(2);
		layout.getChildren().addAll(button, buttonClose); 
		Scene scene1 = new Scene(layout, 300,300);
		
		window.setTitle("AMAS Rendering");
		window.setScene(scene1);
		window.show();
	}

	private void closeProgram() {
		if(ConfirmBox.display("Fermer le programme", "Etes-vous sûr de vouloir fermer le programme ?"))
			window.close();	
		
	}

}
