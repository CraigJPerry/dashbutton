package com.craigjperry.dashbutton.drivers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ConsoleWriterTest {
    @Mock
    Consumer<String> stringConsumer;

    @Test
    public void callsAcceptOnConfiguredConsumer() throws Exception {
        // Given
        ConsoleWriter consoleWriter = new ConsoleWriter(stringConsumer);
        int preventHardCodedImplementationFromPassingThisTest = new Random().nextInt();

        // When
        consoleWriter.println("Testing " + preventHardCodedImplementationFromPassingThisTest);

        // Then
        verify(stringConsumer).accept("Testing " + preventHardCodedImplementationFromPassingThisTest);
    }
}