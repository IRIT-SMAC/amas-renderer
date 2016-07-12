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
package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.controller.graph.GraphController;
import fr.irit.smac.amasrenderer.controller.infrastructure.InfrastructureController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 * This controller contains all the sub-controllers of the main window
 */
public class MainController {

    @FXML
    private GraphController graphController;

    @FXML
    private ToolController toolController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private InfrastructureController infrastructureController;

    @FXML
    private BorderPane rootLayout;

    /**
     * Gets the graph main controller
     *
     * @return the graph main controller
     */
    public GraphController getGraphController() {
        return graphController;
    }

    /**
     * Gets the tools controller
     *
     * @return the tools controller
     */
    public ToolController getToolController() {
        return toolController;
    }

    /**
     * Gets the menu bar controller
     *
     * @return the menu bar controller
     */
    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    /**
     * Gets the infrastructure controller
     *
     * @return the infrastructure controller
     */
    public InfrastructureController getInfrastructureController() {
        return infrastructureController;
    }

    /**
     * Do some stuffs after all the creation of all the sub-controllers
     */
    public void init() {

        Window window = this.rootLayout.getScene().getWindow();
        this.graphController.setWindow(window);
        this.toolController.setWindow(window);
        this.infrastructureController.setWindow(window);
        this.menuBarController.setWindow(window);
    }
}
