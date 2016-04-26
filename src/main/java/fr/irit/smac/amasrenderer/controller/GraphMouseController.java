package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.graphstream.graph.Node;

public class GraphMouseController extends MouseAdapter{

    GraphMainController controller;
    private int currentAgentID;
    
    public GraphMouseController(GraphMainController controller){
        this.controller = controller;
        this.currentAgentID = 0;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getClickCount() == 2){
            controller.getModel().addNode(Integer.toString(currentAgentID++));
        }
        else{
            Node n = (Node) controller.getGraphView().findNodeOrSpriteAt(e.getX(), e.getY());
            
            if(n != null){
                System.out.println("yoloswag les noeud : "+n.getId()+" as les attributs suivants:");
                for (Iterator<String> iterator = n.getAttributeKeyIterator(); iterator.hasNext();) {
                    String s = iterator.next();
                    System.out.println(s+" "+n.getAttribute(s));
                    
                }
            }
        }

    }
    
    
    
    
}
