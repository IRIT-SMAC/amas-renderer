package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * The Class ToolService.
 */
public class ToolService {

    private ToolModel model;

    private static ToolService instance = new ToolService();

    /**
     * Instantiates a new tool service.
     */
    private ToolService() {
        model = new ToolModel();
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
    public ObservableList<String> getTools() {
        return this.model.getTools();
    }

    /**
     * Sets the tools in the model.
     *
     * @param items
     *            the new tools
     */
    public void setTools(ObservableList<String> items) {
        this.model.setTools(items);
    }

    /**
     * Creates the services from a map.
     *
     * @param map
     *            the map
     */
    public void createServicesFromMap(Map<String, Object> map) {

        this.model.getTools().clear();
        for (Map.Entry<String, Object> pair : map.entrySet()) {
            if (pair.getKey() != "className") {
                if (pair.getValue() instanceof String) {
                    this.model.addTool(pair.getKey(), (String) pair.getValue());
                }
                else {
                    this.model.addTool(pair.getKey(), (HashMap<String, Object>) pair.getValue());
                }
            }
        }

    }

    public Map<String, TreeItem<String>> getAttributes() {
        return this.model.getAttributes();
    }

    public void setAttributesMap(Map<String, TreeItem<String>> attributeMap) {
        this.model.setAttributesMap(attributeMap);
    }

}
