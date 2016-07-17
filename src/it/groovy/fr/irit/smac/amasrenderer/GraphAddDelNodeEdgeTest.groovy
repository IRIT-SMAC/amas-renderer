package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane

import org.graphstream.graph.implementations.MultiGraph
import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.controller.MainController
import fr.irit.smac.amasrenderer.controller.graph.GraphController
import fr.irit.smac.amasrenderer.service.graph.GraphService;

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

    @Shared
    String agentId = "0"

    @Shared
    String agentId2 = "1"

    @Shared
    int waitingTime = 2000

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(AmasRenderer.class.getResource("view/RootLayout.fxml"))
            rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            GraphController graphMainController = mainController.getGraphController()
            graphView = graphMainController.getGraphView()
            return rootLayout
        }

        sleep(waitingTime) //time for the graph to be initialized
        graphService = GraphService.getInstance()
        height = graphView.getHeight()
        double gap = 65
        double gapEdge = 110
        positionUp = -(height/2)+gap
        positionDown = height-gap*2
        positionUpEdge = positionUp + gapEdge
        positionDownEdge = positionDown - gapEdge*2
        graphService.getIdCount().set(0)

        fx.clickOn(graphId)
    }


    def "check if an agent is added by clicking on the corresponding button"() {

        given:
        MultiGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()

        when:
        fx.clickOn("#buttonAddAgent").clickOn(graphId)
        sleep(waitingTime)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

        given:
        MultiGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()

        when:
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        sleep(waitingTime)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        MultiGraph model = graphService.getGraph()
        int nbNoeud = model.getNodeCount()
        fx.clickOn(button)
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        sleep(waitingTime)

        expect:
        model.getNodeCount() == (nbNoeud+1)


        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an agent is removed by clicking on the corresponding button"() {

        given:
        MultiGraph model = graphService.getGraph()
        graphService.addNode(0,0)
        sleep(waitingTime)

        when:
        fx.clickOn("#buttonDelAgent").clickOn(graphId)
        sleep(waitingTime)

        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut"() {

        given:
        MultiGraph model = graphService.getGraph()
        graphService.addNode(0,0)
        sleep(waitingTime)

        when:
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)
        sleep(waitingTime)

        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        MultiGraph model = graphService.getGraph()
        graphService.addNode(0,0)
        fx.clickOn(button)
        sleep(waitingTime)
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)
        sleep(waitingTime)

        expect:
        model.getNodeCount() == 0


        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an edge is added by clicking on the corresponding button"() {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode(0.0,positionUp)
        fx.clickOn("#buttonAddEdge")
        sleep(waitingTime)

        when:
        fx.moveTo(graphId).clickOn().clickOn()
        sleep(waitingTime)

        then:
        graphService.getGraph().getEdgeCount() == nbEdge+1
    }

    def "check if an edge is added by doing the corresponding shortcut"() {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode(0.0,positionUp)
        sleep(waitingTime)

        when:
        fx.moveTo(graphId).press(KeyCode.SHIFT)
                        .clickOn()
                        .clickOn()
                        .release(KeyCode.SHIFT)
        sleep(waitingTime)

        then:
        graphService.getGraph().getEdgeCount() == nbEdge+1
    }

    def "check if an edge is added by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode(0.0,positionUp)
        sleep(waitingTime)
        fx.clickOn(button)
        fx.moveTo(graphId).press(KeyCode.SHIFT)
                        .clickOn()
                        .clickOn()
                        .release(KeyCode.SHIFT)
        sleep(waitingTime)

        expect:
        graphService.getGraph().getEdgeCount() == nbEdge+1
        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }

    def "check if an edge is removed by clicking on the corresponding button"() {

        given:
        graphService.addNode(0.0,positionUp)
        graphService.addNode(0.0,positionDown)
        graphService.addEdge(agentId,agentId2)
        fx.clickOn("#buttonDelEdge")
        sleep(waitingTime)

        when:
        fx.clickOn(graphId)
        sleep(waitingTime)

        then:
        graphService.getGraph().getEdgeCount() == 0
    }

    def "check if an edge is removed by doing the corresponding shortcut"() {

        given:
        graphService.addNode(0.0,positionUp)
        graphService.addNode(0.0,positionDown)
        graphService.addEdge(agentId,agentId2)
        sleep(waitingTime)

        when:
        fx.moveTo(graphId)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)
        sleep(waitingTime)

        then:
        graphService.getGraph().getEdgeCount() == 0
    }

    def "check if an edge is removed by doing the corresponding shortcut whereas one of the button AddDel are selected"(String button) {

        given:
        int nbEdge = graphService.getGraph().getEdgeCount()
        graphService.addNode(0.0,positionUp)
        graphService.addNode(0.0,positionDown)
        graphService.addEdge(agentId,agentId2)
        sleep(waitingTime)
        fx.clickOn(button)
        fx.moveTo(graphId)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)
        sleep(waitingTime)

        expect:
        graphService.getGraph().getEdgeCount() == 0

        where:
        button << ["#buttonAddAgent", "#buttonDelAgent", "#buttonAddEdge", "#buttonDelEdge"]
    }
}
