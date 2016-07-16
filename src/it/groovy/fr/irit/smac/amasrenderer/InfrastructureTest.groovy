package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.model.InfrastructureModel
import fr.irit.smac.amasrenderer.service.InfrastructureService

@IgnoreIf({
    System.getenv("TRAVIS") != null
})
@Stepwise
class InfrastructureTest extends GuiSpecification{

    @Shared
    InfrastructureService infrastructureService

    @Shared
    String itemId = "className : fr.irit.smac.amasfactory.impl.BasicInfrastructure"

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
    def "check if an attribute of the infrastructure is updated"() {

        given:
        String extraName = "Hello"

        when:
        fx.clickOn("#editInfrastructure")
                        .rightClickOn(itemId)
                        .clickOn("#renameAttributeItem")
                        .clickOn(itemId)
                        .write(extraName)
                        .type(KeyCode.ENTER)
                        .clickOn("#confButton")

        then:
        InfrastructureModel infrastructure = infrastructureService.getInfrastructure()
        infrastructure.getAttributesMap().get(Const.CLASSNAME).contains("Hello")
    }
}
