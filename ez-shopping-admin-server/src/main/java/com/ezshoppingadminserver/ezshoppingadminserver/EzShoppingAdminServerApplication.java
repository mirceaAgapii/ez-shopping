package com.ezshoppingadminserver.ezshoppingadminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class EzShoppingAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzShoppingAdminServerApplication.class, args);
	}

}
