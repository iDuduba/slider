package com.laic.slider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@SpringBootApplication
@ImportResource(value={"spring-mybatis.xml"})
public class SliderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SliderApplication.class, args);
	}

	@Bean
	public ApplicationListener<ContextRefreshedEvent> onContextRefeshed() {
		return new ApplicationListener<ContextRefreshedEvent>() {
			@Override
			public void onApplicationEvent(ContextRefreshedEvent event) {
				log.debug("Initializating application...");
			}
		};
	}

}
