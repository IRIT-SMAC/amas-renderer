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
package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.Infrastructure;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenuTree;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of the infrastructure
 */
public class InfrastructureAttributesController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private Infrastructure infra;

    private AttributesService attributesService = AttributesService.getInstance();

    /**
     * When the confirm button is clicked, the attributes of the infrastructure
     * are updated depending on the tree
     */
    @FXML
    public void confirmButton() {

        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tree.setEditable(true);
        tree.setCellFactory(c -> {
            return new AttributesTreeCell(new AttributesContextMenuTree(), new DefaultStringConverter(), infra);
        });
    }

    @Override
    public void init(Stage stage, Object... args) {

        Infrastructure infrastructure = (Infrastructure) args[0];
        infra = infrastructure;
        this.stage = stage;
        TreeItem<String> root = new TreeItem<>(infrastructure.getName());
        tree.setRoot(root);
        root.setExpanded(true);
        attributesService.fillAttributes(infrastructure.getAttributesMap(), root, infrastructure);
    }
}
