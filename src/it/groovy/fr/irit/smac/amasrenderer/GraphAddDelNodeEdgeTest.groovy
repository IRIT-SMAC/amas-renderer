package fr.irit.smac.amasrenderer

import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane

import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.AutoCleanup
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.controller.MainController
import fr.irit.smac.amasrenderer.controller.graph.GraphMainController
import fr.irit.smac.amasrenderer.model.AgentGraphModel
import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
@Stepwise
class GraphAddDelNodeEdgeTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    BorderPane rootLayout

    @Shared
    String graphId = "#graphNode"

    @Shared
    double height


    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            GraphMainController graphMainController = mainController.getGraphMainController()
            graphView = graphMainController.getGraphView()

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()
        height = graphView.getHeight()

        fx.release(KeyCode.CONTROL)
        fx.release(KeyCode.SHIFT)
        fx.clickOn(graphId)
    }


    def "check if an agent is added by clicking on the corresponding button"() {

        given:
        AgentGraphModel model = graphService.getModel()
        int nbNoeud = model.getNodeCount()

        when:
        fx.clickOn("#buttonAddAgent").clickOn(graphId)
        sleep(2000)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

        given:
        AgentGraphModel model = graphService.getModel()
        int nbNoeud = model.getNodeCount()

        when:
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        sleep(2000)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }


    def "check if an agent is removed by clicking on the corresponding button"() {

        given:
        AgentGraphModel model = graphService.getModel()
        graphService.addNode("ag1")
        sleep(2000)

        when:
        fx.clickOn("#buttonDelAgent").clickOn(graphId)
        sleep(2000)
        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut"() {

        given:
        AgentGraphModel model = graphService.getModel()
        graphService.addNode("ag1")
        sleep(2000)

        when:
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)
        sleep(2000)

        then:
        model.getNodeCount() == 0
    }

    def "check if an edge is added by clicking on the corresponding button"() {

        given:
        int nbEdge = graphService.getModel().getEdgeCount()
        graphService.addNode("ag1",0.0,-(height/2)+20)
        graphService.addNode("ag2",0.0,height-40)
        fx.clickOn("#buttonAddEdge")
        sleep(2000)

        when:
        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20).clickOn().moveBy(0.0,height-40).clickOn()
        sleep(2000)

        then:
        graphService.getModel().getEdgeCount() == nbEdge+1
    }

    def "check if an edge is added by doing the corresponding shortcut"() {

        given:
        int nbEdge = graphService.getModel().getEdgeCount()
        graphService.addNode("ag1",0.0,-(height/2)+20)
        graphService.addNode("ag2",0.0,height-40)
        sleep(2000)

        when:
        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        then:
        graphService.getModel().getEdgeCount() == nbEdge+1 && graphService.getModel().getNodeCount() == 2
    }

    def "check if an edge is removed by clicking on the corresponding button"() {

        given:
        graphService.addNode("ag1",0.0,-(height/2)+20)
        graphService.addNode("ag2",0.0,height-40)
        graphService.addEdge("ag1","ag2")
        fx.clickOn("#buttonDelEdge")
        int oneEdge = graphService.getModel().getEdgeCount()
        sleep(2000)

        when:
        fx.clickOn(graphId)
                        .moveBy(0.0,-(height/2)+20)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
        sleep(2000)

        then:
        graphService.getModel().getEdgeCount() == 0 && graphService.getModel().getNodeCount() == 2 && oneEdge == 1
    }

    def "check if an edge is removed by doing the corresponding shortcut"() {

        given:
        graphService.addNode("ag1",0.0,-(height/2)+20)
        graphService.addNode("ag2",0.0,height-40)
        graphService.addEdge("ag1","ag2")
        int oneEdge = graphService.getModel().getEdgeCount()
        sleep(2000)

        when:
        fx.moveTo(graphId)
                        .moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .moveBy(0.0,height-40)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        then:
        graphService.getModel().getEdgeCount() == 0 &&  graphService.getModel().getNodeCount() == 2 && oneEdge == 1
    }
}
