package com.craigjperry.dashbutton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashButtonActionDispatcherTest {
    @Mock
    Action action;

    @Mock
    Action action2;

    private DashButton dashButton;
    private BlockingQueue<WhoHasArpPacketEvent> eventsQueue;

    @Before
    public void setUp() throws Exception {
        String macAddress = "00:00:00:00:00:01";
        dashButton = new DashButton(macAddress);
        eventsQueue = new ArrayBlockingQueue<WhoHasArpPacketEvent>(1);
        eventsQueue.put(new WhoHasArpPacketEvent(LocalDateTime.now(), macAddress));
    }

    @Test
    public void dispatchesKnownButtonEvents() throws Exception {
        // Given
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action), eventsQueue);

        // When
        dispatcher.consumeNextEvent();

        // Then
        verify(action).perform(dashButton);
    }

    @Test
    public void ignoresUnknownButtonEvents() throws Exception {
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(emptyList(), asList(action), eventsQueue);

        dispatcher.consumeNextEvent();

        verify(action, never()).perform(any());
    }

    @Test
    public void runsActionsInOrder() throws Exception {
        when(action.perform(dashButton)).thenReturn(true);
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action, action2), eventsQueue);

        dispatcher.consumeNextEvent();

        InOrder inOrder = inOrder(action, action2);
        inOrder.verify(action).perform(dashButton);
        inOrder.verify(action2).perform(dashButton);
    }

    @Test
    public void doesNotProceedPastFalseAction() throws Exception {
        when(action.perform(dashButton)).thenReturn(false);
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action, action2), eventsQueue);

        dispatcher.consumeNextEvent();

        InOrder inOrder = inOrder(action, action2);
        inOrder.verify(action).perform(dashButton);
        inOrder.verify(action2, never()).perform(dashButton);
    }
}
