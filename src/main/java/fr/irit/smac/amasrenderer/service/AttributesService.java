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
package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;

/**
 * This service allows to fill a tree (for the attributes) and to updates the
 * attributes depending of this tree
 */
public class AttributesService {

    private static AttributesService instance = new AttributesService();

    private AttributesService() {
    }

    public static AttributesService getInstance() {
        return instance;
    }

    /**
     * Recursive method allowing to fill the tree depending on the attributes
     * map
     * 
     * @param attributesMap
     *            the attributes
     * @param parent
     *            the current part of the tree
     * @param model
     *            the model
     */
    @SuppressWarnings("unchecked")
    public void fillAttributes(Map<String, Object> attributesMap, TreeItem<String> parent, IModel model) {

        attributesMap.forEach((k, v) -> {
            String name = k;
            Object value = v;

            if (value instanceof HashMap<?, ?>) {
                fillAttributesParent(attributesMap, parent, model, (Map<String, Object>) value, name);
            }
            else {
                fillAttributesSingle(attributesMap, parent, (String) value, name);
            }
        });

        checkAddOrRemoveNode(attributesMap, parent);
    }

    /**
     * Adds to the given tree a single node
     * 
     * @param attributesMap
     * @param parent
     * @param value
     * @param name
     */
    private void fillAttributesSingle(Map<String, Object> attributesMap, TreeItem<String> parent, String value,
        String name) {

        TreeItem<String> item = new TreeItem<>();

        if (value != null) {
            item.setValue(name + " : " + value);
        }
        else if (!name.contains(Const.NULL_STRING)) {
            item.setValue(name + " : " + Const.NULL_STRING);
        }

        item.setExpanded(true);
        updateValueSingleNode(attributesMap, item);
        parent.getChildren().add(item);
    }

    /**
     * Adds to the given tree a complex node
     * 
     * @param attributesMap
     * @param parent
     * @param model
     * @param name
     */
    private void fillAttributesParent(Map<String, Object> attributesMap, TreeItem<String> parent, IModel model,
        Map<String, Object> value, String name) {

        TreeItem<String> item = new TreeItem<>();
        item.setValue(name);

        boolean isNotExpanded = Stream.of(model.getNotExpanded())
            .anyMatch(s -> name.endsWith(s));

        parent.getChildren().add(item);

        if (!isNotExpanded) {
            fillAttributes(value, item, model);
            updateValueComplexNode(attributesMap, item);
        }
    }

    /**
     * Updates the attributesMap when an item is added or removed
     * 
     * @param attributesMap
     * @param parent
     */
    public void checkAddOrRemoveNode(Map<String, Object> attributesMap, TreeItem<String> parent) {

        parent.getChildren().addListener((ListChangeListener<? super TreeItem<String>>) c -> {
            c.next();
            if (c.wasAdded()) {
                checkAddNode(attributesMap, (TreeItem<String>) c.getAddedSubList().get(0));
            }
            else if (c.wasRemoved()) {
                checkRemoveNode(attributesMap, (TreeItem<String>) c.getRemoved().get(0));
            }

        });
    }

    /**
     * Updates the attributesMap when an item is removed
     * 
     * @param attributesMap
     * @param item
     */
    private void checkRemoveNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        if (item.getValue().contains(":")) {
            String[] val = item.getValue().split("\\:");
            attributesMap.remove(val[0].trim());
        }
        else {
            attributesMap.remove(item.getValue());
        }
    }

    /**
     * Updates the attributesMap when an item is added
     * 
     * @param attributesMap
     * @param item
     */
    private void checkAddNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        if (item.getValue().contains(":")) {
            String[] val = item.getValue().split("\\:");
            attributesMap.put(val[0].trim(), val[1]);
            updateValueSingleNode(attributesMap, item);
        }
        else {
            Map<String, Object> map = new HashMap<>();
            attributesMap.put(item.getValue(), map);
            updateValueComplexNode(attributesMap, item);
            checkAddOrRemoveNode(map, item);
        }
    }

    /**
     * Updates the attributesMap when the value of the complex item is updated
     * 
     * @param attributesMap
     * @param item
     */
    private void updateValueComplexNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        item.valueProperty().addListener((c, oldValue, newValue) -> {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapItem = (Map<String, Object>) attributesMap.get(oldValue);
            attributesMap.remove(oldValue);
            attributesMap.put(newValue, mapItem);
        });

    }

    /**
     * Updates the attributesMap when the value of the item is updated
     * 
     * @param attributesMap
     * @param item
     */
    private void updateValueSingleNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        item.valueProperty().addListener((c, oldValue, newValue) -> {
            String[] newValueSplit = newValue.split("\\:");
            String[] oldValueSplit = oldValue.split("\\:");
            attributesMap.remove(oldValueSplit[0].trim());
            attributesMap.put(newValueSplit[0].trim(), newValueSplit[1]);
        });
    }
}
