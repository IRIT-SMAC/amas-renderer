package fr.irit.smac.amasrenderer.MouseEventsAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;

import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;
import fr.irit.smac.amasrenderer.model.Stock;

public class AddDelNodeMouseAdapter extends MouseAdapter{

    ControllerAmasRendering controller;
    private int currentAgentID;
    
    public void init(ControllerAmasRendering controller) {
        this.controller = controller;
        this.currentAgentID = controller.getModel().getNodeCount() + 1;
        controller.getGraphView().addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.isAltDown()){
            Node n = (Node) controller.getGraphView().findNodeOrSpriteAt(e.getX(), e.getY());
            //si on clic dans le vide on crée un noeud
            if(n == null){
                String curId = Integer.toString(currentAgentID++);
                Point3 clicLoc = controller.getGraphView().getCamera().transformPxToGu(e.getX(), e.getY());
                controller.getModel().addNode(curId);
                controller.getModel().getNode(curId).changeAttribute("xyz", clicLoc.x, clicLoc.y );
                controller.getModel().getNode(curId).setAttribute("ui.stocked-info", new Stock());
                controller.getModel().getNode(curId).setAttribute("layout.weight", 300);
                
            }
            //sinon on supprime le noeud
            else{
                Iterable<Edge> edges = n.getEachEdge();
                if(edges != null){
                    for(Edge edge: edges){
                        controller.getModel().removeEdge(edge.getId());
                    }
                }
                controller.getModel().removeNode(n.getId());
            }
        }
    }
    
    
    
    
}
