package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraph;

public class GraphDefaultMouseController extends MouseAdapter
{
    // Attribute

    /**
     * The view this manager operates upon.
     */
    protected ViewPanel view;

    /**
     * The graph to modify according to the view actions.
     */
    protected AgentGraph graph;

    // Construction

    public void init(ViewPanel view, AgentGraph graph) {
        this.view = view;
        this.graph = graph;
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
    }
    
    
    
    public void release() {
        view.removeMouseListener(this);
        view.removeMouseMotionListener(this);
    }

    // Command

    protected void mouseButtonPress(MouseEvent event) {
        view.requestFocus();

        // Unselect all.

        if (!event.isShiftDown()) {
            for (Node node : graph) {
                node.addAttribute("ui.selected"); // mon ajout, sinon ca marchait pas :^)
                //if (node.hasAttribute("ui.selected"))
                node.removeAttribute("ui.selected");
            }
            for(Edge edge : graph.getEdgeSet()){
                edge.addAttribute("ui.class","selected");
                edge.removeAttribute("ui.class");
            }
        }
    }

    protected void mouseButtonRelease(MouseEvent event,
            Iterable<GraphicElement> elementsInArea) {
        for (GraphicElement element : elementsInArea) {
            if (!element.hasAttribute("ui.selected"))
                element.addAttribute("ui.selected");
            if(!element.hasAttribute("ui.class")){
                System.out.println("salut");
                element.addAttribute("ui.class","selection");
            }
            System.out.println(element.getAttributeKeySet());
        }
    }

    protected void mouseButtonPressOnElement(GraphicElement element,
            MouseEvent event) {
        view.freezeElement(element, true);
        if (event.getButton() == 3) {
            element.addAttribute("ui.selected");
        } else {
            element.addAttribute("ui.clicked");
        }
    }

    protected void elementMoving(GraphicElement element, MouseEvent event) {
        view.moveElementAtPx(element, event.getX(), event.getY());
    }

    protected void mouseButtonReleaseOffElement(GraphicElement element,
            MouseEvent event) {
        view.freezeElement(element, false);
        if (event.getButton() != 3) {
            element.removeAttribute("ui.clicked");
        } else {
        }
    }

    // Mouse Listener

    protected GraphicElement curElement;

    protected float x1, y1;

    public void mouseClicked(MouseEvent event) {
        // NOP
    }

    public void mousePressed(MouseEvent event) {
        if(SwingUtilities.isLeftMouseButton(event)){
            curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());
    
            if (curElement != null) {
                mouseButtonPressOnElement(curElement, event);
            } else {
                x1 = event.getX();
                y1 = event.getY();
                mouseButtonPress(event);
                view.beginSelectionAt(x1, y1);
            }
        }        
    }

    public void mouseDragged(MouseEvent event) {
        if(SwingUtilities.isLeftMouseButton(event)){
            if (curElement != null) {
                elementMoving(curElement, event);
            } else {
                view.selectionGrowsAt(event.getX(), event.getY());
            }
        }
    }

    public void mouseReleased(MouseEvent event) {
        if(SwingUtilities.isLeftMouseButton(event)){
            if (curElement != null) {
                mouseButtonReleaseOffElement(curElement, event);
                curElement = null;
            } else {
                float x2 = event.getX();
                float y2 = event.getY();
                float t;
    
                if (x1 > x2) {
                    t = x1;
                    x1 = x2;
                    x2 = t;
                }
                if (y1 > y2) {
                    t = y1;
                    y1 = y2;
                    y2 = t;
                }
    
                mouseButtonRelease(event, view.allNodesOrSpritesIn(x1, y1, x2, y2));
                view.endSelectionAt(x2, y2);
            }
        }
    }

    public void mouseEntered(MouseEvent event) {
        // NOP
    }

    public void mouseExited(MouseEvent event) {
        // NOP
    }

    public void mouseMoved(MouseEvent e) {
    }
}