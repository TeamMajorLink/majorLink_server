package com.example.majorLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing		// @CreatedDate, @LastModifiedDate 사용을 위해 추가
@SpringBootApplication
public class MajorLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MajorLinkApplication.class, args);
	}

}
