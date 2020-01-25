package com.udacity.reviewsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class ReviewsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApiApplication.class, args);

	}

}
