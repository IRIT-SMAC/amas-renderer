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

/**
 * This interface defines the methods implemented by GraphService and
 * EdgeService
 */
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

    /**
     * Removes an edge
     * 
     * @param id
     *            the id of the edge
     */
    public void removeEdge(String id);

}
