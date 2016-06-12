package fr.irit.smac.amasrenderer.controller;

/**
 * Used by a controller related to a modal window which is owned by another
 * modal window
 */
@FunctionalInterface
public interface IParentWindowModal {

    /**
     * Defines the behaviour of the owner when the window is closed
     */
    public void closeWindow();
}