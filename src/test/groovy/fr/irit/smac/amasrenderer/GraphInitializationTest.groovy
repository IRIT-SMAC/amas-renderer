package fr.irit.smac.amasrenderer

import javafx.collections.FXCollections
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper

import fr.irit.smac.amasrenderer.model.AgentGraphModel
import fr.irit.smac.amasrenderer.model.ConfigurationMapModel;
import fr.irit.smac.amasrenderer.service.ConfigurationMapService;
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService
import fr.irit.smac.amasrenderer.service.ToolService

/**
 * The Class GraphServiceTest.
 */
@Ignore
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
		toolService.setTools(FXCollections.observableArrayList(new ArrayList<String>()))
		infrastructureService = InfrastructureService.getInstance()
		infrastructureService.setInfrastructure(FXCollections.observableArrayList(new ArrayList<String>()))
		graphNodeService.createAgentGraph()
		
    }
	
	def 'check if models are correctly created from a loaded json file'() {
		when:
		InputStream json = ClassLoader.getSystemResourceAsStream("./1infra5services12agents.json")
		ObjectMapper mapper = new ObjectMapper()
		ConfigurationMapModel tmp = mapper.readValue(file, ConfigurationMapModel.class);
		ConfigurationMapService.getInstance().setModel(tmp);
		Map<String, Object> graphMap = GraphService.getInstance().getModel().getAgentMap();
		graphService.createAgentGraphFromMap(graphMap);
		toolService.createServicesFromMap(ToolService.getInstance().getServicesMap());
		infrastructureService.createInfrastructureFromMap(InfrastructureService.getInstance().getInfrastructureMap());
		
		then:
		graphNodeService.getModel().getNodeCount() == 12
		infrastructureService.getInfrastructure().get(0) == "fr.irit.smac.amasfactory.impl.BasicInfrastructure"
		toolService.getTools().size() == 5
	}
	
}