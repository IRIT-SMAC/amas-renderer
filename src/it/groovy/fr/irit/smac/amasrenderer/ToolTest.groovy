package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.ListView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import spock.lang.Shared
import spock.lang.Stepwise;
import fr.irit.smac.amasrenderer.service.GraphService

@Stepwise
class ToolTest extends GuiSpecification{

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
            Main.mainStage = stage

			return rootLayout
		}

        
		sleep(1000) //time for the graph to be initialized
		graphService = GraphService.getInstance()

	}

	def "check if a tool is added by clicking on the corresponding button and filling the form"() {

		when:
		println "addition of a service - toggle button + click"
		fx.clickOn("#buttonAddService")
				.clickOn("#textfieldTool")
				.write("dancingService")
				.clickOn("#buttonConfirm")

		then:
		String service = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().get(0)
		service == "dancingService"
	}
    
    def "check if a tool is deleted by clicking on the corresponding button"() {
        given:
        println "addition of a service - toggle button + click"
        fx.clickOn("#buttonAddService")
                .clickOn("#textfieldTool")
                .write("dancingService")
                .clickOn("#buttonConfirm")
        when:
        int beforeDelSize = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().size()
        String service = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().get(0)
        fx.clickOn(service)
        .clickOn("#delButton")
        then:
        ((ListView<String>) rootLayout.lookup("#listTool")).getItems().size() == beforeDelSize - 1
    }

}
