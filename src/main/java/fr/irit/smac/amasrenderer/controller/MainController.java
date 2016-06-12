package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController.IParentStyle;
import fr.irit.smac.amasrenderer.controller.graph.GraphController;
import fr.irit.smac.amasrenderer.controller.infrastructure.InfrastructureController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 * The Class MainController.
 */
public class MainController implements IParentStyle{

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
     * Gets the graph main controller (contains all of the sub controllers about
     * the graph).
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
        
        Window window = this.rootLayout.getScene().getWindow();
        this.graphController.setWindow(window);
        this.toolController.setWindow(window);
        this.infrastructureController.setWindow(window);
        this.menuBarController.setWindow(window);
        this.toolController.setParentStyle(this);
        this.graphController.setParentStyle(this);
        this.infrastructureController.setParentStyle(this);
    }

    @Override
    public void setBackground() {
        this.rootLayout.getStyleClass().add(Const.SECONDARY_WINDOW);
    }

    @Override
    public void setForeground() {
        this.rootLayout.getStyleClass().remove(Const.SECONDARY_WINDOW);
    }

}
