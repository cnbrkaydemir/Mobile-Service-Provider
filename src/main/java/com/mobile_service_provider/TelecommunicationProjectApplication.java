package com.mobile_service_provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaRepositories("com.mobile_service_provider.repository")
@EnableCaching
@EnableWebSecurity
@ComponentScans({ @ComponentScan("com.mobile_service_provider.controller"), @ComponentScan("com.mobile_service_provider.config")})
@SpringBootApplication
public class TelecommunicationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecommunicationProjectApplication.class, args);
	}




}
