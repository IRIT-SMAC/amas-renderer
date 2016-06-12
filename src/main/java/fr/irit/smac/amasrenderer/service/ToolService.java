package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 * The Class ToolService.
 */
public class ToolService {

    private static ToolService        instance = new ToolService();
    private ObservableList<ToolModel> tools;

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
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().put(tool.getName(),
            tool.getAttributesMap());
        tool.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                InfrastructureService.getInstance().getInfrastructure().getAttributesMap().remove(oldValue);
                InfrastructureService.getInstance().getInfrastructure().getAttributesMap().put(newValue,
                    tool.getAttributesMap());
            });
    }

    /**
     * Creates the tools from a map.
     *
     * @param map
     *            the map
     */
    public void createToolsFromMap(Map<String, Object> map) {

        this.getTools().clear();

        for (Map.Entry<String, Object> pair : map.entrySet()) {
            if (pair.getKey().contains(Const.TOOL)) {
                this.addTool(new ToolModel(pair.getKey(), pair.getValue()));
            }
        }
    }
}
