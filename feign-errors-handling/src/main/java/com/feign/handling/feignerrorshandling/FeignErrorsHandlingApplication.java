package com.feign.handling.feignerrorshandling;

import com.feign.handling.feignerrorshandling.client.JSONPlaceHolderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@RestController
@SpringBootApplication
public class FeignErrorsHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignErrorsHandlingApplication.class, args);
	}

	@Autowired
	private JSONPlaceHolderClient jsonPlaceHolderClient;

	@GetMapping("/hello/{id}")
	public String hello(@PathVariable("id") Long id) {
		return jsonPlaceHolderClient.getPostById(id);
	}

}
