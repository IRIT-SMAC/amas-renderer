package fr.irit.smac.amasrenderer

import javafx.collections.FXCollections
import spock.lang.Shared
import spock.lang.Specification
import fr.irit.smac.amasrenderer.model.InfrastructureModel
import fr.irit.smac.amasrenderer.service.GraphService
import fr.irit.smac.amasrenderer.service.InfrastructureService
import fr.irit.smac.amasrenderer.service.LoadSaveService
import fr.irit.smac.amasrenderer.service.ToolService

class LoadJsonTest extends Specification{

    @Shared GraphService graphService
    @Shared ToolService toolService
    @Shared InfrastructureService infrastructureService
    @Shared LoadSaveService loadSaveService

    def setupSpec() {

        graphService = GraphService.getInstance()
        toolService = ToolService.getInstance()
        loadSaveService = LoadSaveService.getInstance()
        toolService.setTools(FXCollections.observableArrayList(new ArrayList<>()))
        infrastructureService = InfrastructureService.getInstance()
        graphService.createAgentGraph()
        GraphService.getInstance().setAgentMap(new HashMap<>())
        infrastructureService.setInfrastructure(new InfrastructureModel("infrastructure", new HashMap<String,Object>()))
    }

    def 'check if models are correctly created from a loaded json file'() {

        when:
        File file = new File(ClassLoader.getResource("/1infra5services12agents.json").getFile());
        loadSaveService.load(file);

        then:
        graphService.getGraph().getNodeCount() == 12
        infrastructureService.getInfrastructure().getName() == "BasicInfrastructure"
        toolService.getTools().size() == 5
    }
}