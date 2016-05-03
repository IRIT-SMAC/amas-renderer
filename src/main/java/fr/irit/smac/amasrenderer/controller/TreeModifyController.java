package fr.irit.smac.amasrenderer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class TreeModifyController {

    @FXML
    TreeView<String> tree;

    @FXML
    public void addAttribute() {
        // NOP
        System.out.println("hello add");
    }

    @FXML
    public void modifyAttribute() {
        // NOP
        System.out.println("hello mod");
    }

    @FXML
    public void deleteAttribute() {
        // NOP
        System.out.println("hello del");
    }
}
