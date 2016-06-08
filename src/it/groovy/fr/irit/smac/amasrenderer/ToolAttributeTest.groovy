package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.model.ToolModel
import fr.irit.smac.amasrenderer.service.ToolService

@Stepwise
class ToolAttributeTest extends GuiSpecification{

    @Shared
    ToolService toolService

    @Shared
    BorderPane rootLayout

    def setup() {

        toolService = ToolService.getInstance()

        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            this.rootLayout = rootLayout
            Main.mainStage = stage
            Map<String,Object> map = new HashMap<>()
            Map<String,Object> complexNodeMap = new HashMap<>()
            complexNodeMap.put("node1", "value1")
            map.put("complexNode", complexNodeMap)
            toolService.addTool(new ToolModel("messagingService", map))
            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
    }

    def "check if adding an attribute works"() {

        when:
        fx.clickOn("messagingService")
                        .rightClickOn("complexNode")
                        .clickOn("#addAttributeItem")
                        .rightClickOn("item")
                        .clickOn("#addAttributeItem")
                        .clickOn("#confButton")

        then:
        toolService.getTools().get(1).getAttributesMap().get("complexNode").size() == 2
    }

    def "check if modifying an attribute works"() {

        when:
        fx.clickOn("messagingService")
                        .rightClickOn("complexNode")
                        .clickOn("#renameAttributeItem")
                        .type(KeyCode.E)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        toolService.getTools().get(1).getAttributesMap().get("E") != null || ToolService.getInstance().getTools().get(1).getAttributesMap().get("e") != null
    }

    def "check if deleting an attribute works"() {

        when:
        fx.clickOn("messagingService")
                        .rightClickOn("complexNode")
                        .clickOn("#removeAttributeItem")
                        .clickOn("#confButton")

        then:
        toolService.getTools().get(1).getAttributesMap().size() == 0
    }
}
