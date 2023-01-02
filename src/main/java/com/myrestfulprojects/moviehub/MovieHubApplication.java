package com.myrestfulprojects.moviehub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieHubApplication {
	private static final Logger log = LoggerFactory.getLogger(MovieHubApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MovieHubApplication.class, args);
		log.info("App started!");
	}

}
