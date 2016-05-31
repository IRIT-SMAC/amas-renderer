package fr.irit.smac.amasrenderer

import org.graphstream.graph.implementations.SingleNode

import spock.lang.Ignore;
import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.service.GraphService

/**
 * The Class GraphServiceTest.
 */
@Ignore
class GraphServiceTest extends Specification{

    @Shared GraphService graphNodeService
    
    /**
     * Setup spec.
     *
     * @return the java.lang. object
     */
    def setupSpec() {

        graphNodeService = GraphService.getInstance()
        graphNodeService.createAgentGraph()
    }

    /**
     * Check if an agent is added and removed.
     *
     * @return the object
     */
    def 'check if an agent is added and removed'() {

        when:        
        graphNodeService.addNode("agent1",5.0,10.0)

        then:
        SingleNode node = graphNodeService.getGraph().getNode("agent1")
        node.getId()
        node.getAttribute("xy") == [5.0, 10.0]
        node.getAttribute("layout.weight") == Const.LAYOUT_WEIGHT_NODE
        
        when:
        graphNodeService.removeNode(node)
        
        then:
        graphNodeService.getGraph().getNode("agent1") == null
    }
    
    /**
     * Check if an edge is added and removed.
     *
     * @return the object
     */
    def 'check if an edge is added and removed'() {
        when:
        graphNodeService.addNode("agent1",5.0,10.0)
        graphNodeService.addNode("agent2",10.0,5.0)
        graphNodeService.getGraph().addEdge("agent1-agent2","agent1","agent2")

        then:
        graphNodeService.getGraph().getEdge("agent1-agent2") != null
        
        when:
        graphNodeService.getGraph().removeEdge("agent1-agent2")
        
        then:
        graphNodeService.getGraph().getEdge("agent1-agent2") == null
    }
}
