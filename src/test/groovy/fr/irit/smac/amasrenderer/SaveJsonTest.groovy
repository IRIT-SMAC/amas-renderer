package fr.irit.smac.amasrenderer

import java.nio.file.Files
import java.nio.file.Paths

import javafx.collections.FXCollections
import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.model.ToolModel
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService
import fr.irit.smac.amasrenderer.service.LoadSaveService
import fr.irit.smac.amasrenderer.service.ToolService

public class SaveJsonTest extends Specification{

    @Shared GraphService graphService
    @Shared ToolService toolService
    @Shared InfrastructureService infrastructureService

    /**
     * Setup spec.
     *
     * @return the java.lang. object
     */
    def setupSpec() {

        graphService = GraphService.getInstance()
        toolService = ToolService.getInstance()
        toolService.setTools(FXCollections.observableArrayList(new ArrayList<>()))
        infrastructureService = InfrastructureService.getInstance()
        graphService.createAgentGraph()
        GraphService.getInstance().setAgentMap(new HashMap<>())
        infrastructureService.init();
    }

    def 'check if a JSON is correctly saved'() {

        given:
        graphService.addNode(0,0)
        graphService.addNode(0,0)
        graphService.addNode(0,0)
        toolService.addTool(new ToolModel("tool", new HashMap<String,Object>()))
        toolService.addTool(new ToolModel("tool2", new HashMap<String,Object>()))
        File file = new File("./configTest.json")

        when:
        LoadSaveService.getInstance().save(file)
        LoadSaveService.getInstance().load(file)

        then:
        graphService.getGraph().getNodeCount() == 3
        infrastructureService.getInfrastructure().getName() == "BasicInfrastructure"
        toolService.getTools().size() == 4
        Files.delete(Paths.get(file.getPath()));
    }
}
