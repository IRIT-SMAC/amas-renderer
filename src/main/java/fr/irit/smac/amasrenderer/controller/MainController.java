package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.controller.graph.GraphController;
import fr.irit.smac.amasrenderer.controller.infrastructure.InfrastructureController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * The Class MainController.
 */
public class MainController {

    @FXML
    private GraphController graphMainController;

    @FXML
    private ToolController toolController;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private InfrastructureController infrastructureController;

    @FXML
    private BorderPane rootLayout;

    private Scene scene;
    
    /**
     * Gets the graph main controller (contains all of the sub controllers about
     * the graph).
     *
     * @return the graph main controller
     */
    public GraphController getGraphMainController() {
        return graphMainController;
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
     * Gets the menu bar controller.
     *
     * @return the menu bar controller
     */
    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    /**
     * Gets the infrastructure controller.
     *
     * @return the infrastructure controller
     */
    public InfrastructureController getInfrastructureController() {
        return infrastructureController;
    }
    
    public void init() {
        this.scene = rootLayout.getScene();
        menuBarController.setScene(scene);
    }

}
