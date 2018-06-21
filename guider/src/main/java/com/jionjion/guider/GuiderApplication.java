package com.jionjion.guider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GuiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuiderApplication.class, args);
	}
}
