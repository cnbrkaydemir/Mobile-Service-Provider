package com.mobile_service_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.mobile_service_provider.repository")
@EnableCaching
@ComponentScans({ @ComponentScan("com.mobile_service_provider.controller"), @ComponentScan("com.mobile_service_provider.config")})
@SpringBootApplication
public class TelecommunicationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecommunicationProjectApplication.class, args);
	}

}
