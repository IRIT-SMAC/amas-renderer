package fr.irit.smac.amasrenderer.service;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;

public class GraphService {

    private AgentGraph          model;
    private static GraphService instance = new GraphService();

    private GraphService() {

    }

    public static GraphService getInstance() {
        return instance;
    }

    public void createAgentGraph() {

        this.model = new AgentGraph("AMAS Rendering");
        this.model.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../view/styleSheet2.css") + ")");
    }

    public void addNode(String id, double x, double y) {

        model.addNode(id);
        model.getNode(id).changeAttribute("xyz", x, y);
        model.getNode(id).setAttribute("ui.stocked-info", new Stock());
        model.getNode(id).setAttribute("layout.weight", 300);
        System.out.println("nodeAdded");
    }

    public void removeNode(Node n) {

        Iterable<Edge> edges = n.getEachEdge();
        if (edges != null) {
            for (Edge edge : edges) {
                model.removeEdge(edge.getId());
            }
        }
        model.removeNode(n.getId());
    }

    public AgentGraph getModel() {

        return this.model;
    }
}
