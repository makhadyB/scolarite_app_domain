package com.uahb.scolarite.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class ScolariteAppApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(ScolariteAppApplication.class, args);
	}

}
@RestController
class HelloWordController
{
	@GetMapping("/")
	public  String sayHello()
	{
		return "hello word";
	}
}