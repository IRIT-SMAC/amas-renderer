package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.IModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class AttributesService {

    private static AttributesService instance = new AttributesService();

    public static AttributesService getInstance() {
        return instance;
    }

    public void updateAttributesMap(String id, TreeItem<String> item, Map<String, Object> attributesMap,
        IModel model) {

        Map<String, Object> map = new HashMap<>();
        for (String notExpanded : model.getNotExpanded()) {
            for (Map.Entry<String, Object> entry : attributesMap.entrySet()) {
                if (entry.getKey().contains(notExpanded)) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        attributesMap.clear();
        map.forEach((k, v) -> attributesMap.put(k, v));

        for (TreeItem<String> subItem : item.getChildren()) {
            this.updateChildrenAttributesMap(subItem, attributesMap, subItem.getValue());
        }
        model.setName(id);
    }

    public void updateChildrenAttributesMap(TreeItem<String> item, Map<String, Object> map, String key) {

        ObservableList<TreeItem<String>> node = item.getChildren();

        if (node.size() > 1 || (node.size() == 1 && !node.get(0).getChildren().isEmpty())) {
            Map<String, Object> newServiceMap = new HashMap<>();
            for (TreeItem<String> subItem : node) {
                updateChildrenAttributesMap(subItem, newServiceMap, subItem.getValue());
            }
            map.put(key, newServiceMap);
        }
        else if (!node.isEmpty()) {
            map.put(item.getValue(), node.get(0).getValue());
        }
    }

    @SuppressWarnings("unchecked")
    public void fillAttributes(Map<String, Object> tool, TreeItem<String> parent, IModel model) {

        parent.setExpanded(true);
        Iterator<Map.Entry<String, Object>> attributeIterator = tool.entrySet().iterator();
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
