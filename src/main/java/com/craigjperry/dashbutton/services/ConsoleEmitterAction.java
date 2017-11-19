package com.craigjperry.dashbutton.services;

import com.craigjperry.dashbutton.Action;
import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;

public class ConsoleEmitterAction implements Action {
    private final ConsoleWriter consoleWriter;
    private final String message;

    public ConsoleEmitterAction(ConsoleWriter consoleWriter, String message) {
        this.consoleWriter = consoleWriter;
        this.message = message;
    }

    @Override
    public boolean perform(DashButton source) {
        consoleWriter.println(message);
        return true;
    }
}
