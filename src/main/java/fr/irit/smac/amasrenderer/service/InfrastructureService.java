package fr.irit.smac.amasrenderer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * The Class InfrastructureService.
 */
public class InfrastructureService {

    private InfrastructureModel model;

    private static InfrastructureService instance = new InfrastructureService();

    /**
     * Instantiates a new infrastructure service.
     */
    private InfrastructureService() {
        model = new InfrastructureModel();
    }

    /**
     * Gets the single instance of InfrastructureService.
     *
     * @return single instance of InfrastructureService
     */
    public static InfrastructureService getInstance() {
        return instance;
    }

    /**
     * Gets the infrastructure.
     *
     * @return the infrastructure
     */
    public ObservableList<String> getInfrastructure() {
        return this.model.getInfrastructure();
    }

    /**
     * Edits the infrastructure.
     *
     * @param name
     *            the name
     */
    public void editInfrastructure(String name) {
        this.model.editInfrastructure(name);
    }

    /**
     * Sets the infrastructure.
     *
     * @param list
     *            the new infrastructure
     */
    public void setInfrastructure(ObservableList<String> list) {
        this.model.setInfrastructure(list);
    }

    /**
     * Creates the infrastructures from map.
     *
     * @param map
     *            the map
     */
    public void createInfrastructureFromMap(Map<String, Object> map) {

        this.model.getInfrastructure().clear();
        
        for (Map.Entry<String, Object> pair : map.entrySet()) {
            
            if (!pair.getKey().contains("Service")) {
                if (pair.getKey().contains("className")) {
                    String[] splitItem = ((String) pair.getValue()).split("\\.");
                    this.model.editInfrastructure(splitItem[splitItem.length-1]);
                }
            }
            this.model.getInfrastructureMap().put(pair.getKey(), pair.getValue());

        }
    }

    public void updateInfrastructureMap(String id, TreeItem<String> item) {

        this.model.getInfrastructureMap().remove(id);
        Map<String, Object> singleServiceMap = new HashMap<String, Object>();

        this.updateSingleAttributeInfrastructure(item, singleServiceMap, id);
        this.model.getInfrastructureMap().put(id, singleServiceMap.get(id));
    }
    
    public void updateSingleAttributeInfrastructure(TreeItem<String> item, Map<String, Object> map, String key) {

        ObservableList<TreeItem<String>> node = item.getChildren();

        if (node.size() > 0) {
            Map<String, Object> newServiceMap = new HashMap<String, Object>();
            for (TreeItem<String> subItem : node) {

                String[] splitItem = ((String) subItem.getValue()).split(" : ");
                String keyItem = splitItem[0];
                updateSingleAttributeInfrastructure(subItem, newServiceMap, keyItem);

            }
            map.put(key, newServiceMap);

        } else {

            String[] splitItem = ((String) item.getValue()).split(" : ");
            String value = splitItem[1];
            map.put(key, value);
        }
    }
    
    public Map<String, Object> getInfrastructureMap() {

        return this.model.getInfrastructureMap();
    }

    public void setInfrastructureMap(Map<String, Object> infrastructureMap) {

        System.out.println(infrastructureMap);
        this.model.setInfrastructureMap(infrastructureMap);
    }

}
