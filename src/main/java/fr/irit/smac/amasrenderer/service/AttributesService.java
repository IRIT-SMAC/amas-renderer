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
import java.util.Iterator;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
     * Update the attributes
     * 
     * @param id
     *            the id of the model
     * @param item
     *            the tree
     * @param attributesMap
     *            the attributes of the model
     * @param model
     *            the model
     */
    public void updateAttributesMap(String id, TreeItem<String> item, Map<String, Object> attributesMap,
        IModel model) {

        attributesMap.clear();
        Map<String, Object> map = new HashMap<>();
        map.forEach((k, v) -> attributesMap.put(k, v));

        for (TreeItem<String> subItem : item.getChildren()) {
            this.updateChildrenAttributesMap(subItem, attributesMap, subItem.getValue());
        }

        if (model != null) {
            model.setName(id);
        }
    }

    /**
     * Recursive method which allows to end the update of the attributes
     * 
     * @param item
     *            the current part of the tree
     * @param map
     *            the attributes of the current node
     * @param key
     *            the key of the current attribute
     */
    public void updateChildrenAttributesMap(TreeItem<String> item, Map<String, Object> map, String key) {

        ObservableList<TreeItem<String>> node = item.getChildren();

        if (node.size() > 1 || (node.size() == 1 && !node.get(0).getChildren().isEmpty())) {
            Map<String, Object> newMap = new HashMap<>();
            for (TreeItem<String> subItem : node) {
                updateChildrenAttributesMap(subItem, newMap, subItem.getValue());
            }
            map.put(key, newMap);
        }
        else if (!node.isEmpty()) {
            if (node.get(0).getValue().equals(Const.NULL_STRING)) {
                map.put(item.getValue(), null);
            }
            else {
                map.put(item.getValue(), node.get(0).getValue());
            }
        }
        else if (item.getValue().equals(Const.PORT_MAP)) {
            map.put(item.getValue(), new HashMap<>());
        }
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

        parent.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BooleanProperty bb = (BooleanProperty) observable;
                if (model != null) {
                    for (String notExpanded : model.getNotExpanded()) {
                        if (parent.getValue().contains(notExpanded)) {
                            TreeItem<String> t = (TreeItem<String>) bb.getBean();
                            t.setExpanded(false);
                        }
                    }
                }
            }
        });

        parent.setExpanded(true);
        Iterator<Map.Entry<String, Object>> attributeIterator = attributesMap.entrySet().iterator();
        while (attributeIterator.hasNext()) {
            Map.Entry<String, Object> attribute = attributeIterator.next();
            String name = attribute.getKey();
            Object value = attribute.getValue();

            if (value instanceof HashMap<?, ?>) {

                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);
                fillAttributes((HashMap<String, Object>) value, item, model);
                parent.getChildren().add(item);
            }
            else {
                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);
                TreeItem<String> item2 = new TreeItem<>();
                if (value != null) {
                    item2.setValue(value.toString());
                }
                else {
                    item2.setValue("null");
                }
                item.getChildren().add(item2);
                item.setExpanded(true);
                parent.getChildren().add(item);
            }
        }

    }

}
