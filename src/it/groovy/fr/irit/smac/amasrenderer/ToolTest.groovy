package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.ListView
import javafx.scene.layout.BorderPane
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.model.ToolModel
import fr.irit.smac.amasrenderer.service.ToolService

@Stepwise
class ToolTest extends GuiSpecification{

    @Shared
    ToolService toolService

    @Shared
    BorderPane rootLayout

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(AmasRenderer.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()

            return rootLayout
        }


        sleep(1000) //time for the graph to be initialized
        toolService = ToolService.getInstance()

    }

    def "check if a tool is added by clicking on the corresponding button and filling the form"() {

        when:
        fx.clickOn("#buttonAddService")
                        .clickOn("#textfieldTool")
                        .write("messaging")
                        .clickOn("#buttonConfirm")

        then:
        toolService.getTools().get(1).getName() == "messagingService"
    }

    def "check if a tool is deleted by clicking on the corresponding button"() {

        given:
        fx.clickOn("#buttonAddService")
                        .clickOn("#textfieldTool")
                        .write("messaging")
                        .clickOn("#buttonConfirm")
        int nbTools = toolService.getTools().size()

        when:
        fx.clickOn("messagingService")
                        .clickOn("#delButton")
                        .clickOn("#buttonConfirmRemove")

        then:
        toolService.getTools().size() == nbTools - 1
    }
}
