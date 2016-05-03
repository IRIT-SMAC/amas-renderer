package fr.irit.smac.amasrenderer


import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane

import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.Shared
import fr.irit.smac.amasrenderer.controller.GraphMainController
import fr.irit.smac.amasrenderer.service.GraphService

class GraphTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    SwingNode swingNode

    @Shared
    String graphId = "#graphNode"

    def setup() {
        setupStage { stage ->

            BorderPane rootLayout = initRootLayout()
            BorderPane root = initGraphAgents()
            rootLayout.setCenter(root)
            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

    }

    private BorderPane initRootLayout() throws IOException {

        FXMLLoader loaderRootLayout = new FXMLLoader()
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
        return (BorderPane) loaderRootLayout.load()
    }


    private BorderPane initGraphAgents() throws IOException {

        FXMLLoader loaderGraphAgents = new FXMLLoader()
        loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"))
        BorderPane root = (BorderPane) loaderGraphAgents.load()
        swingNode = new SwingNode()
        swingNode.setId("graphNode")
        StackPane stackPaneGraphNode = (StackPane) root.lookup("#stackPaneGraphNode")
        stackPaneGraphNode.getChildren().add(swingNode)
        GraphMainController controller = loaderGraphAgents.getController()
        controller.drawGraph()
        graphView = controller.getGraphView()
        return root
    }

    def "check if an agent is added by clicking on the corresponding button"() {


        given:
        def model = graphService.getModel()
        def nbNoeud = model.getNodeCount()

        when:
        println "addition of an agent - toggle button + click"
        fx.clickOn("#buttonAddAgent").clickOn(graphId)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

        given:
        def model = graphService.getModel()
        def nbNoeud = model.getNodeCount()

        when:
        println "addition of an agent - shortcut"
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)

        then:
        model.getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is removed by clicking on the corresponding button"() {

        given:
        def model = graphService.getModel()

        when:
        println "deletion of an agent - button"
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        fx.clickOn("#buttonDelAgent").clickOn(graphId)

        then:
        model.getNodeCount() == 0
    }

    def "check if an agent is removed by doing the corresponding shortcut"() {

        given:
        def model = graphService.getModel()

        when:
        println "deletion of an agent - shortcut"
        fx.press(KeyCode.CONTROL).clickOn(graphId).release(KeyCode.CONTROL)
        fx.press(KeyCode.CONTROL).rightClickOn(graphId).release(KeyCode.CONTROL)


        then:
        model.getNodeCount() == 0
    }

    def "check if an edge is added by clicking on the corresponding button"() {

        given:
        def nbEdge = graphService.getModel().getEdgeCount()


        when:
        println "addition of a link - button"

        fx.press(KeyCode.CONTROL)
                        .clickOn(graphId)
                        .moveBy(0,-50)
                        .clickOn()
                        .release(KeyCode.CONTROL)
                        .clickOn("#buttonAddEdge")
        double width = graphView.getWidth()
        double height = graphView.getHeight()
        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20).clickOn().moveBy(0.0,height-40).clickOn()

        then:
        graphService.getModel().getEdgeCount() == nbEdge+1
    }

    def "check if an edge is added by doing the correspoonding shortcut"() {

        given:
        def nbEdge = graphService.getModel().getEdgeCount()

        when:
        println "addition of a link - shortcut"
        fx.press(KeyCode.CONTROL)
                        .clickOn(graphId)
                        .moveBy(0,-50)
                        .clickOn()
                        .release(KeyCode.CONTROL)
        double width = graphView.getWidth()
        double height = graphView.getHeight()
        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
                        .release(KeyCode.SHIFT)


        then:
        graphService.getModel().getEdgeCount() == nbEdge+1 && graphService.getModel().getNodeCount() == 2
    }

    def "check if an edge is remove by clicking on the corresponding button"() {

        when:
        println "link removed with button"
        fx.press(KeyCode.CONTROL)
                        .clickOn(graphId)
                        .moveBy(0,-50)
                        .clickOn()
                        .release(KeyCode.CONTROL)
        double width = graphView.getWidth()
        double height = graphView.getHeight()
        fx.moveTo(graphId).press(KeyCode.SHIFT)
                        .moveBy(0.0,-(height/2)+20)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
                        .release(KeyCode.SHIFT)
                        .clickOn("#buttonDelEdge")

        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
                        .release(KeyCode.SHIFT)


        then:
        graphService.getModel().getEdgeCount() == 0 && graphService.getModel().getNodeCount() == 2
    }

    def "check if an edge is remove by doing the correspoonding shortcut"() {

        when:
        println "link removed with button"
        fx.press(KeyCode.CONTROL)
                        .clickOn(graphId)
                        .moveBy(0,-50)
                        .clickOn()
                        .release(KeyCode.CONTROL)
        double width = graphView.getWidth()
        double height = graphView.getHeight()
        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .clickOn()
                        .moveBy(0.0,height-40)
                        .clickOn()
                        .release(KeyCode.SHIFT)

        fx.moveTo(graphId).moveBy(0.0,-(height/2)+20)
                        .press(KeyCode.SHIFT)
                        .rightClickOn()
                        .moveBy(0.0,height-40)
                        .rightClickOn()
                        .release(KeyCode.SHIFT)

        then:
        graphService.getModel().getEdgeCount() == 0
    }
}
