package fr.irit.smac.amasrenderer;

import javafx.collections.FXCollections
import spock.lang.Shared
import spock.lang.Specification

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

import fr.irit.smac.amasrenderer.model.ConfigurationMapModel
import fr.irit.smac.amasrenderer.service.ConfigurationMapService
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService
import fr.irit.smac.amasrenderer.service.ToolService

public class SaveJsonTest extends Specification{
	
	public void exportToJson(){
		
		File file = new File("./1infra5services12agents.json");
		ObjectMapper mapper = new ObjectMapper();

		try {
			if(file != null)
				mapper.writeValue(file, ConfigurationMapService.getInstance().getModel().getConfigurationMap());
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadJson(){
		InputStream json = ClassLoader.getSystemResourceAsStream("./1infra5services12agents.json");
		ObjectMapper mapper = new ObjectMapper();
		ConfigurationMapModel tmp = mapper.readValue(json, ConfigurationMapModel.class);
		ConfigurationMapService.getInstance().setModel(tmp);
		Map<String, Object> graphMap = GraphService.getInstance().getModel().getAgentMap();
		graphService.createAgentGraphFromMap(graphMap);
		toolService.createServicesFromMap(ToolService.getInstance().getServicesMap());
		infrastructureService.createInfrastructureFromMap(InfrastructureService.getInstance().getInfrastructureMap());
	}

	@Shared GraphService graphService
	@Shared ToolService toolService
	@Shared InfrastructureService infrastructureService
	
	/**
	 * Setup spec.
	 *
	 * @return the java.lang. object
	 */
	def setupSpec() {

		graphService = GraphService.getInstance()
		toolService = ToolService.getInstance()
		toolService.setTools(FXCollections.observableArrayList(new ArrayList<String>()))
		infrastructureService = InfrastructureService.getInstance()
		infrastructureService.setInfrastructure(FXCollections.observableArrayList(new ArrayList<String>()))
		graphService.createAgentGraph()
		
	}
	
	def 'check if a JSON is correctly saved'() {
		when:
		loadJson()
		graphService.addNode("testNode")
		exportToJson()
		loadJson()
		
		then:
		graphService.getModel().getNodeCount() == 13
		infrastructureService.getInfrastructure().get(0) == "BasicInfrastructure"
		toolService.getTools().size() == 5
	}
	
		
}
