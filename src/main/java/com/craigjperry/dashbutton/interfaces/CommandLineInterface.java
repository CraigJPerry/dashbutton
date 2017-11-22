package com.craigjperry.dashbutton.interfaces;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.DashButtonActionDispatcher;
import com.craigjperry.dashbutton.NetworkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineInterface implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineInterface.class);

    private final List<DashButton> dashButtons;
    private final String version;
    private final DashButtonActionDispatcher dashButtonActionDispatcher;
    private final NetworkListener listener;
    private final TaskExecutor executor;

    public CommandLineInterface(List<DashButton> dashButtons, @Value("${application.version}") String version, DashButtonActionDispatcher dashButtonActionDispatcher, NetworkListener listener, TaskExecutor executor) {
        this.dashButtons = dashButtons;
        this.version = version;
        this.dashButtonActionDispatcher = dashButtonActionDispatcher;
        this.listener = listener;
        this.executor = executor;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Dash Button v{}", version);
        if (args.containsOption("listen")) {
            LOGGER.info("Listening for [{}] Dash Buttons", dashButtons.size());
            executor.execute(listener);
            for (boolean shouldContinue = true; shouldContinue; ) {
                shouldContinue = dashButtonActionDispatcher.consumeNextEvent();
            }
        }
    }
}
