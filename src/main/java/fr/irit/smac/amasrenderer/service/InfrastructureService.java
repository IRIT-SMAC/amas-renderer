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

    public void updateInfrastructureFromModel(InfrastructureModel infrastructureFile) {

        String[] infrastructureName = infrastructureFile.getAttributesMap().get(Const.CLASSNAME).toString().split("\\.");
        InfrastructureService.getInstance().getInfrastructure()
            .setName(infrastructureName[infrastructureName.length - 1]);
        InfrastructureService.getInstance().getInfrastructure().setAttributes(infrastructureFile.getAttributesMap());
        Map<String, Object> agentHandlerService = (HashMap<String, Object>) this.infrastructure.getAttributesMap()
            .get("agentHandlerService");
        Map<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");
        GraphService.getInstance().createAgentGraphFromMap(agentMap);
        ToolService.getInstance().createServicesFromMap(
            InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
        GraphService.getInstance().setQualityGraph();
    }

    public void init() {

        this.infrastructure = new InfrastructureModel("lala", new HashMap<String, Object>());

        String infrastructureClassname = "BasicInfrastructure";
        Map<String, Object> map = this.infrastructure.getAttributesMap();
        map.put(Const.CLASSNAME, infrastructureClassname);
        Map<String, Object> agentHandlerService = new HashMap<>();
        Map<String, Object> agentMap = new HashMap<>();

        agentHandlerService.put(Const.CLASSNAME, "fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler");
        agentHandlerService.put("agentMap", agentMap);
        map.put("agentHandlerService", agentHandlerService);

        GraphService.getInstance().setAgentMap(agentMap);
    }

}
