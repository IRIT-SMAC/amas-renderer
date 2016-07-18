package fr.irit.smac.amasrenderer.service.graph;

/**
 * This interface defines the methods implemented by GraphService and
 * EdgeService
 */
@FunctionalInterface
public interface IEdgeService {

    /**
     * Adds a directed edge from the source to the target. The edge to
     * instantiate is defined in json file. This method is called when the user
     * adds the edge by clicking on the graph of agents
     * 
     * @param idNodeSource
     *            the id of the source node
     * @param idNodeTarget
     *            the id of the target node
     */
    public void addEdge(String idNodeSource, String idNodeTarget);
}
