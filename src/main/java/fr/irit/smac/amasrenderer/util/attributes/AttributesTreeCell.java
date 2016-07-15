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
package fr.irit.smac.amasrenderer.util.attributes;

import java.util.Arrays;
import java.util.stream.Stream;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

/**
 * This class defines the tree to use for the visualisation or the update of the
 * attributes
 */
public class AttributesTreeCell extends TextFieldTreeCell<String> {

    private final AttributesContextMenu contextMenu;

    private boolean isProtected;
    private boolean isParentSingleNode;

    private IModel model;

    private boolean isNotExpanded;

    public AttributesTreeCell(AttributesContextMenu contextMenu, StringConverter<String> converter,
        IModel model) {
        super(converter);
        this.model = model;
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.contextMenu = contextMenu;
        this.setOnContextMenuRequested(evt -> {
            prepareContextMenu(getTreeItem());
            evt.consume();
        });
    }

    /**
     * Prepares the design of the context menu depending on the node
     * 
     * @param item
     *            the clicked node
     */
    private void prepareContextMenu(TreeItem<String> item) {

        if (item != null) {

            AttributesContextMenu menu = this.contextMenu;
            MenuItem delete = menu.getDelete();
            MenuItem rename = menu.getRename();
            MenuItem add = menu.getAdd();
            MenuItem addSingle = menu.getAddSingle();
            boolean root = item.getParent() == null;

            checkConstraintsNode(item);

            if (!root) {
                delete.setOnAction(evt -> {
                    item.getParent().getChildren().remove(item);
                    menu.freeActionListeners();
                });
            }

            addSingle.setOnAction(evt -> {
                TreeItem<String> item2 = new TreeItem<>("item : null");
                item.getChildren().add(item2);
                menu.freeActionListeners();
            });
            
            add.setOnAction(evt -> {
                TreeItem<String> item2 = new TreeItem<>("item");
                item.getChildren().add(item2);
                menu.freeActionListeners();
            });

            rename.setOnAction(evt -> startEdit());

            if (!this.isProtected) {
                if (this.isParentSingleNode) {
                    delete.setDisable(true);
                    add.setDisable(true);
                    rename.setDisable(false);
                }
                else if (this.isNotExpanded) {
                    delete.setDisable(true);
                    add.setDisable(true);
                    rename.setDisable(true);
                }
                else if (item.getParent() == null) {
                    delete.setDisable(true);
                    add.setDisable(false);
                    rename.setDisable(false);
                }
                else {
                    delete.setDisable(false);
                    add.setDisable(false);
                    rename.setDisable(false);
                }
            }
            else {
                rename.setDisable(true);
                delete.setDisable(true);
                add.setDisable(true);
            }
            
            if (item.getValue().contains(":")) {
                addSingle.setDisable(true);
                add.setDisable(true);
            }
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            setContextMenu("nocontext".equals(item) ? null : contextMenu.getContextMenu());
            setEditable(!"noedit".equals(item));
        }
    }

    /**
     * Check the constraints of a node
     * 
     * @param item
     *            the node
     */
    private void checkConstraintsNode(TreeItem<String> item) {

        IModel currentModel = this.model;
        this.isProtected = false;
        this.isParentSingleNode = false;
        this.isNotExpanded = false;

        this.isNotExpanded = Stream.of(currentModel.getNotExpanded()).anyMatch(s -> item.getValue().endsWith(s));
        TreeItem<String> parent = item.getParent();
        if (parent != null) {
            this.isProtected = Stream.of(currentModel.getProtectedValue()).anyMatch(s -> item.getValue().contains(s));
        }
    }

    @Override
    public void startEdit() {
        if (this.isProtected) {
            this.cancelEdit();
        }
        else {
            super.startEdit();
        }
    };

    @Override
    public void commitEdit(String newValue) {

        IModel currentModel = this.model;

        boolean notEmpty = !newValue.trim().isEmpty();
        boolean notProtectedValue = !Arrays.asList(currentModel.getProtectedValue()).contains(newValue);
        boolean notExpanded = !Arrays.stream(currentModel.getNotExpanded()).allMatch(s -> newValue.contains(s))
            || currentModel.getNotExpanded().length == 0;

        if (notEmpty && notProtectedValue && notExpanded) {

            if (getTreeItem().getParent() == null) {

                super.commitEdit(currentModel.getNewName(newValue));
            }
            else {
                super.commitEdit(newValue);
            }
        }

    }

}
