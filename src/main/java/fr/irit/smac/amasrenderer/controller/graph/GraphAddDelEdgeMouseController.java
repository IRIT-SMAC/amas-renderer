package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.service.GraphService;

/**
 * The Class GraphAddDelEdgeMouseController. This controller controls addition
 * and deletion of edges on the graph "model" Creation and deletion are used bys
 * right-click dragging from one node to an other
 */
public class GraphAddDelEdgeMouseController extends MouseAdapter {

    private GraphService graphNodeService;

    private ViewPanel graphView;

    private Node source = null;

    private Node target = null;

    private EStateGraph previousState;

    /**
     * Initialize the controller, and adds it to the graph.
     *
     * @param graphView
     *            the graph view
     * 
     * @param graphNodeService
     *            the graphNodeService
     * @param graphNodeService
     *            the graph node service
     */
    public void init(ViewPanel graphView) {
        this.graphView = graphView;
        graphView.addMouseListener(this);
        this.graphNodeService = GraphService.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    // the headache function
    @Override
    public void mouseClicked(MouseEvent e) {

        switch (GraphMainController.state) {

            case SHIFT_DOWN:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.SHIFT_DOWN;
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        GraphMainController.state = EStateGraph.READY_TO_ADD;

                    }
                    else if (e.getButton() == MouseEvent.BUTTON3) {
                        GraphMainController.state = EStateGraph.READY_TO_DELETE;

                    }
                    selectSource();
                }
                break;

            case BUTTON_ADD_EDGE:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.BUTTON_ADD_EDGE;
                    GraphMainController.state = EStateGraph.READY_TO_ADD;
                    selectSource();
                }
                break;

            case BUTTON_DELETE_EDGE:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.BUTTON_DELETE_EDGE;
                    GraphMainController.state = EStateGraph.READY_TO_DELETE;
                    selectSource();
                }
                break;

            case READY_TO_ADD:
                GraphMainController.state = previousState;
                addEdge(e);
                unselectSource();
                break;

            case READY_TO_DELETE:
                GraphMainController.state = previousState;
                removeEdge(e);
                unselectSource();
                break;

            default:
                break;
        }

    }

    /**
     * Select the source node
     */
    public void selectSource() {

        if (source != null && !source.hasAttribute("ui.selected")) {
            source.addAttribute("ui.selected");
        }
        target = null;
    }

    /**
     * Add an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    public void addEdge(MouseEvent e) {

        if (getEdge(e) == null && target != null) {
            this.graphNodeService.addEdge(source.getId(), target.getId());
        }
    }

    /**
     * Remove the edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    public void removeEdge(MouseEvent e) {

        this.graphNodeService.removeEdge(getEdge(e));
    }

    /**
     * Get the edge between the source and the target
     * 
     * @param e
     *            the event
     * @return
     */
    private Edge getEdge(MouseEvent e) {

        target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (target != null) {
            Edge edge = this.graphNodeService.getModel().getEdge(source + "" + target);
            return edge;
        }
        return null;
    }

    /**
     * Unselect a source when an edge is created, deleted, or when the target is
     * not a node
     */
    private void unselectSource() {

        source.removeAttribute("ui.selected");
        source = null;
    }
}
