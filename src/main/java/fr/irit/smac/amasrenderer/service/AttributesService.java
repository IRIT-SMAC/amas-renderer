package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.IModel;
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

        Map<String, Object> map = new HashMap<>();

        if (model != null) {
            for (String notExpanded : model.getNotExpanded()) {
                for (Map.Entry<String, Object> entry : attributesMap.entrySet()) {
                    if (entry.getKey().contains(notExpanded)) {
                        map.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        attributesMap.clear();
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
            map.put(item.getValue(), node.get(0).getValue());
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

        parent.setExpanded(true);
        Iterator<Map.Entry<String, Object>> attributeIterator = attributesMap.entrySet().iterator();
        while (attributeIterator.hasNext()) {
            Map.Entry<String, Object> attribute = attributeIterator.next();
            String name = attribute.getKey();
            Object value = attribute.getValue();

            if (value instanceof HashMap<?, ?>) {

                boolean isNotExpanded = false;
                if (model != null) {
                    for (String notExpanded : model.getNotExpanded()) {
                        if (name.contains(notExpanded)) {
                            isNotExpanded = true;
                        }
                    }
                }
                if (!isNotExpanded) {
                    TreeItem<String> item = new TreeItem<>();
                    item.setValue(name);
                    fillAttributes((HashMap<String, Object>) value, item, model);
                    parent.getChildren().add(item);
                }
            }
            else {
                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);
                TreeItem<String> item2 = new TreeItem<>();
                item2.setValue(value.toString());
                item.getChildren().add(item2);
                item.setExpanded(true);
                parent.getChildren().add(item);
            }
        }

    }

}
