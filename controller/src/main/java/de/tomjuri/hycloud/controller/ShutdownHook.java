package de.tomjuri.hycloud.controller;

public class ShutdownHook extends Thread {

    @Override
    public void run() {
        ((Controller) Controller.getInstance()).getTerminal().close();
    }
}
