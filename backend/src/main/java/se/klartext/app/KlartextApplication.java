package se.klartext.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
        basePackageClasses = {KlartextApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class KlartextApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlartextApplication.class, args);
	}
}
