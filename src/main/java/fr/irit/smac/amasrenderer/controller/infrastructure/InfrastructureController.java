package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This controller is related to the infrastructure on the main window
 */
public class InfrastructureController extends LoadSecondaryWindowController implements Initializable {

    @FXML
    private Label infrastructureLabel;

    @FXML
    private Button infrastructureButton;

    private InfrastructureService infrastructureService = InfrastructureService.getInstance();

    /**
     * When the edit button is clicked, a modal window allowing to see and
     * update the attributes is opened
     */
    @FXML
    public void handleOnEditInfraClick() {

        this.loadFxml(this.window, "view/infrastructure/InfrastructureAttributes.fxml", true,
            this.infrastructureService.getInfrastructure());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.infrastructureService.init();

        this.infrastructureService.getInfrastructure().nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> infrastructureLabel.setText(newValue));
    }
}
