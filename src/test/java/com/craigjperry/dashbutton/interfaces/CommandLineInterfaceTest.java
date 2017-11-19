package com.craigjperry.dashbutton.interfaces;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInterfaceTest {
    @Mock
    ConsoleWriter consoleWriter;

    @Test
    public void printsWelcomeTextOnStartup() throws Exception {
        // Given
        List<DashButton> dashButtons = randomNumberOfDashButtons();
        CommandLineInterface cli = new CommandLineInterface(consoleWriter, dashButtons, "testVersion");

        // When
        cli.run(new DefaultApplicationArguments(new String[]{}));

        // Then
        verify(consoleWriter).println("DashButton vtestVersion");
        verify(consoleWriter).println("> Listening for " + dashButtons.size() + " Dash Buttons");
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