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

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesListCell;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import fr.irit.smac.amasrenderer.util.attributes.FeatureModelConverter;
import fr.irit.smac.amasrenderer.util.attributes.PortModelConverter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of an agent
 */
public class NodeAttributesController implements ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private TreeView<String> treeCommonFeatures;

    @FXML
    private TreeView<String> treePrimaryFeature;

    @FXML
    private TreeView<String> treePortMap;

    @FXML
    private TreeView<String> treeOtherAttributes;

    @FXML
    private ListView<PortModel> listPort;

    @FXML
    private ListView<AbstractFeatureModel> listFeatures;

    @FXML
    private Button addPort;

    @FXML
    private TextField textFieldClassName;

    @FXML
    private TextField textFieldId;
    
    private Stage stage;

    private AgentModel agent;

    private AttributesService attributesService = AttributesService.getInstance();

    private GraphService graphService = GraphService.getInstance();

    private ObservableList<PortModel> ports;

    private ObservableList<AbstractFeatureModel> features;

    /**
     * When the confirm button is clicked, the attributes are updated depending
     * on the tree
     */
    @FXML
    public void confirmButton() {
        stage.close();
    }

    @FXML
    public void addPort() {
        PortModel port = graphService.addPort(agent);
        ports.add(port);
    }

    @FXML
    public void addFeature() {

        FeatureModel feature = graphService.addFeature(agent);
        features.add(feature);

    }

    @FXML
    public void clickOnFeatureList() {

        AbstractFeatureModel selectedLabel = listFeatures.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            treeCommonFeatures.setRoot(root);
            root.setExpanded(true);
            attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);

            selectedLabel.nameProperty().addListener((observable, oldvalue, newvalue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatures().remove(oldvalue);
                agent.getCommonFeaturesModel().getFeatures().put(newvalue, selectedLabel);
            });

            features.addListener((ListChangeListener<? super AbstractFeatureModel>) c -> {
                c.next();
                if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                    treeCommonFeatures.setRoot(null);
                    agent.getCommonFeaturesModel().getFeatures().remove(selectedLabel.getName());
                }
            });

            treeCommonFeatures.setCellFactory(c -> {
                return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(),
                    selectedLabel);
            });
        }

    }

    @FXML
    public void clickOnPortList() {

        PortModel selectedLabel = listPort.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            treePortMap.setRoot(root);
            treePortMap.setEditable(true);
            attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);
            root.setExpanded(true);

            ports.addListener((ListChangeListener<? super PortModel>) c -> {
                c.next();
                if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                    treePortMap.setRoot(null);
                    agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap()
                        .remove(selectedLabel.getName());
                }
            });

            selectedLabel.idProperty().addListener((observable, oldvalue, newvalue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put(newvalue,
                    selectedLabel);
            });

            treePortMap.setCellFactory(c -> {
                return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(),
                    selectedLabel);
            });
        }

    }

    @Override
    public void init(Stage stage, Object... args) {

        Node node = (Node) args[0];
        this.stage = stage;
        agent = graphService.getAgentMap().get(node.getAttribute(Const.GS_UI_LABEL));

        initTextFields();
        initPortMap();
        initPrimaryFeature();
        initCommonsFeatures();
        initOtherAttributes();
    }

    private void initTextFields() {
        
        textFieldClassName.textProperty()
            .addListener(c -> agent.getCommonFeaturesModel().setClassName(textFieldClassName.getText()));
        textFieldClassName.setText(agent.getCommonFeaturesModel().getClassName());
        textFieldId.setText(agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().getId());
        textFieldId.textProperty().addListener(
            c -> {
                String id = textFieldId.getText();
                agent.setName(id);
                agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().setId(id);
                treeOtherAttributes.getRoot().setValue(id);
            });
    }

    private void initOtherAttributes() {

        TreeItem<String> root = new TreeItem<>(agent.getName());
        treeOtherAttributes.setRoot(root);
        attributesService.fillAttributes(agent.getAttributesMap(), root, (IModel) agent);
        treeOtherAttributes.setEditable(true);
        root.setExpanded(true);

        treeOtherAttributes.setCellFactory(c -> {
            return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(), agent);
        });
    }

    private void initCommonsFeatures() {

        List<AbstractFeatureModel> fList = new ArrayList<>(agent.getCommonFeaturesModel().getFeatures().values());
        features = FXCollections.observableList(fList);
        listFeatures.setItems(features);
        listFeatures.setEditable(true);
        listFeatures
            .setCellFactory(p -> new AttributesListCell<AbstractFeatureModel>(new AttributesContextMenu(true),
                new FeatureModelConverter(), features, listFeatures));

        treePrimaryFeature.getRoot().setExpanded(true);
        treePrimaryFeature.setEditable(true);
    }

    private void initPortMap() {

        List<PortModel> pList = new ArrayList<>(
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().values());
        ports = FXCollections.observableList(pList);
        listPort.setItems(ports);
        listPort.setEditable(true);
        listPort
            .setCellFactory(p -> new AttributesListCell<PortModel>(new AttributesContextMenu(true),
                new PortModelConverter(), ports, listPort));
    }

    private void initPrimaryFeature() {

        TreeItem<String> root = new TreeItem<>(Const.PRIMARY_FEATURE);
        treePrimaryFeature.setRoot(root);
        attributesService.fillAttributes(agent.getPrimaryFeature().getAttributesMap(), root,
            (IModel) agent.getPrimaryFeature());

        treePrimaryFeature.setCellFactory(c -> {
            return new AttributesTreeCell(new AttributesContextMenu(false), new DefaultStringConverter(),
                agent.getPrimaryFeature());
        });
    }

}