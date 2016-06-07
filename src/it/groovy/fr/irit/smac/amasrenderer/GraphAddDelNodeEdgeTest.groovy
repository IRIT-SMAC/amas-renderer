package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane

import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.controller.MainController
import fr.irit.smac.amasrenderer.controller.graph.GraphController
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

    @Shared
    double positionUp

    @Shared
    double positionDown

    @Shared
    double positionUpEdge

    @Shared
    double positionDownEdge

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            GraphController graphMainController = mainController.getGraphMainController()
            graphView = graphMainController.getGraphView()

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()
        height = graphView.getHeight()
        double gap = 65
        double gapEdge = 110
        positionUp = -(height/2)+gap
        positionDown = height-gap*2
        positionUpEdge = positionUp + gapEdge
        positionDownEdge = positionDown - gapEdge*2

//        fx.release(KeyCode.CONTROL)
//        fx.release(KeyCode.SHIFT)
        fx.clickOn(graphId)
    }


    def "check if an agent is added by clicking on the corresponding button"() {

        given:
        SingleGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()

        when:
        fx.clickOn("#buttonAddAgent").clickOn(graphId)
        sleep(1000)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

        given:
        SingleGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()

        when:
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        sleep(1000)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        SingleGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()
        fx.clickOn(button)
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        sleep(1000)

        expect:
        model.getNodeCount() == (nbNoeud+1)


        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an agent is removed by clicking on the corresponding button"() {

        given:
        SingleGraph model = graphService.getGraph()
        graphService.addNode("ag1")
        sleep(1000)

        when:
        fx.clickOn("#buttonDelAgent").clickOn(graphId)
        sleep(1000)

        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut"() {

        given:
        SingleGraph model = graphService.getGraph()
        graphService.addNode("ag1")
        sleep(1000)

        when:
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)
        sleep(1000)

        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        SingleGraph model = graphService.getGraph()
        graphService.addNode("ag1")
        fx.clickOn(button)
        sleep(2000)
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)
        sleep(2000)

        expect:
        model.getNodeCount() == 0


        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an edge is added by clicking on the corresponding button"() {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        fx.clickOn("#buttonAddEdge")
        sleep(2000)

        when:
        fx.moveTo(graphId).moveBy(0.0,positionUp).clickOn().moveBy(0.0,positionDown).clickOn()
        sleep(2000)

        then:
        graphService.getGraph().getEdgeCount() == nbEdge+1
    }

    def "check if an edge is added by doing the corresponding shortcut"() {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        sleep(2000)

        when:
        fx.moveTo(graphId).moveBy(0.0,positionUp)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,positionDown)
                        .clickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        then:
        graphService.getGraph().getEdgeCount() == nbEdge+1 && graphService.getGraph().getNodeCount() == 2
    }

    def "check if an edge is added by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        sleep(2000)
        fx.clickOn(button)
        fx.moveTo(graphId).moveBy(0.0,positionUp)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,positionDown)
                        .clickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        expect:
        graphService.getGraph().getEdgeCount() == nbEdge+1 && graphService.getGraph().getNodeCount() == 2

        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an edge is removed by clicking on the corresponding button"() {

        given:
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        graphService.addEdge("ag1","ag2")
        fx.clickOn("#buttonDelEdge")
        int oneEdge = graphService.getGraph().getEdgeCount()
        sleep(2000)

        when:
        fx.clickOn(graphId)
                        .moveBy(0.0,positionUpEdge)
                        .clickOn()
                        .moveBy(0.0,positionDownEdge)
                        .clickOn()
        sleep(2000)

        then:
        graphService.getGraph().getEdgeCount() == 0 && graphService.getGraph().getNodeCount() == 2 && oneEdge == 1
    }

    def "check if an edge is removed by doing the corresponding shortcut"() {

        given:
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        graphService.addEdge("ag1","ag2")
        int oneEdge = graphService.getGraph().getEdgeCount()
        sleep(2000)

        when:
        fx.moveTo(graphId)
                        .moveBy(0.0,positionUpEdge)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .moveBy(0.0,positionDownEdge)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        then:
        graphService.getGraph().getEdgeCount() == 0 &&  graphService.getGraph().getNodeCount() == 2 && oneEdge == 1
    }

    def "check if an edge is removed by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode("ag1",0.0,positionUp)
        graphService.addNode("ag2",0.0,positionDown)
        graphService.addEdge("ag1","ag2")
        int oneEdge = graphService.getGraph().getEdgeCount()
        sleep(2000)
        fx.clickOn(button)
        fx.moveTo(graphId)
                        .moveBy(0.0,positionUpEdge)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .moveBy(0.0,positionDownEdge)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)
        sleep(2000)

        expect:
        graphService.getGraph().getEdgeCount() == 0 &&  graphService.getGraph().getNodeCount() == 2 && oneEdge == 1

        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }
}
