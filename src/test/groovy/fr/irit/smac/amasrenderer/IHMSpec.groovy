package fr.irit.smac.amasrenderer

import static org.testfx.api.FxAssert.verifyThat
import static org.testfx.matcher.base.NodeMatchers.hasText
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class IHMSpec extends GuiSpecification{

    /** This is an example of using TestFX **/
    
    def setup() {
        setupStage { stage ->
            def passwordField = new TextField(id: "password")
            def submitButton = new Button(id: "submit", text: "submit")
            def messageLabel = new Label(id: "message")

            submitButton.onAction = { ActionEvent event ->
                if (passwordField.text == "sheogorath") {
                    messageLabel.text = "please enter!"
                }
                else {
                    messageLabel.text = "wrong password!"
                }
            } as EventHandler
            return new HBox(passwordField, submitButton, messageLabel)
        }
    }

    def "enters gate with right password"() {
        when:
        fx.clickOn("#password").write("sheogorath")
        fx.clickOn("#submit")

        then:
        verifyThat("#message", hasText("please enter!"))
    }

    def "enters gate with wrong password"() {
        when:
        fx.clickOn("#password").write("asura")
        fx.clickOn("#submit")

        then:
        verifyThat("#message", hasText("wrong password!"))
    }
}
