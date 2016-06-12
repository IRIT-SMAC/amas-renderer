package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class ServiceDialogController This controller manages the popup form
 */
public class ToolAdditionController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldTool;

    @FXML
    private Text invalidField;

    private Stage stage;

    private ToolService toolService = ToolService.getInstance();

    /**
     * Click on the confirm button handler
     */
    @FXML
    public void clickConfirm() {

        String toolName = textfieldTool.getText();
        if (toolName != null
            && !toolName.trim().isEmpty()
            && !toolName.trim().contains(" ")) {

            ToolModel tool = new ToolModel(toolName,
                new HashMap<String, Object>());

            boolean found = false;
            for (ToolModel item : ToolService.getInstance().getTools()) {
                if (item.getName().equals(tool.getName())) {
                    found = true;
                }
            }

            if (!found) {
                toolService.addTool(tool);
            }

            this.stage.close();
        }
        else {
            this.invalidField.setVisible(true);
        }
    }

    /**
     * Click on the cancel button handler
     */
    @FXML
    public void clickCancel() {
        this.stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.invalidField.setVisible(false);
    }

    @Override
    public void init(Stage stage, Object... args) {
        this.stage = stage;
    }
}