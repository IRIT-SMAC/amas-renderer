package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.controller.graph.GraphMainController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private GraphMainController graphMainController;

    @FXML
    private ToolController toolController;

    @FXML
    private MenuBarController menuBarController;

    public GraphMainController getGraphMainController() {
        return graphMainController;
    }

    public ToolController getServicesController() {
        return toolController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }
}
