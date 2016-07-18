package fr.irit.smac.amasrenderer.service.graph;

import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.Feature;

/**
 * This interface defines the methods implemented by GraphService and
 * FeatureService
 */
@FunctionalInterface
public interface IFeatureService {

    /**
     * Adds a feature to the common features of an agent. The feature is
     * instantiated with a json file
     * 
     * @param agent
     * @return the feature
     */
    public Feature addFeature(Agent agent);
}
