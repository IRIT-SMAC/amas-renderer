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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Port;

/**
 * This service is related to the business logic about the ports of the graph of
 * agents
 */
public class PortService implements IPortService {

    private static PortService  instance = new PortService();
    private static final Logger LOGGER   = Logger.getLogger(PortService.class.getName());

    public static PortService getInstance() {

        return instance;
    }

    @Override
    public Port addPort(Agent agent, String name) {

        File file = new File(getClass().getResource("../../json/initial_port.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        Port port = null;
        try {
            port = mapper.readValue(file, Port.class);
            port.setName(name);
            port.setId(name);
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put(name, port);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occured during the loading of a json file port", e);
        }

        return port;
    }
}
