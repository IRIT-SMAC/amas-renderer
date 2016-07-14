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

import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.tool.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This controller is related to the deletion of a tool
 */
public class ToolDeletionController implements ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    private String toolName;

    private ToolModel tool;

    private Stage stage;

    private IParentWindowModal parentWindowModal;

    private ToolService toolService = ToolService.getInstance();

    /**
     * When the confirm button is clicked, the selected tool is deleted
     */
    @FXML
    public void clickConfirm() {

        this.toolService.removeTool(this.tool);
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().remove(this.toolName);
        this.parentWindowModal.closeWindow();
    }

    /**
     * When the cancel button is clicked, no tool is removed and the window is
     * closed
     */
    @FXML
    public void clickCancel() {
        this.stage.close();
    }

    /**
     * Sets the owner of the window. This attribute is useful when this window
     * is closed because the owner window has to be closed at the same time.
     * 
     * @param parentWindowModal
     */
    public void setParentWindowModal(IParentWindowModal parentWindowModal) {
        this.parentWindowModal = parentWindowModal;
    }

    @Override
    public void init(Stage stageSecondaryWindow, Object... args) {

        this.stage = stageSecondaryWindow;
        this.parentWindowModal = (IParentWindowModal) args[0];
        this.tool = (ToolModel) args[1];
        this.toolName = this.tool.getName();
    }

}
