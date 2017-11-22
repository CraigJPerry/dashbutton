package com.craigjperry.dashbutton.interfaces;

import com.craigjperry.dashbutton.Action;
import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsoleEmitterAction implements Action {
    private final ConsoleWriter consoleWriter;
    private final String message;

    public ConsoleEmitterAction(ConsoleWriter consoleWriter, @Value("${console.emitter.action.message}") String message) {
        this.consoleWriter = consoleWriter;
        this.message = message;
    }

    @Override
    public boolean perform(DashButton source) {
        consoleWriter.println(message);
        return true;
    }
}
