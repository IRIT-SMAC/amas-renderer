package fr.irit.smac.amasrenderer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.graph.GraphMainController;
import fr.irit.smac.amasrenderer.controller.infrastructure.InfrastructureController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * The Class MainController.
 */
public class MainController implements Initializable {

    @FXML
    private GraphMainController graphMainController;

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
    public GraphMainController getGraphMainController() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        InfrastructureService.getInstance().init();
    }

}
