package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.control.ListView
import javafx.scene.control.TreeItem
import javafx.scene.layout.BorderPane
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.service.GraphService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
@Stepwise
class ToolAttributeTest extends GuiSpecification{

    @Shared
    graphService

    @Shared
    BorderPane rootLayout

    @Shared
    double positionX

    @Shared
    double positionY

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()
            this.rootLayout = rootLayout
            Main.mainStage = stage

            return rootLayout
        }

        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

        double height = 450
        double width = 630
        positionX = -(width/2)+20
        positionY = -(height/2) + 70
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
                        .clickOn("#attributesServiceDialog")
                        .moveBy(positionX,positionY)
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
                        .clickOn("#attributesServiceDialog")
                        .moveBy(positionX,positionY)
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
                        .clickOn("#attributesServiceDialog")
                        .moveBy(positionX,positionY)
                        .rightClickOn()
                        .clickOn("#addAttributeItem")

        when:
        fx.clickOn("#tree")
                        .moveBy(positionX+5, positionY+25)
                        .clickOn()
                        fx.moveBy(20, 40)
                        .rightClickOn()

                        .clickOn("#removeAttributeItem")
                        .clickOn("#confButton")
        then:
        true
        //TODO: merge avec gael, pour avoir l'attributeMap dans le modele et y acceder
        //tree.getRoot().getChildren().size() == 0
    }
}
