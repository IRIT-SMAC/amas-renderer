package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
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
public class ToolAdditionController implements Initializable {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldTool;

    @FXML
    private Text invalidField;

    private Stage stage;

    /**
     * Click on the confirm button handler
     */
    @FXML
    public void clickConfirm() {

        if (textfieldTool.getText() != null
            && !textfieldTool.getText().trim().isEmpty()
            && !textfieldTool.getText().trim().contains(" ")) {

            ToolModel tool = new ToolModel(textfieldTool.getText().trim().concat("Service"),
                new HashMap<String, Object>());

            boolean found = false;

            for (ToolModel item : ToolService.getInstance().getTools()) {
                if (item.getName().equals(tool.getName())) {
                    found = true;
                }
            }

            if (!found) {
                ToolService.getInstance().addTool(tool);
                InfrastructureService.getInstance().getInfrastructure().getAttributesMap().put(tool.getName(),
                    tool.getAttributesMap());
            }

            this.stage.close();
        }
        else {
            invalidField.setVisible(true);

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
        invalidField.setVisible(false);
    }
    
    public void init(Stage stage) {
        this.stage = stage;
    }
}
