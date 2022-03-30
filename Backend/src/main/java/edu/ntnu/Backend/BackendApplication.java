package edu.ntnu.Backend;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.service.UserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//for testing without frontend
	//int id, String firstName, String lastName, String hash, String salt, String email, int roles
	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveUserDAO(new UserDAO("test","testington","veldigKulHash", userService.generateSalt(),"Yahoo@yahoo.com",0 ));
			TimeUnit.SECONDS.sleep(30);
			userService.deleteUserDAO(userService.findByEmail("Yahoo@yahoo.com"));
		};
	}

}
