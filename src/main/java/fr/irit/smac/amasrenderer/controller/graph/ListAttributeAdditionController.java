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
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeature;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Port;
import fr.irit.smac.amasrenderer.service.graph.GraphService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This controller is related to the addition of a tool
 */
public class ListAttributeAdditionController<T> implements Initializable, ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldName;

    @FXML
    private Text invalidField;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    private ObservableList<T> list;

    private String type;

    private Agent agent;

    /**
     * When the button confirm is clicked, the attribute is added if the name is
     * correct
     */
    @SuppressWarnings("unchecked")
    @FXML
    public void clickConfirm() {

        String name = textfieldName.getText();
        if (name != null
            && !name.trim().isEmpty()
            && !name.trim().contains(" ")) {

            if (type.equals(Const.PORT)
                && !agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().containsKey(name)) {

                Port port = graphService.addPort(agent, name);
                list.add((T) port);
                stage.close();
            }
            else if (type.equals(Const.FEATURE) && !agent.getCommonFeaturesModel().getFeatures().containsKey(name)) {
                AbstractFeature feature = graphService.addFeature(agent, name);
                list.add((T) feature);
                stage.close();
            }
            else {
                invalidField.setVisible(true);
            }

        }
        else {
            invalidField.setVisible(true);
        }
    }

    /**
     * When the cancel button is clicked, no attribute is added and the modal
     * window is closed
     */
    @FXML
    public void clickCancel() {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidField.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init(Stage stage, Object... args) {
        this.stage = stage;
        type = (String) args[0];
        list = (ObservableList<T>) args[1];
        agent = (Agent) args[2];

    }
}