package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.model.AgentGraphModel;
import javafx.scene.control.ToggleGroup;

/**
 * The Class GraphDefaultMouseController. Implements the default operation of
 * graphstream Thoses include dragging a node and making a selection box
 */
public class GraphDefaultMouseController extends MouseAdapter {

    protected ViewPanel view;

    protected AgentGraphModel graph;

    protected GraphicElement curElement;

    protected float x1;

    protected float y1;

    private ToggleGroup toggroup;

    /**
     * Initialize the controller, and adds it to the graph.
     * 
     * @param view
     *            the view
     * @param graph
     *            the graph
     * @param toggroup
     *            the toggle group
     */
    public void init(ViewPanel view, AgentGraphModel graph, ToggleGroup toggroup) {
        this.view = view;
        this.graph = graph;
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        this.toggroup = toggroup;
    }

    private boolean isButtonSelected() {
        return toggroup.getSelectedToggle() != null;
    }

    /**
     * Used to "unplug" this controller from the graph.
     */
    public void release() {
        view.removeMouseListener(this);
        view.removeMouseMotionListener(this);
    }

    /**
     * Method to unselect all nodes when the Mouse button is pressed.
     *
     * @param event
     *            the event
     */
    protected void mouseButtonPress(MouseEvent event) {
        view.requestFocus();

        // Unselect all.
        // if shift is down, then it means the user wants to select more nodes,
        // so don't unselect
        if (!event.isShiftDown()) {
            for (Node node : graph) {
                node.addAttribute("ui.selected");
                // nonsense line, but don't always work without it
                node.removeAttribute("ui.selected");
                node.addAttribute("ui.clicked");
                // nonsense line, but don't always work without it
                node.removeAttribute("ui.clicked");
            }
        }
    }

    /**
     * Tags all the elements in the selection area as selected on Mouse button
     * release.
     *
     * @param elementsInArea
     *            the elements in area (meant to be called by mouseReleased)
     */
    protected void mouseButtonRelease(Iterable<GraphicElement> elementsInArea) {
        for (GraphicElement element : elementsInArea) {
            if (!element.hasAttribute("ui.selected")) {
                element.addAttribute("ui.selected");
            }
        }
    }

    /**
     * set the Click tag on the clicked element if left button, or selected tag
     * otherwise on Mouse button press on element.
     *
     * @param element
     *            the element
     * @param event
     *            the event
     */
    protected void mouseButtonPressOnElement(GraphicElement element, MouseEvent event) {
        view.freezeElement(element, true);
        if (SwingUtilities.isRightMouseButton(event)) {
            element.addAttribute("ui.selected");
        }
        else {
            element.addAttribute("ui.clicked");
        }
    }

    /**
     * To be called by mouseDragged moves the element to the location of the
     * mouse at each call.
     *
     * @param element
     *            the element
     * @param event
     *            the event
     */
    protected void elementMoving(GraphicElement element, MouseEvent event) {
        view.moveElementAtPx(element, event.getX(), event.getY());
    }

    /**
     * Mouse button release off element. When mouse released after dragging one,
     * freeze the element and remove the clicked attribute
     * 
     * @param element
     *            the element
     * @param event
     *            the event
     */
    protected void mouseButtonReleaseOffElement(GraphicElement element, MouseEvent event) {
        view.freezeElement(element, false);
        if (SwingUtilities.isRightMouseButton(event)) {
            element.removeAttribute("ui.clicked");
        }
    }

    /**
     * On mousePress gets the node at the left click location, if there is a
     * node sets it as curElement to be dragged trough mouseButtonPressOnElement
     * COMMENTED: Else it means the user wants to create a selection box trough
     * mouseButtonPress.
     *
     * @param event
     *            the Mouse Event
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event) && !isButtonSelected() && !event.isControlDown()
            && !event.isShiftDown()) {
            curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());
            if (curElement != null) {
                mouseButtonPress(event);
                mouseButtonPressOnElement(curElement, event);
            }
        }
    }

    /**
     * 
     * if a node was selected at the click, then moves it at mouse location
     * COMMENTED: else expand the selection to the mouse location.
     *
     * @param event
     *            the Mouse Event
     * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event) && !isButtonSelected() && !event.isControlDown()
            && !event.isShiftDown() && curElement != null) {
            elementMoving(curElement, event);
        }
    }

    /**
     * if we were dragging a node, releases that node, and sets curElement to
     * null COMMENTED: else get the selection end ( mouse location ) and ask the
     * graph all the nodes in that selection it then marks all thoses nodes as
     * selected and ends the selection.
     *
     * @param event
     *            the Mouse Event
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event) && !isButtonSelected() && !event.isControlDown()
            && !event.isShiftDown() && curElement != null) {
            mouseButtonReleaseOffElement(curElement, event);
            curElement = null;
        }
    }
}