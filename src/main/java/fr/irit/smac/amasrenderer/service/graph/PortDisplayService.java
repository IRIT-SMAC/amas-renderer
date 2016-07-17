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

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import fr.irit.smac.amasrenderer.Const;

public class PortDisplayService {

    private MultiGraph                graph;
    private boolean                   displayPort;
    private boolean                   displayMain;
    private SpriteManager             spriteManager;
    private static PortDisplayService instance = new PortDisplayService();

    public static PortDisplayService getInstance() {

        return instance;
    }

    public void init(MultiGraph graph, SpriteManager spriteManager) {

        this.graph = graph;
        this.spriteManager = spriteManager;
    }

    public void displaySprite(Sprite sprite, boolean visible, String classNormal, String classBackground) {
        if (visible) {
            sprite.addAttribute(Const.GS_UI_CLASS, classNormal);
        }
        else {
            sprite.addAttribute(Const.GS_UI_CLASS, classBackground);
        }
    }

    public void hideSpriteEdge(String type) {

        this.spriteManager.forEach(s -> {
            if (s.getAttribute(Const.TYPE_SPRITE).equals(type)) {
                s.setAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
            }
        });
    }

    public void displaySpriteEdge(boolean displayNode, Node foregroundNode, String type, String styleClass) {

        if (displayNode) {
            this.spriteManager.forEach(s -> {
                Edge edge = (Edge) s.getAttachment();
                String id = foregroundNode.getId();
                if ((edge.getSourceNode().getId() == id
                    || edge.getTargetNode().getId() == id) && (s.getAttribute(Const.TYPE_SPRITE).equals(type))) {
                    s.setAttribute(Const.GS_UI_CLASS, styleClass);
                }
            });
        }
        else {
            this.spriteManager.forEach(s -> {
                if (s.getAttribute(Const.TYPE_SPRITE).equals(type)) {
                    s.setAttribute(Const.GS_UI_CLASS, styleClass);
                }
            });
        }

    }

    public void displayForegroundNode(Node foregroundNode) {

        String id = foregroundNode.getId();
        this.graph.getEachNode().forEach(node -> {
            if (node.getId() != id) {
                node.addAttribute(Const.GS_UI_CLASS, Const.NODE_CLASS_BACKGROUND);
            }
        });

        this.graph.getEachEdge().forEach(edge -> {
            if (edge.getSourceNode().getId() != id && edge.getTargetNode().getId() != id) {
                edge.addAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
                this.spriteManager.forEach(s -> {
                    if (s.getAttachment().equals(edge)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
                    }
                });
            }
        });
    }

    public void displayBackgroundNode(Node backgroundNode) {

        String id = backgroundNode.getId();
        this.graph.getEachNode().forEach(node -> {
            if (node.getId() == id) {
                node.removeAttribute(Const.GS_UI_CLASS);
            }
        });

        this.graph.getEachEdge().forEach(edge -> {
            if (edge.getSourceNode().getId() == id || edge.getTargetNode().getId() == id) {
                edge.removeAttribute(Const.GS_UI_CLASS);
                this.displaySprite(getDisplayMain(), this.getDisplayPort(), edge);
            }
        });
    }

    public void displayAllNodes() {

        this.graph.getEachNode().forEach(node -> {
            node.removeAttribute(Const.GS_UI_CLASS);
        });

        this.graph.getEachEdge().forEach(edge -> {
            edge.removeAttribute(Const.GS_UI_CLASS);
            this.displaySprite(this.getDisplayMain(), this.getDisplayPort(), edge);
        });
    }

    private void displaySprite(boolean mainSpriteVisible, boolean portSpriteVisible, Edge edge) {

        if (mainSpriteVisible || portSpriteVisible) {
            this.spriteManager.forEach(s -> {

                if (s.getAttachment().equals(edge)) {

                    if (mainSpriteVisible && s.getAttribute(Const.TYPE_SPRITE).equals(Const.MAIN_SPRITE_EDGE)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.MAIN_SPRITE_CLASS);
                    }
                    else if (portSpriteVisible && s.getAttribute(Const.TYPE_SPRITE).equals(Const.PORT)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.PORT_SPRITE_CLASS);
                    }
                }
            });
        }
    }

    public void setDisplayPort(boolean visible) {
        this.displayPort = visible;
    }

    public void setDisplayMain(boolean visible) {
        this.displayMain = visible;
    }

    public boolean getDisplayPort() {
        return this.displayPort;
    }

    public boolean getDisplayMain() {
        return this.displayMain;
    }
}
