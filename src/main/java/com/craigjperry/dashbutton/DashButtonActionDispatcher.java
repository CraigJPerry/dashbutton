package com.craigjperry.dashbutton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Component
public class DashButtonActionDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashButtonActionDispatcher.class);

    private final Map<String, DashButton> dashButtonsByMacAddress;
    private final List<Action> actions;
    private final BlockingQueue<WhoHasArpPacketEvent> eventsQueue;

    public DashButtonActionDispatcher(List<DashButton> dashButtons, List<Action> actions, BlockingQueue<WhoHasArpPacketEvent> eventsQueue) {
        dashButtonsByMacAddress = dashButtons.stream().collect(Collectors.toMap(k -> k.getMacAddress().toLowerCase(), v -> v));
        this.actions = actions;
        this.eventsQueue = eventsQueue;
    }

    public boolean consumeNextEvent() {
        Optional<WhoHasArpPacketEvent> whoHasArpPacketEvent = quietlyTakeNext();
        whoHasArpPacketEvent.ifPresent(this::dispatchEvent);
        return true;
    }

    private void dispatchEvent(WhoHasArpPacketEvent whoHasArpPacketEvent) {
        String macAddress = whoHasArpPacketEvent.getMacAddress().toLowerCase();
        if (dashButtonsByMacAddress.containsKey(macAddress)) {
            LOGGER.info("Performing actions for dash button [{}]", macAddress);
            notifyActions(dashButtonsByMacAddress.get(macAddress));
        } else {
            LOGGER.info("Unknown mac address [{}] broadcast an ARP who-has request", macAddress);
        }
    }

    private Optional<WhoHasArpPacketEvent> quietlyTakeNext() {
        try {
            return Optional.of(eventsQueue.take());
        } catch (InterruptedException e) {
            LOGGER.warn("Thread interrupted when waiting for next consumable event from the queue because: {}", e);
        }
        return Optional.empty();
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
