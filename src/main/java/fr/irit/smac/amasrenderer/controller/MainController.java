package fr.irit.smac.amasrenderer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainController implements Initializable {

    @FXML
    private GraphMainController graphMainController;
    
    @FXML
    private ServicesController servicesController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

System.out.println(graphMainController);
    }

    public GraphMainController getGraphMainController() {
        return graphMainController;
    }

    public ServicesController getServicesController() {
        return servicesController;
    }

}
