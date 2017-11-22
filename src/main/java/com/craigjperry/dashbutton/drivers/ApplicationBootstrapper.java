package com.craigjperry.dashbutton.drivers;

import com.craigjperry.dashbutton.DashButton;
import com.craigjperry.dashbutton.WhoHasArpPacketEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = "com.craigjperry.dashbutton")
public class ApplicationBootstrapper {

    @Value("${dash.button.mac.address}")
    private List<String> macAddresses;

    @Bean
    public List<DashButton> dashButtons() {
        return macAddresses.stream().map(DashButton::new).collect(Collectors.toList());
    }

    @Bean
    public BlockingQueue<WhoHasArpPacketEvent> eventsQueue() {
        return new ArrayBlockingQueue<>(10);
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(1);
        executor.setThreadNamePrefix("worker-pool");
        executor.setDaemon(true);
        return executor;
    }

    @Bean
    public static ConversionService conversionService() {
        return new DefaultFormattingConversionService();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrapper.class, args).close();
        // @see CommandLineInterface for application entry point
    }
}
