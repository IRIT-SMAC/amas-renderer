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
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class TargetAttributesController implements ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    private TargetModel targetModel;

    private AttributesService attributesService = AttributesService.getInstance();

    @FXML
    public void confirmButton() {

        this.stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        this.stage = stage;
        Sprite sprite = (Sprite) args[0];
        Edge e = (Edge) sprite.getAttachment();
        Node node = e.getSourceNode();

        this.targetModel = this.graphService.getTargetModel(node.getAttribute(Const.GS_UI_LABEL),
            e.getAttribute(Const.GS_UI_LABEL));

        TreeItem<String> root = new TreeItem<>(e.getAttribute(Const.GS_UI_LABEL));
        this.tree.setRoot(root);
        this.attributesService.fillAttributes(this.targetModel.getAttributesMap(), root, this.targetModel);

        this.tree.setEditable(true);
        root.setExpanded(true);

        this.tree.setCellFactory(c -> {
            return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(), targetModel);
        });

    }

}
