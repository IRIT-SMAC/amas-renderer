package fr.irit.smac.amasrenderer.controller.help;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * This controller is related to the documentation of the program
 */
public class DocumentationController implements Initializable {

    @FXML
    WebView webView;

    @FXML
    ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        WebEngine engine = webView.getEngine();
        engine.load(DocumentationController.class.getResource("../../html/documentation.html").toExternalForm());

        progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());

        engine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
                @Override
                public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                        progressBar.setVisible(false);
                    }
                }
            });
    }
}
