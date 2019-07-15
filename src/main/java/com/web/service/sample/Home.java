package com.web.service.sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* 
 * @formatter:off
 * - REST API url: http://localhost:8880/
 * - Swagger url: http://localhost:8880/swagger-ui.html#/sample-controller
 */
@Configuration
@ComponentScan
@SpringBootApplication
public class Home {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Home.class).web(WebApplicationType.SERVLET).run(args);
	}

}
