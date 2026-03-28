package com.Journal.journalDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class JournalDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalDemoApplication.class, args);
	}
	@Bean
//	so java developers kept PlatformTransactionManager
//	as interface because dbms can have diff implementation
	public PlatformTransactionManager fun(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}
}

