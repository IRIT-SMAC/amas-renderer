package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * This service is related to the business logic about the tools
 */
public class ToolService {

    private ObservableList<ToolModel> tools;

    private Map<String, Object> toolMap;

    private static ToolService instance = new ToolService();

    private ToolService() {
    }

    /**
     * Gets the single instance of ToolService.
     *
     * @return single instance of ToolService
     */
    public static ToolService getInstance() {
        return instance;
    }

    /**
     * Gets the tools
     *
     * @return the tools
     */
    public ObservableList<ToolModel> getTools() {
        return this.tools;
    }

    /**
     * Sets the tools
     *
     * @param items
     *            the new tools
     */
    public void setTools(ObservableList<ToolModel> tools) {
        this.tools = tools;
    }

    /**
     * Adds a tool
     * 
     * @param tool
     *            the tool
     */
    public void addTool(ToolModel tool) {

        this.tools.add(tool);
        toolMap.put(tool.getName(),
            tool.getAttributesMap());
        tool.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                toolMap.remove(oldValue);
                toolMap.put(newValue,
                    tool.getAttributesMap());
            });
    }

    /**
     * Creates the tools from a tool map.
     *
     * @param toolMap
     *            the map
     */
    public void createToolsFromMap(Map<String, Object> toolMap) {

        this.getTools().clear();

        this.toolMap = toolMap;
        for (Map.Entry<String, Object> pair : toolMap.entrySet()) {
            if (pair.getKey().contains(Const.TOOL)) {
                this.addTool(new ToolModel(pair.getKey(), pair.getValue()));
            }
        }
    }

    /**
     * Remove a tool from the list of tools
     * 
     * @param tool
     *            the tool to remove
     */
    public void removeTool(ToolModel tool) {
        this.getTools().remove(tool);
    }

    /**
     * Remove all the tools
     */
    public void removeTools() {
        this.getTools().clear();
    }

    public Map<String, Object> getToolMap() {
        return toolMap;
    }

    public void setToolMap(Map<String, Object> toolMap) {
        this.toolMap = toolMap;
    }
}
