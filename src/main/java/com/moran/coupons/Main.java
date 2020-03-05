package com.moran.coupons;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);


//		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);
//		UsersApi usersApi = ctx.getBean(UsersApi.class);
//		User user = new User("Avi", "1234");
//		usersApi.createUser(user);
		
		
		// Can add additional configuration files.
		//ctx.register(SpringConfig1.class, SpringConfig2.class);
		//ctx.refresh();
	}
}
