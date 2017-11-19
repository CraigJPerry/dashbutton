package com.craigjperry.dashbutton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashButtonActionDispatcherTest {
    @Mock
    Listener listener;

    @Mock
    Action action;

    @Mock
    Action action2;
    private DashButton dashButton;

    @Before
    public void setUp() throws Exception {
        when(listener.listenForNextButtonEvent()).thenReturn("00:00:00:00:00:01");
        dashButton = new DashButton("00:00:00:00:00:01");
    }

    @Test
    public void dispatchesKnownButtonEvents() throws Exception {
        // Given
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action), listener);

        // When
        dispatcher.dispatchEvents();

        // Then
        verify(action).perform(dashButton);
    }

    @Test
    public void ignoresUnknownButtonEvents() throws Exception {
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(emptyList(), asList(action), listener);

        dispatcher.dispatchEvents();

        verify(action, never()).perform(any());
    }

    @Test
    public void runsEventsInOrder() throws Exception {
        when(action.perform(dashButton)).thenReturn(true);
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action, action2), listener);

        dispatcher.dispatchEvents();

        InOrder inOrder = inOrder(action, action2);
        inOrder.verify(action).perform(dashButton);
        inOrder.verify(action2).perform(dashButton);
    }

    @Test
    public void doesNotProceedPastFalseAction() throws Exception {
        when(action.perform(dashButton)).thenReturn(false);
        DashButtonActionDispatcher dispatcher = new DashButtonActionDispatcher(asList(dashButton), asList(action, action2), listener);

        dispatcher.dispatchEvents();

        InOrder inOrder = inOrder(action, action2);
        inOrder.verify(action).perform(dashButton);
        inOrder.verify(action2, never()).perform(dashButton);
    }
}
