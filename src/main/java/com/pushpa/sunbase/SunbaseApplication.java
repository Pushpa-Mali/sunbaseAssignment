package com.pushpa.sunbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class SunbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunbaseApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// Configure the message converters, including the JSON converter.
		// Configure message converters with Jackson converter
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(); // Note the correct usage of <>

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		// Set supported media types to handle different content types
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));

		messageConverters.add(converter);

		restTemplate.setMessageConverters(messageConverters);

		return restTemplate;
	}
}
