package fr.irit.smac.amasrenderer

import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import fr.irit.smac.amasrenderer.controller.GraphMainController

class GraphEdgeTest extends GuiSpecification{

    def setupSpec() {
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

    def "check if an edge is added by clicking on the corresponding button"() {

        when:
        println "link added with button"

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }

    def "check if an edge is added by doing the correspoonding shortcut"() {

        when:
        println "link added with shortcut"

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }

    def "check if an edge is remove by clicking on the corresponding button"() {

        when:
        println "link removed with button"

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }

    def "check if an edge is remove by doing the correspoonding shortcut"() {

        when:
        println "link removed with shortcut"

        then:
        false
        //Verifier dans le modele qu'un agent a ete ajoute
    }
}
