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
    String itemId = "complexNode"

    @Shared
    String service = "messagingService"
    
    @Shared
    String addItem = "#addAttributeItem"

    @Shared
    String renameItem = "#renameAttributeItem"

    @Shared
    String removeItem = "#removeAttributeItem"

    @Shared
    String confButton = "#confButton"

    def setup() {

        toolService = ToolService.getInstance()

        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            Map<String,Object> map = new HashMap<>()
            Map<String,Object> complexNodeMap = new HashMap<>()
            complexNodeMap.put("node1", "value1")
            map.put(itemId, complexNodeMap)
            toolService.addTool(new ToolModel(service, map))
            Main.mainStage = stage

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
    }

    def "check if adding an attribute works"() {

        when:
        fx.clickOn(service)
                        .rightClickOn(itemId)
                        .clickOn(addItem)
                        .rightClickOn("item")
                        .clickOn(addItem)
                        .clickOn(confButton)

        then:
        toolService.getTools().get(1).getAttributesMap().get(itemId).size() == 2
    }

    def "check if modifying an attribute works"() {

        when:
        fx.clickOn(service)
                        .rightClickOn(itemId)
                        .clickOn(renameItem)
                        .type(KeyCode.E)
                        .type(KeyCode.ENTER)
                        .clickOn(confButton)

        then:
        toolService.getTools().get(1).getAttributesMap().get("E") != null || ToolService.getInstance().getTools().get(1).getAttributesMap().get("e") != null
    }

    def "check if deleting an attribute works"() {

        when:
        fx.clickOn(service)
                        .rightClickOn(itemId)
                        .clickOn(removeItem)
                        .clickOn(confButton)

        then:
        toolService.getTools().get(1).getAttributesMap().size() == 0
    }
}
