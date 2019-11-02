package com.cholnhial.ireviewmovies;

import com.cholnhial.ireviewmovies.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class IreviewmoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IreviewmoviesApplication.class, args);
	}

}
