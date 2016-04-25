package fr.irit.smac.amasrenderer.MouseEventsAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;

public class GraphMouseAdapter extends MouseAdapter{

    ControllerAmasRendering controller;
    private int currentAgentID;
    
    public GraphMouseAdapter(ControllerAmasRendering controller){
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
