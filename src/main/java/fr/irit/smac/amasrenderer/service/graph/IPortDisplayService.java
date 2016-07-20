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
