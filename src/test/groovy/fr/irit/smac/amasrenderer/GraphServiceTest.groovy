package fr.irit.smac.amasrenderer

import org.graphstream.graph.implementations.SingleNode

import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.controller.GraphAddDelNodeMouseController
import fr.irit.smac.amasrenderer.model.AgentGraph
import fr.irit.smac.amasrenderer.service.GraphService

class GraphServiceTest extends Specification{

    @Shared GraphService graphNodeService
    
    def setupSpec() {

        graphNodeService = new GraphService()
        graphNodeService.createAgentGraph()
    }

    def 'check if an agent is added and removed'() {

        when:        
        graphNodeService.addNode("agent1",5.0,10.0)

        then:
        SingleNode node = graphNodeService.getModel().getNode("agent1")
        node.getId()
        node.getAttribute("xyz") == [5.0, 10.0]
        node.getAttribute("layout.weight") == 300
        
        when:
        graphNodeService.removeNode(node)
        
        then:
        graphNodeService.getModel().getNode("agent1") == null
    }
    
    def 'check if an edge is added and removed'() {
        
    }
}
