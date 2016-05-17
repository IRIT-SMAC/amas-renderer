package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.control.ListView
import javafx.scene.control.TreeItem
import javafx.scene.layout.BorderPane
import spock.lang.IgnoreIf
import spock.lang.Shared
import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
class ToolAttributeTest extends GuiSpecification{

    @Shared
    graphService
    
    @Shared
    BorderPane rootLayout

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            this.rootLayout = rootLayout
            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

    }

    def "check if adding an attribute works"() {

        given:
        //int nbChildren = tree.getRoot().getChildren().size()
        println "addition of a service - toggle button + click"
        fx.clickOn("#buttonAddService")
                .clickOn("#textfieldTool")
                .write("Mahogany(S)Tool")
                .clickOn("#buttonConfirm")

        when:
        String service = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().get(0)
        
        //TODO: marche pas, a trouver
//        TreeItem<String> tree = ((TreeItem<String>) rootLayout.lookup("#tree"))
//        println "salut " + tree
        
        fx.clickOn(service)
        .moveBy(320,-190)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
                        .clickOn("#confButton")
        then:
        true
        //TODO: merge avec gael, pour avoir l'attributeMap dans le modele et y acceder
        //tree.getRoot().getChildren().size() == nbChildren + 1
    }
    
    def "check if modifying an attribute works"() {
        
                given:
                //int nbChildren = tree.getRoot().getChildren().size()
                println "addition of a service - toggle button + click"
                fx.clickOn("#buttonAddService")
                        .clickOn("#textfieldTool")
                        .write("Mahogany(S)Tool")
                        .clickOn("#buttonConfirm")
        
                when:
                String service = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().get(0)
                
                //TODO: marche pas, a trouver
//                TreeItem<String> tree = ((TreeItem<String>) rootLayout.lookup("#tree"))
//                println "salut " + tree
                
                fx.clickOn(service)
                .moveBy(320,-190)
                                .rightClickOn()
                                .clickOn("#renameAttributeItem")
                                .type(KeyCode.E)
                                .type(KeyCode.ENTER)
                                .clickOn("#confButton")
                then:
                true
                //TODO: merge avec gael, pour avoir l'attributeMap dans le modele et y acceder
                //tree.getRoot().getValue == "e"
            }
    
    def "check if deleting an attribute works"() {
        
                given:
                //TODO: int nbChildren = tree.getRoot().getChildren().size()
                println "addition of a service - toggle button + click"
                fx.clickOn("#buttonAddService")
                        .clickOn("#textfieldTool")
                        .write("Mahogany(S)Tool")
                        .clickOn("#buttonConfirm")
                        
                String service = ((ListView<String>) rootLayout.lookup("#listTool")).getItems().get(0)
                        
                        //marche pas, a trouver
//                TreeItem<String> tree = ((TreeItem<String>) rootLayout.lookup("#tree"))
//                println "salut " + tree
                        
                fx.clickOn(service)
                        .moveBy(320,-190)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")
        
                when:
                
                fx.clickOn("#tree")
                        .moveBy(-290,-130)
                        .clickOn()
                        .moveBy(50,50)
                        .rightClickOn()

                        .clickOn("#removeAttributeItem")
                        .clickOn("#confButton")
                then:
                true
                //TODO: merge avec gael, pour avoir l'attributeMap dans le modele et y acceder
                //tree.getRoot().getChildren().size() == 0
            }
}