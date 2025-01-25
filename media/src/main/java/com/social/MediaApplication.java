package com.social;

import com.social.models.SocialProfile;
import com.social.models.SocialUser;
import com.social.services.SocialService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaApplication.class, args);
	}

	//you can initialize a object like this also. after running this, if you run get, you'll get these values


}
