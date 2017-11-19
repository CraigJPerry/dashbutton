package com.craigjperry.dashbutton.drivers;

import com.craigjperry.dashbutton.DashButton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.craigjperry.dashbutton")
public class ApplicationBootstrapper {

	@Bean
	public DashButton dashButton1() {
		return new DashButton("00:12:34:56:78:00");
	}

	@Bean
	public DashButton dashButton2() {
		return new DashButton("00:00:12:34:56:78");
	}

	@Bean
	public ConsoleWriter consoleWriter() {
		return new ConsoleWriter(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBootstrapper.class, args);
	}
}
