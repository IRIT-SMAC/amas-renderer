package fr.irit.smac.amasrenderer

import static org.testfx.api.FxAssert.verifyThat
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import fr.irit.smac.amasrenderer.controller.GraphMainController

class IHMTest extends GuiSpecification{

	/** This is an example of using TestFX **/

	/*def setup() {
	 setupStage { stage ->
	 def passwordField = new TextField(id: "password")
	 def submitButton = new Button(id: "submit", text: "submit")
	 def messageLabel = new Label(id: "message")
	 submitButton.onAction = { ActionEvent event ->
	 if (passwordField.text == "sheogorath") {
	 messageLabel.text = "please enter!"
	 }
	 else {
	 messageLabel.text = "wrong password!"
	 }
	 } as EventHandler
	 return new HBox(passwordField, submitButton, messageLabel)
	 }
	 }*/

	BorderPane rootLayout;

	private void initRootLayout() throws IOException {

		FXMLLoader loaderRootLayout = new FXMLLoader();
		loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
		this.rootLayout = (BorderPane) loaderRootLayout.load();
	}

	private void initGraphAgents() throws IOException {

		FXMLLoader loaderGraphAgents = new FXMLLoader();
		loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"));
		AnchorPane root = (AnchorPane) loaderGraphAgents.load();
		GraphMainController controller = loaderGraphAgents.getController();
		controller.drawGraph();
		rootLayout.setCenter(root);
	}


	def setup() {
		setupStage { stage ->
			this.initRootLayout();
			this.initGraphAgents();

			return new Scene(rootLayout);
		}
	}

	def "Premier test" () {
		given:
		println "alakon"
		when:
		println "lol"
		fx.clickOn("#attributes_synthesis")
		then:
		true
	}
}
