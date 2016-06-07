package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;

/**
 * The Class InfrastructureService.
 */
public class InfrastructureService {

    private InfrastructureModel infrastructure;

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
    public InfrastructureModel getInfrastructure() {
        return this.infrastructure;
    }
    
    /**
     * Sets the infrastructure.
     *
     * @param infrastructure
     *            the new infrastructure
     */
    public void setInfrastructure(InfrastructureModel infrastructure) {
        this.infrastructure = infrastructure;
    }

    /**
     * Creates the infrastructures from map.
     *
     * @param map
     *            the map
     */
    public void createInfrastructureFromMap(Map<String, Object> map) {

        this.setInfrastructure(new InfrastructureModel("infra", (Map<String, Object>) map));
    }
    
    public void init() {
        
        this.infrastructure = new InfrastructureModel("lala", new HashMap<String, Object>());

        String infrastructureClassname = "BasicInfrastructure";
        Map<String,Object> map = this.infrastructure.getAttributesMap();
        map.put("className", infrastructureClassname);
        HashMap<String, Object> agentHandlerService = new HashMap<String, Object>();
        HashMap<String, Object> agentMap = new HashMap<String, Object>();

        agentHandlerService.put("className", "fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler");
        agentHandlerService.put("agentMap", agentMap);
        map.put("agentHandlerService", agentHandlerService);

        GraphService.getInstance().setAgentMap(agentMap);
    }

}
