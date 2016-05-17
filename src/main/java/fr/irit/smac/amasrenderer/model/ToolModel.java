package fr.irit.smac.amasrenderer.model;

import javafx.collections.ObservableList;

public class ToolModel {

    private ObservableList<String> tools;

    public ObservableList<String> getTools() {
        return tools;
    }

    public void setTools(ObservableList<String> items) {
        this.tools = items;
    }

    public void addTool(String name) {
        this.tools.add(name);
    }
}
