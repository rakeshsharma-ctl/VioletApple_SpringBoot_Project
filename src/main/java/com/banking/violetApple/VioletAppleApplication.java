package com.banking.violetApple;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@EnableAdminServer
@EnableWebMvc
public class VioletAppleApplication {
	public static void main(String[] args) {
		SpringApplication.run(VioletAppleApplication.class, args);
	}

}


