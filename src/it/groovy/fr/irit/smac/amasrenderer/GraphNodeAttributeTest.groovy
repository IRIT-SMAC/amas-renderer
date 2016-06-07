package fr.irit.smac.amasrenderer

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

import org.graphstream.graph.implementations.SingleGraph
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

    GraphController graphMainController

    @Shared
    Stage mainStage

    @Shared
    double positionX

    @Shared
    double positionY

    @Shared
    double gapLastAttribute

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            graphMainController = mainController.getGraphMainController()
            graphView = graphMainController.getGraphView()
            Main.mainStage = stage
            mainStage = stage

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

        SingleGraph model = graphService.getGraph()
        graphService.addNode("ag1")

        double height = 450
        double width = 630
        positionX = -(width/2)+20
        positionY = -(height/2) + 70
        gapLastAttribute = 360

        sleep(2000)

    }

    def "check if an attribute is correctly added"() {

        given:
        Map<String,Object> agent = GraphService.getInstance().getAgentMap().get("ag1")
        int nbChildren = agent.size()

        when:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .moveBy(positionX, positionY)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .moveBy(0, positionY + gapLastAttribute)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .clickOn("#confButton")

        then:
        agent.size() == nbChildren + 1
    }

    def "check if an attribute is correctly updated"() {

        when:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .moveBy(positionX,positionY)
                        .rightClickOn()
                        .clickOn("#renameAttributeItem")
                        .type(KeyCode.E)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        GraphService.getInstance().getAgentMap().get("e") || GraphService.getInstance().getAgentMap().get("E")
    }

    def "check if an attribute is correctly deleted"() {

        given:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .moveBy(positionX, positionY)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .moveBy(0, positionY + gapLastAttribute)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .clickOn("#confButton")
        Map<String,Object> agent = GraphService.getInstance().getAgentMap().get("ag1")
        int nbChildren = agent.size()

        when:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .moveBy(0, -40)
                        .rightClickOn()
                        .clickOn("#removeAttributeItem")
                        .clickOn("#confButton")

        then:
        GraphService.getInstance().getAgentMap().get("ag1").size() == nbChildren - 1
    }
}
