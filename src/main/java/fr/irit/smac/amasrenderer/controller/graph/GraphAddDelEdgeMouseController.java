package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.scene.control.ToggleButton;

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

    private ToggleButton buttonAddEdge;

    private ToggleButton buttonDelEdge;

    private EState state = EState.AT_EASE;

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

        if (!e.isControlDown()) {

            switch (state) {

                case AT_EASE:
                    state = nextStepAtEase(e);
                    selectSource();
                    break;

                case READY_TO_ADD:
                    state = EState.AT_EASE;
                    addEdge(e);
                    unselectSource();
                    break;

                case READY_TO_DELETE:
                    state = EState.AT_EASE;
                    removeEdge(e);
                    unselectSource();
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * Select the next step of the first step
     * 
     * @param e
     *            the mouse event
     * @return
     */
    public EState nextStepAtEase(MouseEvent e) {

        EState localState = EState.AT_EASE;
        source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());

        if (source != null) {

            if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON1) {
                localState = EState.READY_TO_ADD;
            }
            else if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON3) {
                localState = EState.READY_TO_DELETE;
            }
            else if (this.buttonAddEdge.isSelected()) {
                localState = EState.READY_TO_ADD;
            }
            else if (this.buttonDelEdge.isSelected()) {
                localState = EState.READY_TO_DELETE;
            }
        }

        return localState;
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

    /**
     * Sets the button AddEdge from the parent controller
     *
     * @param buttonAddEdge
     *            the new button add edge
     */
    public void setButtonAddEdge(ToggleButton buttonAddEdge) {
        this.buttonAddEdge = buttonAddEdge;
    }

    /**
     * Sets the button DelEdge from the parent controller
     *
     * @param buttonDelEdge
     *            the new button del edge
     */
    public void setButtonDelEdge(ToggleButton buttonDelEdge) {
        this.buttonDelEdge = buttonDelEdge;
    }
}
