package fr.irit.smac.amasrenderer

import org.graphstream.graph.implementations.SingleNode
import org.graphstream.ui.swingViewer.ViewPanel
import org.graphstream.ui.view.Viewer

import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.controller.GraphAddDelNodeMouseController
import fr.irit.smac.amasrenderer.model.AgentGraph

class GraphNodeTest extends Specification{

    @Shared GraphAddDelNodeMouseController controller
    @Shared AgentGraph model
    
    def setupSpec() {

        model = new AgentGraph("AMAS Rendering")
        Viewer viewer = new Viewer(model, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD)
        viewer.enableAutoLayout()
        ViewPanel graphView = viewer.addDefaultView(false)
        controller = new GraphAddDelNodeMouseController()
        controller.model = model
    }

    def 'check if an agent is added and removed'() {

        when:
        controller.addNode("agent1",5.0,10.0)

        then:
        SingleNode node = model.getNode("agent1")
        node.getId()
        node.getAttribute("xyz") == [5.0, 10.0]
        node.getAttribute("layout.weight") == 300
        
        when:
        controller.removeNode(node)
        
        then:
        model.getNode("agent1") == null
        
    }
}
