package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
@Stepwise
class InfrastructureTest extends GuiSpecification{

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

    def "check if the infrastructure is modified by doubleclicking on the textfield"() {

        when:
        fx.doubleClickOn("#editInfrastructure")
                        .clickOn("#tree")
                        .moveBy(positionX, positionY)
                        .rightClickOn()
                        .clickOn("#renameAttributeItem")
                        .type(KeyCode.E)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        String infrastructureName = InfrastructureService.getInstance().getInfrastructure().getName()
        infrastructureName == "e" ||  infrastructureName == "E"
    }
}
