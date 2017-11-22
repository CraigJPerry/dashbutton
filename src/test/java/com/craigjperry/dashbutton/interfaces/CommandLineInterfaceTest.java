package com.craigjperry.dashbutton.interfaces;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.DashButtonActionDispatcher;
import com.craigjperry.dashbutton.NetworkListener;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.core.task.TaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInterfaceTest {
    @Mock
    DashButtonActionDispatcher dashButtonActionDispatcher;

    @Mock
    NetworkListener listener;

    @Mock
    TaskExecutor executor;

    @Test
    public void launchesDispatcherWhenListenOptionIsSpecified() throws Exception {
        // Given
        List<DashButton> dashButtons = randomNumberOfDashButtons();
        when(dashButtonActionDispatcher.consumeNextEvent()).thenReturn(false);
        CommandLineInterface cli = new CommandLineInterface(dashButtons, "testVersion", dashButtonActionDispatcher, listener, executor);

        // When
        cli.run(new DefaultApplicationArguments(new String[]{"--listen"}));

        // Then
        verify(dashButtonActionDispatcher).consumeNextEvent();
        verify(executor).execute(listener);
    }

    @Test
    public void doesNothingWhenNoCommandLineOptionsSpecified() throws Exception {
        List<DashButton> dashButtons = randomNumberOfDashButtons();
        when(dashButtonActionDispatcher.consumeNextEvent()).thenReturn(false);
        CommandLineInterface cli = new CommandLineInterface(dashButtons, "testVersion", dashButtonActionDispatcher, listener, executor);

        cli.run(new DefaultApplicationArguments(new String[]{}));

        verify(dashButtonActionDispatcher, never()).consumeNextEvent();
        verify(executor, never()).execute(listener);
    }

    private List<DashButton> randomNumberOfDashButtons() {
        int count = new Random().nextInt(10);  // 0-9
        List<DashButton> dashButtons = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            dashButtons.add(new DashButton("00:00:00:00:00:0" + i));
        }
        return dashButtons;
    }
}