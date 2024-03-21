package com.kumar.reactive;

import com.kumar.reactive.model.Student;
import com.kumar.reactive.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveProgrammingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			StudentService service
    ){
		return args->{
			for (int i = 0; i < 100; i++) {
				service.save(
						Student.builder()
								.firstName("kumar"+i)
								.lastName("dev"+i)
								.age(i)
								.build()
				);
			}
		};
	}
}
