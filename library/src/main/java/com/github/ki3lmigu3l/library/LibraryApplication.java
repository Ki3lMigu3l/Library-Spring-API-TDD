package com.github.ki3lmigu3l.library;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
