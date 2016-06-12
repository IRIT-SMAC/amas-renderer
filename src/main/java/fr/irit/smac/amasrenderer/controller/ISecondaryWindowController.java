package fr.irit.smac.amasrenderer.controller;

import javafx.stage.Stage;

/**
 * Used by controller which are related to a modal window
 */
@FunctionalInterface
public interface ISecondaryWindowController {

    /**
     * Do the required stuff to init the controller
     * 
     * @param stage
     *            the stage of the controller
     * @param args
     *            the specific arguments needed to initialize the controller
     */
    void init(Stage stage, Object... args);
}