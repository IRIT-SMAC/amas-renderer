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
package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This controller is related to the infrastructure on the main window
 */
public class InfrastructureController extends LoadSecondaryWindowController implements Initializable {

    @FXML
    private Label infrastructureLabel;

    @FXML
    private Button infrastructureButton;

    private InfrastructureService infrastructureService = InfrastructureService.getInstance();

    /**
     * When the edit button is clicked, a modal window allowing to see and
     * update the attributes is opened
     */
    @FXML
    public void handleOnEditInfraClick() {

        loadFxml(window, "view/infrastructure/InfrastructureAttributes.fxml", true,
            infrastructureService.getInfrastructure());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infrastructureService.init();

        infrastructureService.getInfrastructure().nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> infrastructureLabel.setText(newValue));
    }
}
