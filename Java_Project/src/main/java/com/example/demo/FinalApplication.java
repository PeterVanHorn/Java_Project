// Peter Van Horn
// JavaII Final Project
// 12/12/2023
// main .java file used to run the whole thing. unchanged from previous JAVAII lab.

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class FinalApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinalApplication.class, args);
	}
}