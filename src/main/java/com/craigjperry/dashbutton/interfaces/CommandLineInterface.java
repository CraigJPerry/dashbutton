package com.craigjperry.dashbutton.interfaces;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.DashButtonActionDispatcher;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandLineInterface implements ApplicationRunner {
    private final ConsoleWriter consoleWriter;
    private final List<DashButton> dashButtons;
    private final String version;
    private final DashButtonActionDispatcher dashButtonActionDispatcher;

    public CommandLineInterface(ConsoleWriter consoleWriter, List<DashButton> dashButtons, @Value("${application.version}") String version, DashButtonActionDispatcher dashButtonActionDispatcher) {
        this.consoleWriter = consoleWriter;
        this.dashButtons = dashButtons;
        this.version = version;
        this.dashButtonActionDispatcher = dashButtonActionDispatcher;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consoleWriter.println(String.format("DashButton v%s", version));
        consoleWriter.println(String.format("> Listening for %d Dash Buttons", dashButtons.size()));
        dashButtonActionDispatcher.dispatchEvents();
    }
}
