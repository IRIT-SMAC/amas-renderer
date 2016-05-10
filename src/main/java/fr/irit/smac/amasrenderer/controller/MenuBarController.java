package fr.irit.smac.amasrenderer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

/**
 *    This controller manages the menu bar events
 */
public class MenuBarController {

   GraphService graphService = GraphService.getInstance();
   
   private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

   /**
    * On click on the menu item "Charger" in the menu "Fichier".
    * Open a file chooser to load a configuration file.
    * Generate the graph from the configuration file.
    */
   @SuppressWarnings("unchecked")
   @FXML
   public void clickMenuCharger(){
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choisir un fichier de configuration");
      FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
      fileChooser.getExtensionFilters().add(extFilter);
      File file = fileChooser.showOpenDialog(Main.getMainStage());
      graphService.createAgentGraphFromJson();
   }

   /**
    * Create as many nodes as agents in the map
    * @param map the agent map
    */
   private void fillAgentMap(HashMap<String,Object> map){
      Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry<String,Object> agent = it.next();
         graphService.addNode(agent.getKey());
      }
   }
   
   /**
    * Create all attributes for the agent in parameter
    * @param agent the agent to explore
    */
   private void fillAgentAttributes(HashMap<String,Object> agent){
      Iterator<Map.Entry<String, Object>> attributeIterator = agent.entrySet().iterator();
      while(attributeIterator.hasNext()){
         Map.Entry<String, Object> attribute = attributeIterator.next();
         String name = attribute.getKey();
         Object value = attribute.getValue();
         graphService.setNodeAttribute((String)agent.get("id"), name, value);
      }
   }

   /**
    * Create all outgoing edges of the agent in parameter
    * @param agent the agent to explore
    */
   @SuppressWarnings("unchecked")
   private void fillAgentTargets(HashMap<String, Object> agent){
      HashMap<String, Object> knowledgeMap = (HashMap<String, Object>) agent.get("knowledge");
      ArrayList<String> targets = (ArrayList<String>) knowledgeMap.get("targets");
      for(String target : targets){
         graphService.addEdge((String) agent.get("id"), target);
      }
   }

   /**
    * Empty the graph and reset the stylesheet
    */
   private void clearGraph(){
      graphService.getModel().clear();
      graphService.getModel().addAttribute("ui.stylesheet", "url(" + getClass().getResource("../view/TheTrueStyleSheet.css") + ")");
   }

   /**
    * On click on the menu item "Fermer" in the menu "Fichier"
    * Close the program
    */
   @FXML
   public void clickMenuFermer(){
       Platform.exit();
   }

   /**
    * On click on the menu item "A propos" in the menu "Aide"
    * Open a help window
    */
   @FXML
   public void clickMenuAPropos(){
      // TODO Popup à propos
      LOGGER.log(Level.INFO, "Popup à propos");
   }


}
