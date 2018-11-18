package com.crud.kodillalibrary;

import com.crud.kodillalibrary.jsonformatting.LocalDateDeserializer;
import com.crud.kodillalibrary.jsonformatting.LocalDateSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@SpringBootApplication
public class KodillaLibraryApplication {

	public static final DateTimeFormatter FORMATTER = ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		SpringApplication.run(KodillaLibraryApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper serializingObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}
}
