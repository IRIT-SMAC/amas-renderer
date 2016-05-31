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
        ConfigurationMapService.getInstance().getModel().getConfigurationMap().put(tool.getName(), tool.getAttributesMap());
        System.out.println(ConfigurationMapService.getInstance().getModel().getConfigurationMap());
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

    public void updateToolsMap(String id, TreeItem<String> item, ToolModel tool) {

        for (TreeItem<String> subItem : item.getChildren()) {
            
            String[] splitItem = ((String) subItem.getValue()).split(" : ");
            String keyItem = splitItem[0];
            this.updateSingleToolMap(subItem, tool.getAttributesMap(), keyItem);
        }

    }

    public void updateSingleToolMap(TreeItem<String> item, Map<String, Object> map, String key) {

        ObservableList<TreeItem<String>> node = item.getChildren();

        
        if (node.size() > 0) {
            Map<String, Object> newServiceMap = new HashMap<String, Object>();
            for (TreeItem<String> subItem : node) {

                String[] splitItem = ((String) subItem.getValue()).split(" : ");
                String keyItem = splitItem[0];
                updateSingleToolMap(subItem, newServiceMap, keyItem);

            }
            map.put(key, newServiceMap);

        }
        else {

            String[] splitItem = ((String) item.getValue()).split(" : ");
            String value = splitItem[1];
            map.put(key, value);
        }
    }
}
