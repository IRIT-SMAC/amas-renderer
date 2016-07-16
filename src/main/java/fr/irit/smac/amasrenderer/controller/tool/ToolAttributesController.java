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
package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.tool.ToolModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of a tool
 */
public class ToolAttributesController extends LoadSecondaryWindowController
    implements Initializable, IParentWindowModal, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private ImageView delButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private ToolModel tool;

    private AttributesService attributesService = AttributesService.getInstance();

    /**
     * When the delete button is clicked, the selected tool is deleted
     */
    @FXML
    public void deleteButton() {

        this.loadFxml(this.window, "view/tool/ToolDeletion.fxml", false, this, this.tool);
    }

    /**
     * When the confirm button is clecked, the attributes are updated depending
     * on the items of the tree
     */
    @FXML
    public void confirmButton() {

        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        this.tree.setCellFactory(c -> {
            return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(), tool);
        });
    }

    @Override
    public void closeWindow() {
        this.stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        ToolModel currentTool = (ToolModel) args[0];
        this.tool = currentTool;
        this.stage = stage;
        TreeItem<String> root = new TreeItem<>(currentTool.getName());
        this.tree.setRoot(root);
        root.setExpanded(true);
        HashMap<String, Object> toolAttributes = (HashMap<String, Object>) currentTool.getAttributesMap();
        this.attributesService.fillAttributes(toolAttributes, root, currentTool);
    }

}