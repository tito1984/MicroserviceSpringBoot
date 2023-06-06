package com.cloud.server.cloudservergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudServerGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServerGatewayApplication.class, args);
	}

}
