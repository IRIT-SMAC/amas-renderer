package fr.irit.smac.amasrenderer


import java.io.IOException

import fr.irit.smac.amasrenderer.controller.GraphMainController
import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane

class GraphNodeTest extends GuiSpecification{

    def setup() {
        setupStage { stage ->

            BorderPane rootLayout = initRootLayout()
            AnchorPane root = initGraphAgents()
            rootLayout.setCenter(root)
            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
    }

    private BorderPane initRootLayout() throws IOException {

        FXMLLoader loaderRootLayout = new FXMLLoader()
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
        return (BorderPane) loaderRootLayout.load()
    }

    private AnchorPane initGraphAgents() throws IOException {

        FXMLLoader loaderGraphAgents = new FXMLLoader()
        loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"))
        AnchorPane root = (AnchorPane) loaderGraphAgents.load()
        SwingNode swingNode = new SwingNode()
        swingNode.setId("graphNode")
        StackPane stackPaneGraphNode = (StackPane) root.lookup("#stackPaneGraphNode")
        stackPaneGraphNode.getChildren().add(swingNode)
        GraphMainController controller = loaderGraphAgents.getController()
        controller.drawGraph()
        return root
    }

    def "check if an agent is added by clicking on the corresponding button"() {

        when:
        fx.clickOn("#addAgent")

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

        when:
        println "addition of an agent - shortcut"
        //changer ALT par SHIFT
        fx.press(KeyCode.ALT).clickOn("#graphNode").release(KeyCode.ALT)

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }

    def "check if an agent is removed by clicking on the corresponding button"() {

        when:
        //fx.clickOn("#deleteAgent")
        println "deletion of an agent - button"

        then:
        false
        //Verifier dans le modele qu'un agent a ete supprime
    }

    def "check if an agent is removed by doing the corresponding shortcut"() {

        when:
        println "deletion of an agent - shortcut"

        then:
        false
        //Verifier dans le modele qu'un agent a ete supprime
    }
}
