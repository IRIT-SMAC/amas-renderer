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
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeature;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Port;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenuList;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenuTree;
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
public class NodeAttributesController extends LoadSecondaryWindowController implements ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private TreeView<String> treeCommonFeatures;

    @FXML
    private TreeView<String> treePortMap;

    @FXML
    private TreeView<String> treeOtherAttributes;

    @FXML
    private ListView<Port> listPort;

    @FXML
    private ListView<AbstractFeature> listFeatures;

    @FXML
    private Button addPort;

    @FXML
    private TextField textFieldClassName;

    @FXML
    private TextField textFieldId;

    private Stage stage;

    private Agent agent;

    private AttributesService attributesService = AttributesService.getInstance();

    private GraphService graphService = GraphService.getInstance();

    private ObservableList<Port> ports;

    private ObservableList<AbstractFeature> features;

    /**
     * When the confirm button is clicked, the window is closed
     */
    @FXML
    public void confirmButton() {
        stage.close();
    }

    /**
     * When the button addPort is clicked, a port is added to the portMap of the
     * agent
     */
    @FXML
    public void addPort() {
        loadFxml(window, "view/graph/ListAttributeAddition.fxml", false, Const.PORT, ports, agent);
    }

    /**
     * When the button addFeature isClicked, a common feature is added to the
     * list of common features of the agent
     */
    @FXML
    public void addFeature() {

        loadFxml(window, "view/graph/ListAttributeAddition.fxml", false, Const.FEATURE, features, agent);
    }

    /**
     * When a common feature is selected in the list, the attributes of this
     * feature are displayed in the tree
     */
    @FXML
    public void clickOnFeatureList() {

        AbstractFeature selectedLabel = listFeatures.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            treeCommonFeatures.setRoot(root);
            root.setExpanded(true);
            attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);

            selectedLabel.nameProperty().addListener((observable, oldValue, newValue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatures().remove(oldValue);
                agent.getCommonFeaturesModel().getFeatures().put(newValue, selectedLabel);
            });

            features.addListener((ListChangeListener<? super AbstractFeature>) c -> {
                c.next();
                if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                    treeCommonFeatures.setRoot(null);
                    agent.getCommonFeaturesModel().getFeatures().remove(selectedLabel.getName());
                }
            });

            treeCommonFeatures.setCellFactory(
                c -> new AttributesTreeCell(new AttributesContextMenuTree(), new DefaultStringConverter(),
                    selectedLabel));
        }

    }

    /**
     * When a port is selected in the list, the attributes of this port are
     * displayed in the tree
     */
    @FXML
    public void clickOnPortList() {

        Port selectedLabel = listPort.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            TreeItem<String> root = new TreeItem<>(selectedLabel.getName());
            treePortMap.setRoot(root);
            attributesService.fillAttributes(selectedLabel.getAttributesMap(), root, (IModel) selectedLabel);
            root.setExpanded(true);
            ports.addListener((ListChangeListener<? super Port>) c -> {
                c.next();
                if (c.wasRemoved() && !c.wasUpdated() && !c.wasPermutated() && !c.wasReplaced()) {
                    treePortMap.setRoot(null);
                    agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap()
                        .remove(selectedLabel.getName());
                }
            });

            selectedLabel.idProperty().addListener((observable, oldValue, newValue) -> {
                root.setValue(selectedLabel.getName());
                agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().remove(oldValue);
                agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put(newValue,
                    selectedLabel);
            });

            treePortMap.setCellFactory(
                c -> new AttributesTreeCell(new AttributesContextMenuTree(), new DefaultStringConverter(),
                    selectedLabel));
        }

    }

    @Override
    public void init(Stage stage, Object... args) {

        Node node = (Node) args[0];
        this.stage = stage;
        agent = graphService.getAgentMap().get(node.getAttribute(Const.GS_UI_LABEL));

        initTextFields();
        initPortMap();
        initCommonFeatures();
        initOthersAttributes();
    }

    /**
     * Initialises the textFields of the window
     */
    private void initTextFields() {

        textFieldClassName.textProperty()
            .addListener(c -> agent.getCommonFeaturesModel().setClassName(textFieldClassName.getText()));
        textFieldClassName.setText(agent.getCommonFeaturesModel().getClassName());
        textFieldId.setText(agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().getId());
        textFieldId.textProperty().addListener(c -> {
            String id = textFieldId.getText();
            agent.setName(id);
            agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().setId(id);
            treeOtherAttributes.getRoot().setValue(id);
        });
    }

    /**
     * Initialises the tree of the other attributes of the agent
     */
    private void initOthersAttributes() {

        TreeItem<String> root = new TreeItem<>(agent.getName());
        treeOtherAttributes.setRoot(root);
        attributesService.fillAttributes(agent.getAttributesMap(), root, (IModel) agent);
        treeOtherAttributes.setEditable(true);
        root.setExpanded(true);

        treeOtherAttributes.setCellFactory(
            c -> new AttributesTreeCell(new AttributesContextMenuTree(), new DefaultStringConverter(), agent));
    }

    /**
     * Initialises the list and the tree of the common features
     */
    private void initCommonFeatures() {

        List<AbstractFeature> fList = new ArrayList<>(agent.getCommonFeaturesModel().getFeatures().values());
        features = FXCollections.observableList(fList);
        listFeatures.setItems(features);
        listFeatures.setEditable(true);
        listFeatures
            .setCellFactory(p -> new AttributesListCell<AbstractFeature>(new AttributesContextMenuList(),
                new FeatureModelConverter(), features, listFeatures));
    }

    /**
     * Initialises the list and the tree of the port map
     */
    private void initPortMap() {

        List<Port> pList = new ArrayList<>(
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().values());
        ports = FXCollections.observableList(pList);
        listPort.setItems(ports);
        listPort.setEditable(true);
        listPort
            .setCellFactory(p -> new AttributesListCell<Port>(new AttributesContextMenuList(),
                new PortModelConverter(), ports, listPort));

        treePortMap.setEditable(true);
    }

}