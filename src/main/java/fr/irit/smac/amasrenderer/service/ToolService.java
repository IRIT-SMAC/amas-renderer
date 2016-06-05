package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.collections.ObservableList;

/**
 * The Class ToolService.
 */
public class ToolService {

    private static ToolService instance = new ToolService();
    private ObservableList<ToolModel> tools;

    /**
     * Instantiates a new tool service.
     */
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
     * Gets the tools from to the model.
     *
     * @return the tools
     */
    public ObservableList<ToolModel> getTools() {
        return this.tools;
    }

    /**
     * Sets the tools in the model.
     *
     * @param items
     *            the new tools
     */
    public void setTools(ObservableList<ToolModel> tools) {
        this.tools = tools;
    }

    public void addTool(ToolModel tool) {

        this.tools.add(tool);
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().put(tool.getName(), tool.getAttributesMap());
    }
    
    /**
     * Creates the services from a map.
     *
     * @param map
     *            the map
     */
    public void createServicesFromMap(Map<String, Object> map) {

        this.getTools().clear();

        for (Map.Entry<String, Object> pair : map.entrySet()) {
            if (pair.getKey() != "className") {
                this.addTool(new ToolModel(pair.getKey(),pair.getValue()));
            }
        }
    }
}
