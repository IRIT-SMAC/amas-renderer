package fr.irit.smac.amasrenderer

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

import org.graphstream.graph.implementations.MultiGraph
import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.controller.MainController
import fr.irit.smac.amasrenderer.controller.graph.GraphController
import fr.irit.smac.amasrenderer.model.AgentModel
import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
@Stepwise
class GraphNodeAttributeTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    String graphId = "#graphNode"

    @Shared
    String agentId = "0"

    @Shared
    String knowledgeId = "knowledge"

    @Shared
    String defaultNameItem = "item"

    @Shared
    String addItem = "#addAttributeItem"

    @Shared
    String renameItem = "#renameAttributeItem"

    @Shared
    String removeItem = "#removeAttributeItem"

    @Shared
    String confButton = "#confButton"

    GraphController graphMainController

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(AmasRenderer.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            graphMainController = mainController.getGraphController()
            graphView = graphMainController.getGraphView()

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

        MultiGraph model = graphService.getGraph()
        graphService.idCount.set(0)
        graphService.addNode(0,0)
        sleep(2000)

    }

    def "check if an attribute is correctly added"() {

        given:
        int nbChildren = graphService.getAgentMap().get(agentId).get("primaryFeature").get("knowledge").size()

        when:
        fx.rightClickOn(graphId)
                        .rightClickOn(knowledgeId)
                        .clickOn(addItem)
                        .rightClickOn(defaultNameItem)
                        .clickOn(addItem)
                        .clickOn(confButton)

        then:
        graphService.getAgentMap().get(agentId).get("primaryFeature").get("knowledge").size() == nbChildren + 1
    }

    def "check if an attribute is correctly updated"() {

        given:
        String extraName = "Hello"

        when:
        fx.rightClickOn(graphId)
                        .rightClickOn(agentId)
                        .clickOn(renameItem)
                        .clickOn(agentId)
                        .write(extraName)
                        .type(KeyCode.ENTER)
                        .clickOn(confButton)

        then:
        GraphService.getInstance().getAgentMap().get(agentId + extraName)
    }

    def "check if an attribute is correctly deleted"() {

        given:
        fx.rightClickOn(graphId)
                        .rightClickOn(knowledgeId)
                        .clickOn(addItem)
                        .rightClickOn(defaultNameItem)
                        .clickOn(addItem)
                        .clickOn(confButton)
        int nbChildren = graphService.getAgentMap().get(agentId).get("primaryFeature").get("knowledge").size()
        sleep(2000)

        when:
        fx.rightClickOn(graphId)
                        .rightClickOn(defaultNameItem)
                        .clickOn(removeItem)
                        .clickOn(confButton)

        then:
        graphService.getAgentMap().get(agentId).get("primaryFeature").get("knowledge").size() == nbChildren - 1
    }
}
