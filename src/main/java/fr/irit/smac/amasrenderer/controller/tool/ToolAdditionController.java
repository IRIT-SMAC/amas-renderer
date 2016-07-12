/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This controller is related to the addition of a tool
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
     * When the button confirm is clicked, the tool is added if the name is
     * correct
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
     * When the cancel button is clicked, no tool is added and the modal window
     * is closed
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