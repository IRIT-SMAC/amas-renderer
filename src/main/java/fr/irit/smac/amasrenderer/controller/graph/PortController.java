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
package fr.irit.smac.amasrenderer.controller.graph;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Target;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This controller is related to the update of the name of the port of an agent
 */
public class PortController implements ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldTool;

    private Sprite sprite;

    private Target targetModel;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    /**
     * When the confirm button is clicked, the name of the selected sprite is
     * updated
     */
    @FXML
    public void clickConfirm() {

        String portName = textfieldTool.getText();

        if (portName.trim().isEmpty() || portName.trim().equals(Const.NULL_STRING)) {
            portName = null;
        }

        if (sprite.getAttribute(Const.SUBTYPE_SPRITE).equals(Const.SOURCE_PORT_SPRITE)) {
            targetModel.setPortSource(portName);
            sprite.setAttribute(Const.GS_UI_LABEL, portName);
        }
        else if (sprite.getAttribute(Const.SUBTYPE_SPRITE).equals(Const.TARGET_PORT_SPRITE)) {
            targetModel.setPortTarget(portName);
            sprite.setAttribute(Const.GS_UI_LABEL, portName);
        }

        stage.close();

    }

    /**
     * When the cancel button is clicked, the name of the selected sprite is not
     * udpated
     */
    @FXML
    public void clickCancel() {

        stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        this.stage = stage;
        sprite = (Sprite) args[0];
        Edge e = (Edge) sprite.getAttachment();
        Node node = e.getSourceNode();

        targetModel = graphService.getTargetModel(node.getAttribute(Const.GS_UI_LABEL),
            e.getAttribute(Const.GS_UI_LABEL).toString());
    }
}
