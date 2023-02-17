package com.jardessouza.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GerenciadorfuncionariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorfuncionariosApplication.class, args);
	}

}
