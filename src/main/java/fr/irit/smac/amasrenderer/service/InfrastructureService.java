package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
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

    @SuppressWarnings("unchecked")
    public void updateInfrastructureFromModel(InfrastructureModel infrastructureFile) {

        String[] infrastructureName = infrastructureFile.getAttributesMap().get(Const.CLASSNAME).toString()
            .split("\\.");
        InfrastructureService.getInstance().getInfrastructure()
            .setName(infrastructureName[infrastructureName.length - 1]);
        InfrastructureService.getInstance().getInfrastructure().setAttributes(infrastructureFile.getAttributesMap());
        Map<String, Object> agentHandlerService = (HashMap<String, Object>) this.infrastructure.getAttributesMap()
            .get(Const.AGENT_HANDLER_SERVICE);
        Map<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get(Const.AGENT_MAP);
        GraphService.getInstance().createAgentGraphFromMap(agentMap);
        ToolService.getInstance().createServicesFromMap(
            InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
        GraphService.getInstance().setQualityGraph();
    }

    public void init() {

        Map<String, Object> attributesMap = new HashMap<String, Object>();
        
        this.infrastructure = new InfrastructureModel(Const.INFRASTRUCTURE_NAME, attributesMap);
        attributesMap.put(Const.CLASSNAME, Const.INFRASTRUCTURE_CLASSNAME);

        Map<String, Object> agentHandlerService = new HashMap<>();
        Map<String, Object> agentMap = new HashMap<>();
        agentHandlerService.put(Const.CLASSNAME, Const.AGENT_HANDLER_CLASSNAME);
        agentHandlerService.put(Const.AGENT_MAP, agentMap);
        
        Map<String, Object> executionService = new HashMap<>();
        executionService.put(Const.CLASSNAME, Const.EXECUTION_CLASSNAME);

        attributesMap.put(Const.AGENT_HANDLER_SERVICE, agentHandlerService);
        attributesMap.put(Const.EXECUTION_SERVICE, executionService);

        GraphService.getInstance().setAgentMap(agentMap);
    }
}