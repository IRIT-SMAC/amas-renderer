package fr.irit.smac.amasrenderer.service.graph;

import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;

/**
 * This interface defines the methods implemented by GraphService and
 * PortDisplayService
 */
public interface IPortDisplayService {

    public void displaySprite(Sprite sprite, boolean visible, String classNormal, String classBackground);

    public void hideSpriteEdge(String type);

    public void displaySpritesEdge(boolean displayNode, Node foregroundNode, String type, String styleClass);

    /**
     * Displays a selected node. The others are in the background
     * 
     * @param foregroundNode
     */
    public void displayForegroundNode(Node foregroundNode);

    /**
     * Displays an unselected node. It is in the background
     * 
     * @param backgroundNode
     */
    public void displayBackgroundNode(Node backgroundNode);

    /**
     * Displays all nodes normally. No nodes are in the background.
     */
    public void displayAllNodesNormally();

    public void setDisplayPort(boolean displayPort);

    public void setDisplayMain(boolean displayMain);
}
