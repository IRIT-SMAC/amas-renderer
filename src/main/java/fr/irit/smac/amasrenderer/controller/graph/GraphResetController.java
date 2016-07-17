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
package fr.irit.smac.amasrenderer.controller.graph;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This controller is related to the deletion of a tool
 */
public class GraphResetController implements ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    /**
     * When the confirm button is clicked, the graph is reseted
     */
    @FXML
    public void clickConfirm() {

        graphService.clearGraph();
        stage.close();
    }

    /**
     * When the cancel button is clicked, the graph is not reseted
     */
    @FXML
    public void clickCancel() {
        stage.close();
    }

    @Override
    public void init(Stage stageSecondaryWindow, Object... args) {

        stage = stageSecondaryWindow;
    }

}
