package fr.irit.smac.amasrenderer.model;

import javafx.collections.ObservableList;

/**
 * The Class ToolModel.
 */
public class ToolModel {

    private ObservableList<String> tools;

    /**
     * Gets the tools.
     *
     * @return the tools
     */
    public ObservableList<String> getTools() {
        return tools;
    }

    /**
     * Sets the tools.
     *
     * @param items
     *            the new tools
     */
    public void setTools(ObservableList<String> items) {
        this.tools = items;
    }

    /**
     * Adds a tool.
     *
     * @param name
     *            the name
     */
    public void addTool(String name) {
        this.tools.add(name);
    }
}
