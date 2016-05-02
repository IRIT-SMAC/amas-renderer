package fr.irit.smac.amasrenderer


import javafx.embed.swing.SwingNode
import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import spock.lang.Shared
import fr.irit.smac.amasrenderer.controller.GraphMainController
import fr.irit.smac.amasrenderer.service.GraphService

class GraphTest extends GuiSpecification{

	@Shared
	GraphService graphService
	
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
        
        FXMLLoader loaderRootLayout = new FXMLLoader();
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        return (BorderPane) loaderRootLayout.load();
    }
    
    
    private BorderPane initGraphAgents() throws IOException {
        
        FXMLLoader loaderGraphAgents = new FXMLLoader();
        loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"));
        BorderPane root = (BorderPane) loaderGraphAgents.load();
        SwingNode swingNode = new SwingNode();
        swingNode.setId("graphNode");
        StackPane stackPaneGraphNode = (StackPane) root.lookup("#stackPaneGraphNode");
        stackPaneGraphNode.getChildren().add(swingNode);
        GraphMainController controller = loaderGraphAgents.getController();
        controller.drawGraph();
		return root;
    }

    def "check if an agent is added by clicking on the corresponding button"() {

		
		given:
		def nbNoeud = graphService.getModel().getNodeCount()
		
        when:
        fx.clickOn("#buttonAddAgent").moveBy(0,-100).clickOn(MouseButton.PRIMARY)

        then:
        graphService.getModel().getNodeCount() == (nbNoeud+1)
    }

    def "check if an agent is added by doing the corresponding shortcut"() {

		given:
		def nbNoeud = graphService.getModel().getNodeCount()
		
        when:
        println "addition of an agent - shortcut"
        fx.moveBy(-200,-200).press(KeyCode.CONTROL).clickOn("#graphNode").release(KeyCode.CONTROL)

        then:
		graphService.getModel().getNodeCount() == (nbNoeud+1)
		
    }

    /*def "check if an agent is removed by clicking on the corresponding button"() {

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
    }*/
}
