package ru.myapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.config.ActivityToggle;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RestController
	@RequestMapping(path = "activity")
	@RequiredArgsConstructor
	public static class ActivitiesRestController {

		private final ActivityToggle activityToggle;

		@GetMapping
		public Boolean getIsActive() {
			return activityToggle.getEnabled();
		}
	}
}
