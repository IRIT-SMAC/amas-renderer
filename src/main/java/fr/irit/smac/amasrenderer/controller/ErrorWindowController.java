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
package fr.irit.smac.amasrenderer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This controller is related to the display of an error
 */
public class ErrorWindowController implements ISecondaryWindowController {

    @FXML
    Label titleError;

    @FXML
    Text infoError;

    @FXML
    Button confButton;

    private Stage stage;

    /**
     * When the confButton is clicked, the window is closed
     */
    @FXML
    public void clickClose() {

        stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        this.stage = stage;
        titleError.setText((String) args[0]);
        infoError.setText((String) args[1]);
    }

}
