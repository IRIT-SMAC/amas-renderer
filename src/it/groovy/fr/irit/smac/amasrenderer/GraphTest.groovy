package fr.irit.smac.amasrenderer


import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane

import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.IgnoreIf
import spock.lang.Shared
import fr.irit.smac.amasrenderer.controller.GraphMainController

import fr.irit.smac.amasrenderer.controller.GraphNodeEditController
import fr.irit.smac.amasrenderer.controller.TreeModifyController
import fr.irit.smac.amasrenderer.controller.MainController

import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
class GraphTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    SwingNode swingNode

    @Shared
    BorderPane rootLayout

    GraphMainController graphMainController

    @Shared
    String graphId = "#graphNode"

    def setup() {
        setupStage {
            stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            graphMainController = mainController.getGraphMainController()
            graphView = graphMainController.getGraphView()
            return rootLayout
        }
        graphMainController.initSubControllers()

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

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

    def "check if adding an attribute works with alt+rightClick"() {

        when:
        println "attribute added"
        fx.press(KeyCode.CONTROL)
        .clickOn(graphId)
        .release(KeyCode.CONTROL)
        .press(KeyCode.ALT)
        .rightClickOn()
        .release(KeyCode.ALT)
        .clickOn("#addValue")
        .write("VictoryDance")
        TreeView<String> tree = GraphNodeEditController.getRoot3().lookup("#tree")
        tree.getRoot().getChildren().clear()
        tree.getSelectionModel().select(0)
        fx.clickOn("#addButton")
        .clickOn("#confButton")

        then:
        println "succeeded"
        println "victoryDance"
        graphService.getModel().getNode(0).getAttribute("ui.stocked-info").getRoot().getChildren()[0].getValue() == "VictoryDance"
    }

    def "check if modifying an attribute works with alt+rightClick"() {

        when:
        println "attribute modified"
        fx.press(KeyCode.CONTROL)
        .clickOn(graphId)
        .release(KeyCode.CONTROL)
        .press(KeyCode.ALT)
        .rightClickOn()
        .release(KeyCode.ALT)
        .clickOn("#modifyValue")
        .write("MuchAttributeVerySucceeded\\o/")
        TreeView<String> tree = GraphNodeEditController.getRoot3().lookup("#tree")
        tree.getSelectionModel().select(tree.getRoot())

        fx.clickOn("#modButton")
        .clickOn("#confButton")

        then:
        graphService.getModel().getNode(0).getAttribute("ui.stocked-info").getRoot().getValue() == "MuchAttributeVerySucceeded\\o/"
    }

    def "check if deleting an attribute works with alt+rightClick"() {

        when:
        println "attribute deleted"
        fx.press(KeyCode.CONTROL)
        .clickOn(graphId)
        .release(KeyCode.CONTROL)
        .press(KeyCode.ALT)
        .rightClickOn()
        .release(KeyCode.ALT)
        TreeView<String> tree = GraphNodeEditController.getRoot3().lookup("#tree")
        tree.getSelectionModel().select(tree.getRoot().getChildren()[0])
        //TODO trouver comment cliquer sur un noeud(pas root), ET get l'arbre pour faire un setSelected ...
        int itemCount = tree.getRoot().getChildren().size()
        fx.clickOn("#delButton")
        .clickOn("#confButton")

        then:
        graphService.getModel().getNode(0).getAttribute("ui.stocked-info").getRoot().getChildren().size() == (itemCount-1)


    }
}