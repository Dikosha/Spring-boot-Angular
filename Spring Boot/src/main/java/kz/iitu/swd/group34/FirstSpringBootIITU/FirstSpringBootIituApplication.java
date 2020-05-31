package kz.iitu.swd.group34.FirstSpringBootIITU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FirstSpringBootIituApplication {


	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(FirstSpringBootIituApplication.class, args);
	}

}
