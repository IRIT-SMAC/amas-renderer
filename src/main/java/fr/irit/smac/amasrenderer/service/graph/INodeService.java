package fr.irit.smac.amasrenderer.service.graph;

/**
 * This interface defines the methods implemented by GraphService and
 * NodeService
 */
public interface INodeService {

    /**
     * Adds a node. The coordinates are given. This method is called when the
     * user clicks on the graph of agents
     *
     * @param x
     *            the x location of the node
     * @param y
     *            the y location of the node
     */
    public void addNode(double x, double y);

    /**
     * Removes a node
     * 
     * @param id
     */
    public void removeNode(String id);
}
