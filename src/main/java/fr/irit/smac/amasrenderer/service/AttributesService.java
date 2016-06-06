package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.IConstraintFields;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class AttributesService {

    private static AttributesService instance = new AttributesService();

    public static AttributesService getInstance() {
        return instance;
    }

    public void updateAttributesMap(String id, TreeItem<String> item, Map<String, Object> attributesMap) {

        attributesMap.clear();
        for (TreeItem<String> subItem : item.getChildren()) {

            String[] splitItem = ((String) subItem.getValue()).split(" : ");
            String keyItem = splitItem[0];
            this.updateChildrenAttributesMap(subItem, attributesMap, keyItem);
        }
    }

    public void updateChildrenAttributesMap(TreeItem<String> item, Map<String, Object> map, String key) {

        ObservableList<TreeItem<String>> node = item.getChildren();

        if (node.size() > 0) {
            Map<String, Object> newServiceMap = new HashMap<String, Object>();
            for (TreeItem<String> subItem : node) {

                String[] splitItem = ((String) subItem.getValue()).split(" : ");
                String keyItem = splitItem[0];
                updateChildrenAttributesMap(subItem, newServiceMap, keyItem);
            }
            map.put(key, newServiceMap);
        }
        else {
            String[] splitItem = ((String) item.getValue()).split(" : ");
            String value = splitItem[1];
            map.put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public void fillAttributes(HashMap<String, Object> tool, TreeItem<String> parent, IConstraintFields model) {

        String nameParent = parent.getValue();

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
