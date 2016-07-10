package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;

/**
 * This service is related to the business logic about the infrastructure
 */
public class InfrastructureService {

    private InfrastructureModel infrastructure;

    private static InfrastructureService instance = new InfrastructureService();

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
     * Updates the infrastructure depending on the infrastructure of a JSON file
     * 
     * @param infrastructureFile
     */
    public void updateInfrastructureFromFile(InfrastructureModel infrastructureFile) {

        String[] infrastructureName = infrastructureFile.getAttributesMap().get(Const.CLASSNAME).toString()
            .split("\\.");
        InfrastructureService.getInstance().getInfrastructure()
            .setName(infrastructureName[infrastructureName.length - 1]);
        InfrastructureService.getInstance().getInfrastructure().setAttributes(infrastructureFile.getAttributesMap());

        @SuppressWarnings("unchecked")
        Map<String, Object> toolMap = (Map<String, Object>) this.infrastructure.getAttributesMap().get("services");
        ToolService.getInstance().updateGraphFromFile(toolMap);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> agentMap = (HashMap<String, Object>) ((Map<String, Object>) toolMap
            .get(Const.AGENT_HANDLER_SERVICE)).get(Const.AGENT_MAP);
        GraphService.getInstance().updateGraphFromFile(agentMap);
    }

    /**
     * Initialize a default infrastructure
     */
    public void init() {

        Map<String, Object> attributesMap = new HashMap<>();

        this.infrastructure = new InfrastructureModel(Const.INFRASTRUCTURE_NAME, attributesMap);
        attributesMap.put(Const.CLASSNAME, Const.INFRASTRUCTURE_CLASSNAME);

        Map<String, Object> toolMap = new HashMap<>();
        attributesMap.put("services", toolMap);

        ToolService.getInstance().init(toolMap);
    }
}