package fr.irit.smac.amasrenderer

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane

import org.graphstream.ui.swingViewer.ViewPanel

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
class GraphNodeAttributeTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    String graphId = "#graphNode"

    GraphMainController graphMainController

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            MainController mainController = loaderRootLayout.getController()
            graphMainController = mainController.getGraphMainController()
            graphView = graphMainController.getGraphView()
            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

        AgentGraphModel model = graphService.getModel()
        graphService.addNode("ag1")
        sleep(2000)

    }

    def "check if adding an attribute works with alt+rightClick"() {

        given:
        Object<T> tree = graphService.getModel().getNode(0).getAttribute("ui.stocked-info")
        int nbChildren = tree.getRoot().getChildren().size()

        when:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .doubleClickOn("#tree")
                        //TODO : y depends on the height of the window
                        .moveBy(-200,-160)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .clickOn("#confButton")

        then:
        tree.getRoot().getChildren().size() == nbChildren + 1
    }

    def "check if modifying an attribute works with alt+rightClick"() {

        when:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .doubleClickOn("#tree")
                        .moveBy(-200,-160)
                        .rightClickOn()
                        .clickOn("#renameAttributeItem")
                        .type(KeyCode.E)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        String value = graphService.getModel().getNode(0).getAttribute("ui.stocked-info").getRoot().getValue()
        value == "e" || value == "E"
    }

    def "check if deleting an attribute works with alt+rightClick"() {

        given:
        fx.rightClickOn(graphId)
                        .clickOn("#tree")
                        .doubleClickOn("#tree")
                        .moveBy(-200,-160)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
        int nbChildren = 1

        when:
        fx.clickOn("#tree")
                        .moveBy(-340,-130)
                        .rightClickOn()
                        .clickOn("#removeAttributeItem")
                        .clickOn("#confButton")

        then:
        Object<T> tree = graphService.getModel().getNode(0).getAttribute("ui.stocked-info")
        tree.getRoot().getChildren().size() == nbChildren - 1
    }
}
