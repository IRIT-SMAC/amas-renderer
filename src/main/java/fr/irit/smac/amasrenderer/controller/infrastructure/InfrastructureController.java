package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class InfrastructureController extends LoadSecondaryWindowController implements Initializable {

    @FXML
    private Label infrastructureLabel;

    @FXML
    private Button infrastructureButton;

    @FXML
    private Label infrastructureWarningLabel;

    @FXML
    private ImageView infrastructureWarningIcon;

    /**
     * Handle mouse click.
     */
    @FXML
    public void handleInfraClick() {

        this.loadFxml(this.window, "view/infrastructure/InfrastructureAttributes.fxml", true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        InfrastructureService.getInstance().init();

        InfrastructureService.getInstance().getInfrastructure().nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> infrastructureLabel.setText(newValue));
    }

    @Override
    public void initDialogModalController() throws IOException {

        InfrastructureAttributesController controller = this.loaderWindowModal.getController();
        controller.setStage(this.stageWindowModal);
        controller.init();
    }
}
