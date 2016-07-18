package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.model.tool.Tool
import fr.irit.smac.amasrenderer.service.ToolService

@Stepwise
class ToolAttributeTest extends GuiSpecification{

    @Shared
    ToolService toolService

    @Shared
    String itemId = "complexNode"

    @Shared
    String service = "myService"

    @Shared
    String addItem = "#addSingleAttributeItem"

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
            loaderRootLayout.setLocation(AmasRenderer.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            Map<String,Object> map = new HashMap<>()
            Map<String,Object> complexNodeMap = new HashMap<>()
            complexNodeMap.put("node1", "value1")
            toolService.addTool(service)
            toolService.getToolsModel().getServices().get(service).getAttributesMap().put(itemId, complexNodeMap)

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
        toolService.getTools().get(toolService.getTools().size()-1).getAttributesMap().get(itemId).size() == 2
    }

    @IgnoreIf({
        System.getenv("TRAVIS") != null
    })
    def "check if modifying an attribute works"() {

        given:
        String extraName = "Hello"

        when:
        fx.clickOn(service)
                        .rightClickOn(itemId)
                        .clickOn(renameItem)
                        .clickOn(itemId)
                        .write(extraName)
                        .type(KeyCode.ENTER)
                        .clickOn(confButton)

        then:
        toolService.getTools().get((toolService.getTools().size()-1)).getAttributesMap().get(itemId + extraName) != null
    }

    def "check if deleting an attribute works"() {

        when:
        fx.clickOn(service)
                        .rightClickOn(itemId)
                        .clickOn(removeItem)
                        .clickOn(confButton)

        then:
        toolService.getTools().get((toolService.getTools().size()-1)).getAttributesMap().size() == 1
    }
}
