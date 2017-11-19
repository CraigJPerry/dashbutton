package com.craigjperry.dashbutton.services;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleEmitterActionTest {

    @Mock
    ConsoleWriter mockConsoleWriter;

    @Test
    public void performEmitsTheConfiguredMessage() throws Exception {
        // Given
        ConsoleEmitterAction action = new ConsoleEmitterAction(mockConsoleWriter, "Hello");

        // When
        action.perform(new DashButton("00:12:34:56:78:00"));

        // Then
        verify(mockConsoleWriter).println("Hello");
    }

    @Test
    public void noCallToPerformEmitsNothing() throws Exception {
        // Given
        ConsoleEmitterAction action = new ConsoleEmitterAction(mockConsoleWriter, "Hello");

        // When perform() is NOT invoked ...

        // Then
        verify(mockConsoleWriter, never()).println(anyString());
    }
}
