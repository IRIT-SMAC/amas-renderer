package fr.irit.smac.amasrenderer

import spock.lang.Shared
import spock.lang.Specification

import java.util.Map;

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper

import fr.irit.smac.amasrenderer.model.AgentGraph
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ServiceService
import javafx.collections.FXCollections;

/**
 * The Class GraphServiceTest.
 */
class GraphInitializationTest extends Specification{

    @Shared GraphService graphNodeService
	@Shared ServiceService serviceService
	@Shared InfrastructureService infrastructureService
    
    /**
     * Setup spec.
     *
     * @return the java.lang. object
     */
    def setupSpec() {

        graphNodeService = GraphService.getInstance()
		serviceService = ServiceService.getInstance()
		serviceService.setListServices(FXCollections.observableArrayList(new ArrayList<String>()))
		infrastructureService = InfrastructureService.getInstance()
		infrastructureService.setInfrastructure(FXCollections.observableArrayList(new ArrayList<String>()))
		graphNodeService.createAgentGraph()
		
    }
	
	def 'check if models are correctly created from a loaded json file'() {
		when:
		InputStream json = ClassLoader.getSystemResourceAsStream("./1infra5services12agents.json")
		ObjectMapper mapper = new ObjectMapper()
		AgentGraph tmp = mapper.readValue(json,AgentGraph.class);
		GraphService.getInstance().getModel().setGraphMap(tmp.getGraphMap());
		Map<String,Object> graphMap = GraphService.getInstance().getModel().getGraphMap();
		graphNodeService.createAgentGraphFromMap(graphMap);
		
		serviceService.createServicesFromMap(graphMap);
		infrastructureService.createInfrastructuresFromMap(graphMap);
		
		then:
		graphNodeService.getModel().getNodeCount() == 12
		infrastructureService.getInfrastructure().get(0) == "fr.irit.smac.amasfactory.impl.BasicInfrastructure"
		serviceService.getListServices().size() == 5
	}
	
}