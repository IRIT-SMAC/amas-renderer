package fr.irit.smac.amasrenderer.service.graph;

import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Port;

/**
 * This interface defines the methods implemented by GraphService and
 * PortService
 */
@FunctionalInterface
public interface IPortService {

    /**
     * Adds a port to the port map of an agent. The port is instantiated with a
     * json file
     * 
     * @param agent
     * @return the port
     */
    public Port addPort(Agent agent, String name);
}
