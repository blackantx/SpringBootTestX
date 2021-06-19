package com.junianto.apps.test2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.junianto.apps.test2", "controller", "service"})
public class RepoxApplication {
    private static final Logger logger = LogManager.getLogger(RepoxApplication.class);
	
    public static void main(String[] args) {
		logger.info("XXXXXXXX SPRINGBOOT APPS TEST XXXXXXXXXXXX");
		SpringApplication.run(RepoxApplication.class, args);
	}

}
