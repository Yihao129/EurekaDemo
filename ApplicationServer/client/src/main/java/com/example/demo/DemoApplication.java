package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
@EnableEurekaClient
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
   @RequestMapping(value = "/")
   public String home() {
	   
	   
	  
      return "Application";
   }
   
   
   
   
   	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/getName", method = RequestMethod.GET)
	public String getStudents() 
	{

		String response = restTemplate.exchange("http://eurekaclient/",
								HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, "").getBody();

		System.out.println("Response Received as " + response);

		return "served by: " + response;
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	

}
