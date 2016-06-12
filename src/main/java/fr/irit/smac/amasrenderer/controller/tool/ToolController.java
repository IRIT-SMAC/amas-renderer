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
 * This controller is related to the tools in the main window
 */
public class ToolController extends LoadSecondaryWindowController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<ToolModel> listTool;

    private ToolModel selectedLabel;

    private ToolService toolService = ToolService.getInstance();

    /**
     * When a tool is clicked, a modal window showing its attributes is opened
     */
    @FXML
    public void clickOnToolList() {

        this.selectedLabel = this.listTool.getSelectionModel().getSelectedItem();
        if (this.selectedLabel != null && this.selectedLabel.getName() != "") {
            this.loadFxml(this.window, "view/tool/ToolAttributes.fxml", true, this.selectedLabel);
            this.listTool.getSelectionModel().clearSelection();
        }
    }

    /**
     * When the add tool button is clicked, open a modal window allowing to
     * enter the name of the new tool
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
        for (ToolModel tool : this.listTool.getItems()) {
            list.add(tool);
        }
        this.toolService
            .setTools(FXCollections.observableArrayList(actionStep -> new Observable[] { actionStep.nameProperty() }));
        this.listTool.setItems(this.toolService.getTools());
        this.toolService
            .createToolsFromMap(InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
    }
}