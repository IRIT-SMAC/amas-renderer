package fr.irit.smac.amasrenderer

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane

import org.graphstream.ui.swingViewer.ViewPanel

import spock.lang.IgnoreIf
import spock.lang.Shared
import fr.irit.smac.amasrenderer.controller.GraphMainController
import fr.irit.smac.amasrenderer.controller.MainController
import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
class GraphNodeAttributeTest extends GuiSpecification{

    @Shared
    GraphService graphService

    @Shared
    ViewPanel graphView

    @Shared
    String graphId = "#graphNode"

    GraphMainController graphMainController
    
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
    
    def "check if adding an attribute works with alt+rightClick"() {
        
                given:
                fx.press(KeyCode.CONTROL)
                .clickOn(graphId)
                .release(KeyCode.CONTROL)
                
                Object<T> tree = graphService.getModel().getNode(0).getAttribute("ui.stocked-info")
                int nbChildren = tree.getRoot().getChildren().size()
                
                when:
                fx.press(KeyCode.ALT)
                .rightClickOn(graphId)
                .release(KeyCode.ALT)
                .clickOn("#tree")
                .doubleClickOn("#tree")
                .moveBy(-200,-140)
                .doubleClickOn()
                .rightClickOn()
                .clickOn("#addAttributeItem")
                .clickOn("#confButton")
                
                then:
                tree.getRoot().getChildren().size() == nbChildren + 1
            }
        
            def "check if modifying an attribute works with alt+rightClick"() {
        
                given:
                fx.press(KeyCode.CONTROL)
                .clickOn(graphId)
                .release(KeyCode.CONTROL)
                        
                when:
                println "attribute modified"
                fx.press(KeyCode.CONTROL)
                .clickOn(graphId)
                .release(KeyCode.CONTROL)
                .press(KeyCode.ALT)
                .rightClickOn()
                .release(KeyCode.ALT)
                .clickOn("#tree")
                .doubleClickOn("#tree")
                .moveBy(-200,-140)
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
                fx.press(KeyCode.CONTROL)
                .clickOn(graphId)
                .release(KeyCode.CONTROL)
                
                Object<T> tree = graphService.getModel().getNode(0).getAttribute("ui.stocked-info")
                int nbChildren = tree.getRoot().getChildren().size()
                        
                when:
                fx.press(KeyCode.ALT)
                .rightClickOn(graphId)
                .release(KeyCode.ALT)
                .clickOn("#tree")
                .doubleClickOn("#tree")
                .moveBy(-200,-140)
                .doubleClickOn()
                .moveBy(0,40)
                .rightClickOn()
                .clickOn("#removeAttributeItem")
                .clickOn("#confButton")
                
                then:
                tree.getRoot().getChildren().size() == nbChildren - 1
        
            }

}
