package com.craigjperry.dashbutton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DashButtonActionDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashButtonActionDispatcher.class);

    private final Map<String, DashButton> dashButtonsByMacAddress;
    private final List<Action> actions;
    private final Listener listener;

    public DashButtonActionDispatcher(List<DashButton> dashButtons, List<Action> actions, Listener listener) {
        dashButtonsByMacAddress = dashButtons.stream().collect(Collectors.toMap(k -> k.getMacAddress(), v -> v));
        this.actions = actions;
        this.listener = listener;
    }

    public void dispatchEvents() {
        String macAddress = listener.listenForNextButtonEvent();
        if (dashButtonsByMacAddress.containsKey(macAddress)) {
            LOGGER.info("Performing actions for dash button [{}]", macAddress);
            notifyActions(dashButtonsByMacAddress.get(macAddress));
        } else {
            LOGGER.info("Unknown dash button [{}] woke up", macAddress);
        }
    }

    private void notifyActions(DashButton dashButton) {
        for (Action action : actions) {
            boolean shouldContinue = action.perform(dashButton);
            if (!shouldContinue) {
                return;
            }
        }
    }
}
