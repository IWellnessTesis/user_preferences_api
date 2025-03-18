package com.iwellness.preferences;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

@EnableFeignClients
@Slf4j
@SpringBootApplication
public class PreferencesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreferencesApplication.class, args);
	}
}
