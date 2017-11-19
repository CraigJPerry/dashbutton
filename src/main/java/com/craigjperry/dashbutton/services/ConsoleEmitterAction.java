package com.craigjperry.dashbutton.services;

import com.craigjperry.dashbutton.Action;
import com.craigjperry.dashbutton.DashButton;

import java.util.function.Consumer;

public class ConsoleEmitterAction implements Action {
    private final Consumer<String> consoleWriter;
    private final String message;

    public ConsoleEmitterAction(Consumer<String> consoleWriter, String message) {
        this.consoleWriter = consoleWriter;
        this.message = message;
    }

    @Override
    public boolean perform(DashButton source) {
        consoleWriter.accept(message);
        return true;
    }
}
