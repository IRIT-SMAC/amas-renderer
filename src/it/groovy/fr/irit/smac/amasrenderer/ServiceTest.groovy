package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import spock.lang.Shared
import fr.irit.smac.amasrenderer.service.GraphService

class ServiceTest extends GuiSpecification{

	@Shared
	graphService
	
	@Shared
	BorderPane rootLayout

	def setup() {
		setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            this.rootLayout = rootLayout
			return rootLayout
		}

		sleep(1000) //time for the graph to be initialized
		graphService = GraphService.getInstance()

	}

	def "check if a service is added by clicking on the corresponding button and filling the form"() {

		when:
		println "addition of a service - toggle button + click"
		fx.clickOn("#buttonAddService")
				.clickOn("#textfieldService")
				.write("dancingService")
				.clickOn("#buttonConfirm")

		then:
		Label service = ((ListView<Label>) rootLayout.lookup("#listServices")).getItems().get(0)
		service.getText() == "dancingService"
	}

}
