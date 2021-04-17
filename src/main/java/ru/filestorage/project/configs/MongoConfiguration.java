package ru.filestorage.project.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;

@Configuration
@ComponentScan("ru.filestorage.*")
@PropertySource("classpath:application.properties")
public class MongoConfiguration extends AbstractMongoConfiguration {

	private final static String BUCKET_FILES = "files";
	
	@Autowired
	private Environment env;

	@Override
	public String getDatabaseName() {
		return env.getProperty("mongoName");
	}

	@Override
	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(env.getProperty("mongoURL"),
				Integer.parseInt(env.getProperty("mongoPort")));
	}

	@Bean
	public SimpleMongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public GridFS gridFs() {
		return new GridFS(mongoDbFactory().getLegacyDb(), BUCKET_FILES);
	}

}