package com.uam.pracowniaprogramowaniaprojekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracowniaProgramowaniaProjektApplication {

	public static void main(String[] args) {

//		System.setProperty("spring.profiles.active", "mysql");
		System.setProperty("spring.profiles.active", "hsql");
		SpringApplication.run(PracowniaProgramowaniaProjektApplication.class, args);
	}

}
