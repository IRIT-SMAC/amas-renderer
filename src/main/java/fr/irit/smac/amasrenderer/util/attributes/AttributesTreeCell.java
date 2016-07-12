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

    private boolean isRequiredKeyComplex;
    private boolean isRequiredKeySingle;
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
            boolean root = item.getParent() == null;

            checkConstraintsNode(item);

            if (!root) {
                delete.setOnAction(evt -> {
                    item.getParent().getChildren().remove(item);
                    menu.freeActionListeners();
                });
            }

            add.setOnAction(evt -> {

                if (item.getValue().equals(Const.COMMON_FEATURES)) {

                    TreeItem<String> feature = new TreeItem<>(Const.FEATURE);

                    TreeItem<String> skill = new TreeItem<>(Const.SKILL);
                    TreeItem<String> skillVal = new TreeItem<>(Const.CLASSNAME);
                    TreeItem<String> skillClassNameVal = new TreeItem<>(
                        Const.EXAMPLE_CLASSNAME);
                    skillVal.getChildren().add(skillClassNameVal);
                    skill.getChildren().add(skillVal);
                    feature.getChildren().add(skill);

                    TreeItem<String> knowledge = new TreeItem<>(Const.KNOWLEDGE);
                    TreeItem<String> knowledgeVal = new TreeItem<>(Const.CLASSNAME);
                    TreeItem<String> knowledgeClassNameVal = new TreeItem<>(
                        Const.EXAMPLE_CLASSNAME);
                    knowledgeVal.getChildren().add(knowledgeClassNameVal);
                    knowledge.getChildren().add(knowledgeVal);
                    feature.getChildren().add(knowledge);

                    TreeItem<String> className = new TreeItem<>(Const.CLASSNAME);
                    TreeItem<String> classNameVal = new TreeItem<>(
                        Const.FEATURE_DEFAULT_CLASSNAME);
                    className.getChildren().add(classNameVal);
                    feature.getChildren().add(className);

                    item.getChildren().add(feature);
                }
                else if (item.getValue().equals(Const.PORT_MAP)) {
                    TreeItem<String> port = new TreeItem<>(Const.PORT);
                    TreeItem<String> id = new TreeItem<>(Const.ID);
                    TreeItem<String> idVal = new TreeItem<>(Const.PORT);
                    id.getChildren().add(idVal);
                    port.getChildren().add(id);
                    TreeItem<String> type = new TreeItem<>(Const.TYPE);
                    TreeItem<String> typeVal = new TreeItem<>(Const.PORT_TYPE_DEFAULT_CLASSNAME);
                    type.getChildren().add(typeVal);
                    port.getChildren().add(type);
                    TreeItem<String> className = new TreeItem<>(Const.CLASSNAME);
                    TreeItem<String> classNameVal = new TreeItem<>(
                        Const.PORT_DEFAULT_CLASSNAME);
                    className.getChildren().add(classNameVal);
                    port.getChildren().add(className);
                    item.getChildren().add(port);
                }
                else {
                    item.getChildren().add(new TreeItem<>("item"));
                }
                menu.freeActionListeners();
            });

            rename.setOnAction(evt -> startEdit());

            if (!this.isRequiredKeySingle && !this.isProtected) {
                if (this.isRequiredKeyComplex) {
                    rename.setDisable(true);
                    delete.setDisable(true);
                    add.setDisable(false);
                }
                else if (this.isParentSingleNode) {
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
        this.isRequiredKeySingle = false;
        this.isProtected = false;
        this.isRequiredKeyComplex = false;
        this.isParentSingleNode = false;
        this.isNotExpanded = false;

        this.isRequiredKeySingle = Stream.of(currentModel.getRequiredKeySingle())
            .anyMatch(s -> item.getValue().contains(s));
        this.isNotExpanded = Stream.of(currentModel.getNotExpanded()).anyMatch(s -> item.getValue().endsWith(s));
        TreeItem<String> parent = item.getParent();
        if (parent != null) {
            this.isParentSingleNode = Stream.of(currentModel.getRequiredKeySingle())
                .anyMatch(s -> item.getValue().contains(s));
            this.isProtected = Stream.of(currentModel.getProtectedValue()).anyMatch(s -> item.getValue().contains(s));
        }
        this.isRequiredKeyComplex = Stream.of(currentModel.getRequiredKeyComplex())
            .anyMatch(s -> item.getValue().contains(s));
    }

    @Override
    public void startEdit() {
        if (this.isRequiredKeySingle || this.isProtected || this.isRequiredKeyComplex) {
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
        boolean notRequiredComplex = !Arrays.asList(currentModel.getRequiredKeyComplex()).contains(newValue);
        boolean notRequiredKeySingle = !Arrays.asList(currentModel.getRequiredKeySingle()).contains(newValue);
        boolean notProtectedValue = !Arrays.asList(currentModel.getProtectedValue()).contains(newValue);
        boolean notExpanded = !Arrays.stream(currentModel.getNotExpanded()).allMatch(s -> newValue.contains(s))
            || currentModel.getNotExpanded().length == 0;

        if (notEmpty && notRequiredComplex && notRequiredKeySingle && notProtectedValue && notExpanded) {

            if (getTreeItem().getParent() == null) {
                super.commitEdit(currentModel.getNewName(newValue));
            }
            else {
                super.commitEdit(newValue);
            }
        }

    }

}
