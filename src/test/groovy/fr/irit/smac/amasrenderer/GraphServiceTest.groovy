package fr.irit.smac.amasrenderer

import org.graphstream.graph.implementations.MultiNode

import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.service.GraphService

class GraphServiceTest extends Specification{

    @Shared GraphService graphService
    @Shared String agentId = "0"
    @Shared String agentId2 = "1"

    def setup() {

        graphService = GraphService.getInstance()
        graphService.setAgentMap(new HashMap<String,Object>())
        graphService.idCount = 0
    }

    def 'check if an agent is added and removed'() {

        when:
        graphService.addNode(5.0,10.0)

        then:
        MultiNode node = graphService.getGraph().getNode(agentId)
        node.getId() == agentId

        when:
        graphService.removeNode(node)

        then:
        graphService.getGraph().getNode(agentId) == null
    }

    def 'check if an edge is added and removed'() {

        when:
        graphService.addNode(5.0,10.0)
        graphService.addNode(10.0,5.0)
        graphService.addEdge(agentId, agentId2, agentId + agentId2, agentId)

        then:
        graphService.getGraph().getEdge(0) != null

        when:
        graphService.getGraph().removeEdge(0)

        then:
        graphService.getGraph().getEdgeCount() == 0
    }
}
