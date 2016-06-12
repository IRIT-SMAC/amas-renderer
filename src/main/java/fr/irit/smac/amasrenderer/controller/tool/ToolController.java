package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The Class ServicesController. This controller handles the service part of the
 * main UI
 */
public class ToolController extends LoadSecondaryWindowController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<ToolModel> listTool;

    private ToolModel selectedLabel;

    private ToolService toolService = ToolService.getInstance();

    /**
     * Handle mouse click.
     */
    @FXML
    public void handleMouseClick() {

        selectedLabel = listTool.getSelectionModel().getSelectedItem();
        if (selectedLabel != null && selectedLabel.getName() != "") {
            this.loadFxml(this.window, "view/tool/ToolAttributes.fxml", true, selectedLabel);
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

        this.loadFxml(this.window, "view/tool/ToolAddition.fxml", false);
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
        toolService
            .setTools(FXCollections.observableArrayList(actionStep -> new Observable[] { actionStep.nameProperty() }));
        listTool.setItems(toolService.getTools());
        toolService
            .createServicesFromMap(InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
    }
}
