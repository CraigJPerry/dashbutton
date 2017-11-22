package com.craigjperry.dashbutton.drivers;

import com.craigjperry.dashbutton.DashButton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = "com.craigjperry.dashbutton")
public class ApplicationBootstrapper {

    @Value("${dash.button.mac.address}")
    private List<String> macAddresses;

    @Bean
    public List<DashButton> dashButtons() {
        return macAddresses.stream().map(DashButton::new).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrapper.class, args).close();
        // @see CommandLineInterface for application entry point
    }
}
