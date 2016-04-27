package fr.irit.smac.amasrenderer

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

import org.testfx.framework.junit.ApplicationTest

import spock.lang.Specification

abstract class GuiSpecification extends Specification {

   ApplicationTest fx

    void setupStage(Closure<Parent> rootNodeFactory) {
        fx = new GuiTestMixin(rootNodeFactory)
        fx.internalBefore()
    }

    static class GuiTestMixin extends ApplicationTest {
        final Closure<Parent> rootNodeFactory

        def GuiTestMixin(Closure<Parent> rootNodeFactory) {
            this.rootNodeFactory = rootNodeFactory
        }

        protected Parent getRootNode(stage) {
            return rootNodeFactory.call(stage) as Parent
        }

        @Override
        public void start(Stage stage) {
            Scene scene = new Scene(getRootNode(stage))
            stage.scene = scene
            stage.show()
        }
    }

}