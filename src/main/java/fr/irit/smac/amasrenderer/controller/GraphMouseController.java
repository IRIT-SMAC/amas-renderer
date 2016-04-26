package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;

public class GraphMouseController extends MouseAdapter{

    private ViewPanel  graphView;
    private int        currentNodeId;
    private AgentGraph model;

    public void init(ViewPanel graphView, AgentGraph model) {
        this.graphView = graphView;
        this.model = model;
        this.currentNodeId = model.getNodeCount() + 1;
        graphView.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.isAltDown()){
            Node n = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
            //si on clic dans le vide on crée un noeud
            if(n == null){
                String curId = Integer.toString(currentNodeId++);
                Point3 clicLoc = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
                model.addNode(curId);
                model.getNode(curId).changeAttribute("xyz", clicLoc.x, clicLoc.y );
                model.getNode(curId).setAttribute("ui.stocked-info", new Stock());
                model.getNode(curId).setAttribute("layout.weight", 300);
                
            }
            //sinon on supprime le noeud
            else{
                Iterable<Edge> edges = n.getEachEdge();
                if(edges != null){
                    for(Edge edge: edges){
                        model.removeEdge(edge.getId());
                    }
                }
                model.removeNode(n.getId());
            }
        }
    }
    
    
    
    
    
}
