package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.controller.graph.EStateGraph;
import fr.irit.smac.amasrenderer.controller.graph.GraphMainController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import fr.irit.smac.amasrenderer.controller.tool.ToolController;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

/**
 * The Class MainController.
 */
public class MainController {

    @FXML
    private GraphMainController graphMainController;

    @FXML
    private ToolController toolController;

    @FXML
    private MenuBarController menuBarController;


    @FXML
    public void handleOnKeyPressed(KeyEvent e) {
        switch (GraphMainController.state) {
            case AT_EASE:
                if (e.isControlDown()) {
                    GraphMainController.state = EStateGraph.CTRL_DOWN;
                }
                else if (e.isShiftDown()) {
                    GraphMainController.state = EStateGraph.SHIFT_DOWN;
                }
            default:
                break;

        }
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent e) {

        switch (GraphMainController.state) {
            case CTRL_DOWN:
                GraphMainController.state = EStateGraph.AT_EASE;

            case SHIFT_DOWN:
                GraphMainController.state = EStateGraph.AT_EASE;
            default:
                break;

        }
    }
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
}
