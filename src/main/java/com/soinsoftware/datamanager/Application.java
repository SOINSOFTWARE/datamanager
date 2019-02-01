package com.soinsoftware.datamanager;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.soinsoftware.datamanager.dao.BankDAO;
import com.soinsoftware.datamanager.model.Bank;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(BankDAO repository) {
		return (args) -> {

			// fetch all customers
			log.info("Banks found with findAll():");
			log.info("-------------------------------");
			for (Bank bank : repository.findAll()) {
				log.info(bank.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Bank bank = repository.findById(BigInteger.valueOf(1)).get();
			log.info("Bank found with findOne(1L):");
			log.info("--------------------------------");
			log.info(bank.toString());
			log.info("");
		};
	}
}
