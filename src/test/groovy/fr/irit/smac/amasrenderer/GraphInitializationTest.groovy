package fr.irit.smac.amasrenderer

import javafx.collections.FXCollections
import spock.lang.Shared
import spock.lang.Specification

import com.fasterxml.jackson.databind.ObjectMapper

import fr.irit.smac.amasrenderer.model.InfrastructureModel
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService
import fr.irit.smac.amasrenderer.service.ToolService

/**
 * The Class GraphServiceTest.
 */
class GraphInitializationTest extends Specification{

    @Shared GraphService graphNodeService
    @Shared ToolService toolService
    @Shared InfrastructureService infrastructureService

    /**
     * Setup spec.
     *
     * @return the java.lang. object
     */
    def setupSpec() {

        graphNodeService = GraphService.getInstance()
        toolService = ToolService.getInstance()
        toolService.setTools(FXCollections.observableArrayList(new ArrayList<>()))
        infrastructureService = InfrastructureService.getInstance()
        graphNodeService.createAgentGraph()
        GraphService.getInstance().setAgentMap(new HashMap<>())
        infrastructureService.setInfrastructure(new InfrastructureModel("infrastructure", new HashMap<String,Object>()))
    }

    def 'check if models are correctly created from a loaded json file'() {

        when:
        InputStream json = ClassLoader.getSystemResourceAsStream("./1infra5services12agents.json")
        ObjectMapper mapper = new ObjectMapper()
        InfrastructureModel infrastructure = mapper.readValue(json,InfrastructureModel.class)
        InfrastructureService.getInstance().updateInfrastructureFromModel(infrastructure)

        then:
        graphNodeService.getGraph().getNodeCount() == 12
        infrastructureService.getInfrastructure().getName() == "BasicInfrastructure"
        toolService.getTools().size() == 5
    }
}