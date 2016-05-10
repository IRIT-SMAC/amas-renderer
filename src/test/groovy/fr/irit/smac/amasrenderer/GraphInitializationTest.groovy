package fr.irit.smac.amasrenderer

import spock.lang.Shared
import spock.lang.Specification

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper

import fr.irit.smac.amasrenderer.model.AgentGraph
import fr.irit.smac.amasrenderer.service.GraphService

/**
 * The Class GraphServiceTest.
 */
class GraphInitializationTest extends Specification{

    @Shared GraphService graphNodeService
    
    /**
     * Setup spec.
     *
     * @return the java.lang. object
     */
    def setupSpec() {

        graphNodeService = GraphService.getInstance()
        InputStream json = ClassLoader.getSystemResourceAsStream("./graph.json")
		ObjectMapper mapper = new ObjectMapper()
		mapper.readValue(json,AgentGraph.class)
    }
	
	def 'check if the model is correctly created from a loaded json file'() {
		
	}
	
}