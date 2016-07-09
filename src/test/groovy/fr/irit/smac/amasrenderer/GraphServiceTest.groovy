package fr.irit.smac.amasrenderer

import org.graphstream.graph.implementations.MultiNode

import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.service.GraphService

class GraphServiceTest extends Specification{

    @Shared GraphService graphService
    @Shared String agentId = "0"
    @Shared String agentId2 = "1"

    def setupSpec() {

        graphService = GraphService.getInstance()
        graphService.createAgentGraph()
        graphService.setAgentMap(new HashMap<String,Object>())
    }

    def 'check if an agent is added and removed'() {

        when:
        graphService.addNode(5.0,10.0)

        then:
        MultiNode node = graphService.getGraph().getNode(agentId)
        node.getId() == agentId
        node.getAttribute("xy") == [5.0, 10.0]
        node.getAttribute("layout.weight") == Const.LAYOUT_WEIGHT_NODE

        when:
        graphService.removeNode(node)

        then:
        graphService.getGraph().getNode(agentId) == null
    }

    def 'check if an edge is added and removed'() {
        when:
        graphService.addNode(5.0,10.0)
        graphService.addNode(10.0,5.0)
        graphService.addEdge(agentId, agentId2, agentId2)

        then:
        graphService.getGraph().getEdge(agentId + agentId2) != null

        when:
        graphService.getGraph().removeEdge(agentId + agentId2)

        then:
        graphService.getGraph().getEdge(agentId + agentId2) == null
    }
}
