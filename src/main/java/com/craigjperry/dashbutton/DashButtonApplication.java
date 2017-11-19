package com.craigjperry.dashbutton;

import com.craigjperry.dashbutton.drivers.ConsoleWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DashButtonApplication {

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
		SpringApplication.run(DashButtonApplication.class, args);
	}
}
