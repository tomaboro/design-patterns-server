package spring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

	@Scheduled(fixedDelay = 1000)
	public void scheduleFixedDelayTask() {
		System.out.println(
				"Fixed delay task - " + System.currentTimeMillis() / 1000);
	}
}
