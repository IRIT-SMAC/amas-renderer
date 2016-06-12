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
    InfrastructureService infrastructureService

    @Shared
    String itemId = "className"

    def setup() {
        setupStage { stage ->

            FXMLLoader loaderRootLayout = new FXMLLoader()
            loaderRootLayout.setLocation(AmasRenderer.class.getResource("view/RootLayout.fxml"))
            BorderPane rootLayout = (BorderPane) loaderRootLayout.load()

            return rootLayout
        }


        sleep(1000) //time for the graph to be initialized
        infrastructureService = InfrastructureService.getInstance()
    }

    @IgnoreIf({
        System.getenv("TRAVIS") != null
    })
    def "check if the infrastructure is modified by doubleclicking on the textfield"() {

        given:
        String extraName = "Hello"

        when:
        fx.doubleClickOn("#editInfrastructure")
                        .clickOn("#tree")
                        .rightClickOn(itemId)
                        .clickOn("#renameAttributeItem")
                        .clickOn(itemId)
                        .write(extraName)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        Map<String,Object> infrastructure = infrastructureService.getInfrastructure().getAttributesMap()
        infrastructure.get(itemId + extraName) != null
    }
}
