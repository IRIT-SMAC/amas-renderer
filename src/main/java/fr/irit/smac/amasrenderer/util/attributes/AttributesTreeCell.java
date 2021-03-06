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

    private final AttributesContextMenuTree contextMenu;

    private boolean isProtected;

    private IModel model;

    private boolean isNotExpanded;

    public AttributesTreeCell(AttributesContextMenuTree contextMenu, StringConverter<String> converter,
        IModel model) {
        super(converter);
        this.model = model;
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.contextMenu = contextMenu;
        setOnContextMenuRequested(evt -> {
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

            checkConstraintsNode(item);
            setMenuAction(item);
            displayMenuItem(item);
        }

    }

    /**
     * Sets the action to each menu item
     * 
     * @param item
     */
    private void setMenuAction(TreeItem<String> item) {

        AttributesContextMenuTree menu = contextMenu;
        MenuItem delete = menu.getDelete();
        MenuItem rename = menu.getRename();
        MenuItem addComplex = menu.getAddComplex();
        MenuItem addSingle = menu.getAddSingle();
        boolean root = item.getParent() == null;

        if (!root) {
            delete.setOnAction(evt -> {
                item.getParent().getChildren().remove(item);
                menu.freeActionListeners();
            });
        }

        addSingle.setOnAction(evt -> {
            TreeItem<String> itemAdded = new TreeItem<>("item : null");
            item.getChildren().add(itemAdded);
            item.setExpanded(true);
            menu.freeActionListeners();
        });

        addComplex.setOnAction(evt -> {
            TreeItem<String> itemAdded = new TreeItem<>("item");
            item.getChildren().add(itemAdded);
            item.setExpanded(true);
            menu.freeActionListeners();
        });

        rename.setOnAction(evt -> startEdit());
    }

    /**
     * Enables or disables the items of the menu depending on the selected item
     * 
     * @param item
     */
    private void displayMenuItem(TreeItem<String> item) {

        AttributesContextMenuTree menu = contextMenu;
        MenuItem delete = menu.getDelete();
        MenuItem rename = menu.getRename();
        MenuItem addComplex = menu.getAddComplex();
        MenuItem addSingle = menu.getAddSingle();

        if (isProtected) {
            rename.setDisable(true);
            delete.setDisable(true);
        }
        else if (isNotExpanded) {
            delete.setDisable(true);
            addComplex.setDisable(true);
            addSingle.setDisable(true);
            rename.setDisable(true);
        }
        else if (item.getParent() == null) {
            delete.setDisable(true);
            addComplex.setDisable(false);
            rename.setDisable(false);
        }
        else {
            delete.setDisable(false);
            addComplex.setDisable(false);
            rename.setDisable(false);
        }

        if (item.getValue().contains(":")) {
            addSingle.setDisable(true);
            addComplex.setDisable(true);
        }
    }

    /**
     * Check the constraints of a node
     * 
     * @param item
     *            the node
     */
    private void checkConstraintsNode(TreeItem<String> item) {

        IModel currentModel = model;
        isProtected = false;
        isNotExpanded = false;

        if (currentModel.getNotExpanded() != null) {
            isNotExpanded = Stream.of(currentModel.getNotExpanded()).anyMatch(s -> item.getValue().endsWith(s));
        }

        TreeItem<String> parent = item.getParent();
        if (parent != null && currentModel.getProtectedValue() != null) {
            isProtected = Stream.of(currentModel.getProtectedValue()).anyMatch(s -> item.getValue().contains(s));
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

    @Override
    public void startEdit() {

        if (isProtected) {
            cancelEdit();
        }
        else {
            super.startEdit();
        }
    };

    @Override
    public void commitEdit(String newValue) {

        IModel currentModel = model;

        boolean notEmpty = !newValue.trim().isEmpty();
        boolean notProtectedValue = !Arrays.asList(currentModel.getProtectedValue()).contains(newValue);
        boolean notExpanded = !Arrays.stream(currentModel.getNotExpanded()).allMatch(s -> newValue.contains(s))
            || currentModel.getNotExpanded().length == 0;
        boolean correctSyntaxAttribute = true;

        String value = newValue;
        if (getTreeItem().getValue().contains(":")) {
            String[] newValueSplit = newValue.split("\\:");
            if (newValueSplit.length != 2) {
                correctSyntaxAttribute = false;
            }
            else {
                value = newValueSplit[0].trim() + " : " + newValueSplit[1].trim();
            }
        }

        if (notEmpty && notProtectedValue && notExpanded && correctSyntaxAttribute) {

            if (getTreeItem().getParent() == null) {
                currentModel.setName(currentModel.getNewName(value));
                super.commitEdit(currentModel.getNewName(value));
            }
            else {
                super.commitEdit(value);
            }
        }

    }

}
