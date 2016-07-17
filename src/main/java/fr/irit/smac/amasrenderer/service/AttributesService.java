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

                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);

                boolean isNotExpanded = Stream.of(model.getNotExpanded())
                    .anyMatch(s -> name.endsWith(s));

                parent.getChildren().add(item);

                if (!isNotExpanded) {
                    fillAttributes((HashMap<String, Object>) value, item, model);
                    this.updateComplexNode(attributesMap, item);
                }
            }
            else {
                TreeItem<String> item = new TreeItem<>();

                if (value != null) {
                    item.setValue(name + " : " + value.toString());
                }
                else if (!name.contains(Const.NULL_STRING)) {
                    item.setValue(name + " : " + Const.NULL_STRING);
                }

                item.setExpanded(true);
                this.checkSingleNode(attributesMap, item);
                parent.getChildren().add(item);
            }
        });

        checkComplexNode(attributesMap, parent);
    }

    public void checkComplexNode(Map<String, Object> attributesMap, TreeItem<String> parent) {

        parent.getChildren().addListener((ListChangeListener<? super TreeItem<String>>) c -> {
            c.next();
            if (c.wasAdded()) {

                TreeItem<String> t = (TreeItem<String>) c.getAddedSubList().get(0);

                if (t.getValue().contains(":")) {
                    String[] val = t.getValue().split("\\:");
                    attributesMap.put(val[0].trim(), val[1]);
                    checkSingleNode(attributesMap, t);
                }
                else {
                    Map<String, Object> map = new HashMap<>();
                    attributesMap.put(t.getValue(), map);
                    this.updateComplexNode(attributesMap, t);
                    this.checkComplexNode(map, t);
                }

            }
            else if (c.wasRemoved()) {

                TreeItem<String> t = (TreeItem<String>) c.getRemoved().get(0);

                if (t.getValue().contains(":")) {
                    String[] val = t.getValue().split("\\:");
                    attributesMap.remove(val[0].trim());
                }
                else {
                    attributesMap.remove(t.getValue());
                }
            }

        });
    }

    private void updateComplexNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        item.valueProperty().addListener((c, oldValue, newValue) -> {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapItem = (Map<String, Object>) attributesMap.get(oldValue);
            attributesMap.remove(oldValue);
            attributesMap.put(newValue, mapItem);
        });

    }

    private void checkSingleNode(Map<String, Object> attributesMap, TreeItem<String> item) {

        item.valueProperty().addListener((c, oldValue, newValue) -> {
            String[] newValueSplit = newValue.split("\\:");
            String[] oldValueSplit = oldValue.split("\\:");
            attributesMap.remove(oldValueSplit[0].trim());
            attributesMap.put(newValueSplit[0].trim(), newValueSplit[1]);
        });
    }
}
