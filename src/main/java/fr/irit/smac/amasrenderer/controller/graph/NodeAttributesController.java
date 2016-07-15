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
package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.GraphService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesListCell;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import fr.irit.smac.amasrenderer.util.attributes.FeatureModelConverter;
import fr.irit.smac.amasrenderer.util.attributes.PortModelConverter;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of an agent
 */
public class NodeAttributesController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    @FXML
    private TreeView<String> treeP;

    @FXML
    private TreeView<String> treePort;

    private Stage stage;

    private AgentModel agent;

    private String newAgentName = null;

    private String id;

    private AttributesService attributesService = AttributesService.getInstance();

    @FXML
    private ListView<PortModel> listPort;

    @FXML
    private ListView<FeatureModel> listFeatures;

    @FXML
    private Button addPort;

    private ObservableList<PortModel> ports;

    private ObservableList<FeatureModel> features;

    /**
     * When the confirm button is clicked, the attributes are updated depending
     * on the tree
     */
    @FXML
    public void confirmButton() {

        // this.attributesService.updateAttributesMap(tree.getRoot().getValue(),
        // tree.getRoot(),
        // (Map<String, Object>)
        // GraphService.getInstance().getAgentMap().get(id), agent);
        // this.newAgentName = tree.getRoot().getValue();
        // this.agent.setAttribute(Const.GS_UI_LABEL, newAgentName);
        this.stage.close();
    }

    /**
     * When the cancel button is clicked, the attributes are not updated and the
     * window is closed
     */
    @FXML
    public void cancelButton() {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
    }

    @FXML
    public void addPort() {
        PortModel p = new PortModel("hello");
        ports.add(p);
        agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put("hello", p);
    }

    @FXML
    public void addFeature() {
        FeatureModel f = new FeatureModel("feature");
        features.add(f);
        agent.getCommonFeaturesModel().getFeatures().put("feature", f);
    }

    @FXML
    public void clickOnFeaturesList() {

        FeatureModel selectedLabel = this.listFeatures.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            this.tree.setRoot(root);
            root.setExpanded(true);
            this.attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);

            this.tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

                private final AttributesContextMenu contextMenu = new AttributesContextMenu(false);
                @SuppressWarnings("rawtypes")
                private final StringConverter converter = new DefaultStringConverter();

                @SuppressWarnings("unchecked")
                @Override
                public TreeCell<String> call(TreeView<String> param) {
                    return new AttributesTreeCell(this.contextMenu, this.converter,
                        agent);
                }
            });
            
            selectedLabel.nameProperty().addListener((observable, oldvalue, newvalue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatures().remove(oldvalue);
                agent.getCommonFeaturesModel().getFeatures().put(newvalue, selectedLabel);
            });
            
            features.addListener(new ListChangeListener<FeatureModel>() {

                @Override
                public void onChanged(javafx.collections.ListChangeListener.Change<? extends FeatureModel> c) {
                    while (c.next()) {
                        if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                            tree.setRoot(null);
                            agent.getCommonFeaturesModel().getFeatures().remove(selectedLabel.getName());
                        }
                    }
                }
            });
        }

    }

    @FXML
    public void clickOnList() {

        PortModel selectedLabel = this.listPort.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            this.treePort.setRoot(root);
            treePort.setEditable(true);
            this.attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);
            root.setExpanded(true);

            ports.addListener(new ListChangeListener<PortModel>() {

                @Override
                public void onChanged(javafx.collections.ListChangeListener.Change<? extends PortModel> c) {
                    while (c.next()) {
                        if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                            treePort.setRoot(null);
                            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().remove(selectedLabel.getName());
                        }
                    }
                }
            });

            selectedLabel.idProperty().addListener((observable, oldvalue, newvalue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put(newvalue, selectedLabel);
            });
            
            this.treePort.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

                private final AttributesContextMenu contextMenu = new AttributesContextMenu(false);
                @SuppressWarnings("rawtypes")
                private final StringConverter converter = new DefaultStringConverter();

                @SuppressWarnings("unchecked")
                @Override
                public TreeCell<String> call(TreeView<String> param) {
                    return new AttributesTreeCell(this.contextMenu, this.converter,
                        agent);
                }
            });
        }

    }

    @Override
    public void init(Stage stage, Object... args) {

        Node node = (Node) args[0];
        this.stage = stage;
        this.agent = GraphService.getInstance().getAgentMap().get(node.getAttribute(Const.GS_UI_LABEL));
        this.id = node.getAttribute(Const.GS_UI_LABEL);

        List<PortModel> pList = new ArrayList<>(
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().values());
        List<FeatureModel> fList = new ArrayList<>(agent.getCommonFeaturesModel().getFeatures().values());

        ports = FXCollections.observableList(pList);
        listPort.setItems(ports);

        features = FXCollections.observableList(fList);
        listFeatures.setItems(features);

        TreeItem<String> root = new TreeItem<>("PrimaryFeature");
        this.treeP.setRoot(root);
        this.attributesService.fillAttributes(agent.getPrimaryFeature().getAttributesMap(), root,
            (IModel) agent.getPrimaryFeature());

        this.treeP.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            private final AttributesContextMenu contextMenu = new AttributesContextMenu(false);
            @SuppressWarnings("rawtypes")
            private final StringConverter converter = new DefaultStringConverter();

            @SuppressWarnings("unchecked")
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(this.contextMenu, this.converter,
                    agent);
            }
        });

        listPort.setEditable(true);

        this.listPort
            .setCellFactory(p -> new AttributesListCell<PortModel>(new AttributesContextMenu(true),
                new PortModelConverter(), ports, listPort));
        
        listFeatures.setEditable(true);
        this.listFeatures
        .setCellFactory(p -> new AttributesListCell<FeatureModel>(new AttributesContextMenu(true),
            new FeatureModelConverter(), features, listFeatures));
        
        treeP.getRoot().setExpanded(true);
        treeP.setEditable(true);
    }

}