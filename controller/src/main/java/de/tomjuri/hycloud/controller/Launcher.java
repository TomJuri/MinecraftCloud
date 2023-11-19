package de.tomjuri.hycloud.controller;

import dev.derklaro.aerogel.Injector;
import dev.derklaro.aerogel.auto.runtime.AutoAnnotationRegistry;

public class Launcher {
    public static void main(String[] args) {
        Injector injector = Injector.newInjector();
        AutoAnnotationRegistry registry = AutoAnnotationRegistry.newRegistry();
        registry.installBindings(Controller.class.getClassLoader(), "autoconfigure/bindings.aero", injector);
        Controller controller = injector.instance(Controller.class);
        controller.start();
    }
}