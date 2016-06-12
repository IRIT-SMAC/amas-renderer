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
