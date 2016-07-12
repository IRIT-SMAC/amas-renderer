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

public class LoadSaveServiceTest extends Specification{

    @Shared GraphService graphService
    @Shared ToolService toolService
    @Shared InfrastructureService infrastructureService
    @Shared LoadSaveService loadSaveService

    def setupSpec() {

        graphService = GraphService.getInstance()
        toolService = ToolService.getInstance()
        toolService.setTools(FXCollections.observableArrayList(new ArrayList<>()))
        infrastructureService = InfrastructureService.getInstance()
        loadSaveService = LoadSaveService.getInstance()
    }

    def setup() {

        infrastructureService.init()
    }

    def 'check if a JSON is correctly loaded'() {

        when:
        File file = new File(ClassLoader.getResource("/1infra5services12agents.json").getFile())
        loadSaveService.load(file)

        then:
        graphService.getGraph().getNodeCount() == 12
        infrastructureService.getInfrastructure().getName() == "BasicInfrastructure"
        toolService.getTools().size() == 5
    }

    def 'check if a JSON is correctly saved'() {

        given:
        graphService.getAgentMap().clear()
        graphService.getGraph().clear()
        graphService.addNode(0,0)
        graphService.addNode(0,0)
        graphService.addNode(0,0)
        graphService.addNode(0,0)
        toolService.addTool(new ToolModel("tool", new HashMap<String,Object>()))
        toolService.addTool(new ToolModel("tool2", new HashMap<String,Object>()))
        File file = new File("./configTest.json")
        LoadSaveService.getInstance().save(file)


        when:
        LoadSaveService.getInstance().load(file)

        then:
        graphService.getGraph().getNodeCount() == 4
        infrastructureService.getInfrastructure().getName() == "BasicInfrastructure"
        toolService.getTools().size() == 6
        Files.delete(Paths.get(file.getPath()))
    }
}
