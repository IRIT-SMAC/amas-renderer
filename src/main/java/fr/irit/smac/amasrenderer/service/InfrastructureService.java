package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import javafx.collections.ObservableList;

public class InfrastructureService {

    private InfrastructureModel model;

    private static InfrastructureService instance = new InfrastructureService();

    private InfrastructureService() {
        model = new InfrastructureModel();
    }

    public static InfrastructureService getInstance() {
        return instance;
    }

    public ObservableList<String> getInfrastructure() {
        return this.model.getInfrastructure();
    }

    public void editInfrastructure(String nom) {
        this.model.editInfrastructure(nom);
    }

    public void setInfrastructure(ObservableList<String> list) {
        this.model.setInfrastructure(list);
    }

    public void createInfrastructuresFromMap(Map<String, Object> map) {
        this.model.editInfrastructure((String) map.get("className"));
    }

}
