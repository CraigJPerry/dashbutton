package com.craigjperry.dashbutton.services;

import com.craigjperry.dashbutton.DashButton;
import org.junit.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;


public class ConsoleEmitterActionTest {

    @Test
    public void performEmitsTheConfiguredMessage() throws Exception {
        // Given
        ConsoleSpy mockConsole = new ConsoleSpy();
        ConsoleEmitterAction action = new ConsoleEmitterAction(mockConsole, "Hello");

        // When
        action.perform(new DashButton("00:12:34:56:78:00"));

        // Then
        assertThat(mockConsole.getMessage()).isEqualTo("Hello");
    }

    @Test
    public void noCallToPerformEmitsNothing() throws Exception {
        // Given
        ConsoleSpy mockConsole = new ConsoleSpy();
        ConsoleEmitterAction action = new ConsoleEmitterAction(mockConsole, "Hello");

        // When perform() is NOT invoked ...

        // Then
        assertThat(mockConsole.getMessage()).isNull();
    }

    private static class ConsoleSpy implements Consumer<String> {
        private String message;

        public void accept(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}