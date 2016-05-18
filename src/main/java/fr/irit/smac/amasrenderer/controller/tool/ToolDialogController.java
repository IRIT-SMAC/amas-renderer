package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class ServiceDialogController This controller manages the popup form
 */
public class ToolDialogController implements Initializable {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldTool;

    private Map<String, TreeItem<String>> map;

    @FXML
    private Text invalidField;

    /**
     * Click on the confirm button handler
     */
    @FXML
    public void clickConfirm() {

        if (textfieldTool.getText() != null
            && !textfieldTool.getText().trim().isEmpty()
            && !textfieldTool.getText().trim().contains(" ")) {

            String newTool = textfieldTool.getText().trim();

            boolean found = false;

            for (String item : ToolService.getInstance().getTools()) {
                if (item.equals(newTool)) {
                    found = true;
                }
            }

            if (!found) {
                ToolService.getInstance().getTools().add(newTool);
            }

            ((Stage) buttonConfirm.getScene().getWindow()).close();
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
        ((Stage) buttonCancel.getScene().getWindow()).close();
    }

    public void setAttributeMap(Map<String, TreeItem<String>> attributeMap) {
        this.map = attributeMap;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidField.setVisible(false);

    }
}
