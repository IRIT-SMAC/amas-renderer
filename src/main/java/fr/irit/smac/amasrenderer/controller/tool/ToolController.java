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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.tool.Tool;
import fr.irit.smac.amasrenderer.service.ToolService;
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
    private ListView<Tool> listTool;

    private Tool selectedLabel;

    private ToolService toolService = ToolService.getInstance();

    /**
     * When a tool is clicked, a modal window showing its attributes is opened
     */
    @FXML
    public void clickOnToolList() {

        selectedLabel = listTool.getSelectionModel().getSelectedItem();
        if (selectedLabel != null && selectedLabel.getName() != "") {
            loadFxml(window, "view/tool/ToolAttributes.fxml", true, selectedLabel);
            listTool.getSelectionModel().clearSelection();
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

        loadFxml(window, "view/tool/ToolAddition.fxml", false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listTool.setItems(toolService.getTools());
    }
}