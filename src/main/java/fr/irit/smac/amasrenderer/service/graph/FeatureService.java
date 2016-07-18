/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.amasrenderer.service.graph;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.Feature;

/**
 * This service is related to the business logic about the features of the graph
 * of the agents
 */
public class FeatureService {

    private static FeatureService instance = new FeatureService();

    public static FeatureService getInstance() {

        return instance;
    }

    /**
     * Adds a feature to the common features of an agent. The feature is
     * instantiated with a json file
     * 
     * @param agent
     * @return the feature
     */
    public Feature addFeature(Agent agent) {

        File file = new File(getClass().getResource("../../json/initial_feature.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        Feature feature = null;
        try {
            feature = mapper.readValue(file, Feature.class);
            feature.setName(Const.FEATURE);
            agent.getCommonFeaturesModel().getFeatures().put(Const.FEATURE, feature);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return feature;
    }
}