package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.service.GraphService;

/**
 * The Class GraphAddDelNodeMouseController. This controller controls addition
 * and deletion of nodes on the graph "model" Creation and deletion are used by
 * alt+click on an empty space to create one and on a node to delete it and all
 * connected edges
 */
public class GraphAddDelNodeMouseController extends MouseAdapter {

    private GraphService graphNodeService;

    private ViewPanel graphView;

    private int currentNodeId;

    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView
     *            the graph view
     * @param graphNodeService
     *            the graph node service
     * @param graphView
     *            the graph view
     * @param graphNodeService
     *            the graph node service
     */
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
        this.graphNodeService = GraphService.getInstance();
        this.currentNodeId = graphNodeService.getModel().getNodeCount() + 1;
        graphView.addMouseListener(this);
    }

    /**
     * On mouse alt+click, if there is no node at mouse location creates a node
     * at mouse location else deletes the node at mouse location.
     *
     * @param e
     *            the event. On mouse alt+click, if there is no node at mouse
     *            location creates a node at mouse location else deletes the
     *            node at mouse location.
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {

        switch (GraphMainController.state) {

            case CTRL_DOWN:
                if (e.getButton() == MouseEvent.BUTTON1) {
                    createNode(e);
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    Node node = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                    this.graphNodeService.removeNode(node);
                }
                GraphMainController.state = EStateGraph.CTRL_DOWN;
                break;
                
            case BUTTON_ADD_NODE:
                createNode(e);
                GraphMainController.state = EStateGraph.BUTTON_ADD_NODE;
                break;
                
            case BUTTON_DELETE_NODE:
                Node n = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                this.graphNodeService.removeNode(n);
                GraphMainController.state = EStateGraph.BUTTON_DELETE_NODE;
                break;
                
            default:
                break;  

        }
    }

    /**
     * Creates the node.
     *
     * @param e
     *            the event
     */
    private void createNode(MouseEvent e) {
        String curId = Integer.toString(currentNodeId++);
        Point3 clicLoc = graphView.getCamera().transformPxToGu(e.getX(), e.getY());

        this.graphNodeService.addNode(curId, clicLoc.x, clicLoc.y);
    }

}