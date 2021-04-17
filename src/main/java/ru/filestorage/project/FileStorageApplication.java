package ru.filestorage.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.filestorage.project.configs.JPAConfiguration;
import ru.filestorage.project.configs.MongoConfiguration;
import ru.filestorage.project.configs.WebSecurityConfiguration;

@SpringBootApplication
@EnableSpringConfigured
@EnableTransactionManagement
@EnableWebSecurity
@EnableWebMvc
@Import({ MongoConfiguration.class, JPAConfiguration.class,
		WebSecurityConfiguration.class })
public class FileStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageApplication.class, args);
	}
}
