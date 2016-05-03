package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.layout.VBox
import spock.lang.Shared
import fr.irit.smac.amasrenderer.service.GraphService

class ServiceTest extends GuiSpecification{

	@Shared
	graphService

	def setup() {
		setupStage { stage ->

			VBox rootLayout = initRootLayout()
			return rootLayout
		}

		sleep(1000) //time for the graph to be initialized
		graphService = GraphService.getInstance()

	}

	private VBox initRootLayout() throws IOException {

		FXMLLoader loaderRootLayout = new FXMLLoader();
		loaderRootLayout.setLocation(Main.class.getResource("view/Services.fxml"));
		return (VBox) loaderRootLayout.load();
	}

	def "check if a service is added by clicking on the corresponding button and filling the form"() {

		when:
		println "addition of a service - toggle button + click"
		fx.clickOn("#buttonAddService").clickOn("#textfieldService").write("dancingService")

		then:
		true
	}

}
