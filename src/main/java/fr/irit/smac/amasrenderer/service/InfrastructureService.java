package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * The Class InfrastructureService.
 */
public class InfrastructureService {

    private ObservableList<InfrastructureModel> infrastructure;

    private static InfrastructureService instance = new InfrastructureService();

    /**
     * Instantiates a new infrastructure service.
     */
    private InfrastructureService() {
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
    public ObservableList<InfrastructureModel> getInfrastructure() {
        return this.infrastructure;
    }

    /**
     * Edits the infrastructure.
     *
     * @param infrastructure
     *            the infrastructure
     */
    public void editInfrastructure(InfrastructureModel infrastructure) {
        this.infrastructure.add(0, infrastructure);
    }

    /**
     * Sets the infrastructure.
     *
     * @param infrastructure
     *            the new infrastructure
     */
    public void setInfrastructure(ObservableList<InfrastructureModel> infrastructure) {
        this.infrastructure = infrastructure;
    }

    /**
     * Creates the infrastructures from map.
     *
     * @param map
     *            the map
     */
    public void createInfrastructureFromMap(Map<String, Object> map) {

        this.infrastructure.clear();
        this.editInfrastructure(new InfrastructureModel("infra", (Map<String, Object>) map));
    }

    public void updateInfrastructureMap(String id, TreeItem<String> item, InfrastructureModel infrastructure) {

        infrastructure.getAttributesMap().clear();
        for (TreeItem<String> subItem : item.getChildren()) {

            String[] splitItem = ((String) subItem.getValue()).split(" : ");
            String keyItem = splitItem[0];
            this.updateSingleAttributeInfrastructure(subItem, infrastructure.getAttributesMap(), keyItem);
        }
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

        }
        else {

            String[] splitItem = ((String) item.getValue()).split(" : ");
            String value = splitItem[1];
            map.put(key, value);
        }
    }
    
    public void init() {
        
        this.infrastructure.add(new InfrastructureModel("lala", new HashMap<String, Object>()));

        String infrastructureClassname = "BasicInfrastructure";
        Map<String,Object> map = this.infrastructure.get(0).getAttributesMap();
        map.put("className", infrastructureClassname);
        HashMap<String, Object> agentHandlerService = new HashMap<String, Object>();
        HashMap<String, Object> agentMap = new HashMap<String, Object>();

        agentHandlerService.put("className", "fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler");
        agentHandlerService.put("agentMap", agentMap);
        map.put("agentHandlerService", agentHandlerService);

        ToolService.getInstance().createServicesFromMap(map);
        GraphService.getInstance().getGraph().setAgentMap(agentMap);
    }

}
