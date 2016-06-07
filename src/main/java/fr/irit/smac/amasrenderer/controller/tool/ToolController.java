package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadWindowModalController;
import fr.irit.smac.amasrenderer.model.AgentModel;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The Class ServicesController. This controller handles the service part of the
 * main UI
 */
public class ToolController extends LoadWindowModalController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<ToolModel> listTool;

    private ToolModel selectedLabel;

    private EControllerState currentController;

    private enum EControllerState {
        TOOL_ATTRIBUTES_CONTROLLER, TOOL_ADDITION_CONTROLLER
    }

    /**
     * Handle mouse click.
     */
    @FXML
    public void handleMouseClick() {

        selectedLabel = listTool.getSelectionModel().getSelectedItem();
        if (selectedLabel != null && selectedLabel.getName() != "") {
            currentController = EControllerState.TOOL_ATTRIBUTES_CONTROLLER;
            this.loadFxml(buttonAddService.getScene().getWindow(), "view/tool/ServiceAttributes.fxml");
            listTool.getSelectionModel().clearSelection();
        }
    }

    /**
     * Adds the tool.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @FXML
    public void addTool() throws IOException {

        currentController = EControllerState.TOOL_ADDITION_CONTROLLER;
        this.loadFxml(buttonAddService.getScene().getWindow(), "view/tool/ToolDialog.fxml");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<ToolModel> list = new ArrayList<>();
        for (ToolModel tool : listTool.getItems()) {
            list.add(tool);
        }
        ToolService.getInstance()
            .setTools(FXCollections.observableArrayList(actionStep -> new Observable[] { actionStep.nameProperty() }));
        listTool.setItems(ToolService.getInstance().getTools());
        ToolService.getInstance().createServicesFromMap(InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
    }

    @Override
    public void initDialogModalController() throws IOException {

        if (currentController == EControllerState.TOOL_ATTRIBUTES_CONTROLLER) {
            ToolAttributesController controller;
            controller = (ToolAttributesController) loaderWindowModal.getController();
            controller.setStage(this.stageWindowModal);
            controller.init(listTool, selectedLabel.getName(), selectedLabel);
        }
    }
}
