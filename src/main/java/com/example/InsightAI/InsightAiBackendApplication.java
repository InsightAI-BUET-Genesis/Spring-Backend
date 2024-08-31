package com.example.InsightAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class InsightAiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightAiBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void configurePathMatch(PathMatchConfigurer configurer) {
				// Set the global prefix
				configurer.addPathPrefix("api/v1", c -> true);
			}
		};
	}
}

@RestController
@RequestMapping("/")
class HelloController {

	@GetMapping
	public String sayHello() {
		System.out.println("Called root");
		return "Hello from the root endpoint!";
	}
}
