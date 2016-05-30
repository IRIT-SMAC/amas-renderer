package fr.irit.smac.amasrenderer

import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import fr.irit.smac.amasrenderer.service.GraphService

//@IgnoreIf({
//    System.getenv("TRAVIS") != null
//})
@Ignore
//@Stepwise
class InfrastructureTest extends GuiSpecification{

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
            Main.mainStage = stage

            return rootLayout
        }


        sleep(1000) //time for the graph to be initialized
        graphService = GraphService.getInstance()

    }

    def "check if the infrastructure is modified by doubleclicking on the textfield"() {

        when:
        println "infrastructure through textfield"
        fx.doubleClickOn("#infrastructureLabel")
                        .write("Michel c'est le Bresil!")
                        .type(KeyCode.ENTER)

        then:
        Label label = (Label) rootLayout.lookup("#infrastructureLabel")
        TextField txtField = (TextField) rootLayout.lookup("#infrastructureTextField")
        "Michel c'est le Bresil!" == label.getText() && label.getText() == txtField.getText()
    }
    
    def "check if the infrastructure is modified by using the button"() {
        
        when:
        println "infrastructure through textfield"
        fx.clickOn("#infrastructureButton")
                        .write("Il danse la Samba!")
                        .type(KeyCode.ENTER)

        then:
        Label label = (Label) rootLayout.lookup("#infrastructureLabel")
        TextField txtField = (TextField) rootLayout.lookup("#infrastructureTextField")
        "Il danse la Samba!" == label.getText() && label.getText() == txtField.getText()
    }
    def "check if the infrastructure warning shows if empty entry"() {
        
        when:
        println "infrastructure through textfield"
        fx.clickOn("#infrastructureButton")
                        .write("")
                        .type(KeyCode.ENTER)

        then:
        ImageView icon = (ImageView) rootLayout.lookup("#infrastructureWarningIcon")
        Label label = (Label) rootLayout.lookup("#infrastructureWarningLabel")
        icon.isVisible() && label.isVisible()
    }
}
