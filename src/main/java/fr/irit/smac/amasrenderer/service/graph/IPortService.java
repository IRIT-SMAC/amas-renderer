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