package com.ammbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ammbr")
public class AmmbrApplication extends SpringBootServletInitializer{
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(AmmbrApplication.class);
	 }

	public static void main(String[] args) {
		SpringApplication.run(AmmbrApplication.class, args);
		System.out.println("hurrrrrrrrrrrr");

	}

}
