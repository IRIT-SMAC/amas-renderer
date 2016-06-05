package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadWindowModalController;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class InfrastructureController extends LoadWindowModalController implements Initializable {

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

        this.loadFxml(infrastructureButton.getScene().getWindow(), "view/infrastructure/InfrastructureAttributes.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        InfrastructureService.getInstance().getInfrastructure();
    }

    @Override
    public void initDialogModalController() throws IOException {

        InfrastructureAttributesController controller = this.loaderWindowModal.getController();
        controller.setStage(this.stageWindowModal);
        controller.init();
    }

}
