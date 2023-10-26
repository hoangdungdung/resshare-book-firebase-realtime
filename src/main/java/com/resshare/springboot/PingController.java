package com.resshare.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ping")
public class PingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

	@GetMapping
	public ResponseEntity<String> ping() {
		final String LOG_HEADER = "[ping]: ";
		LOGGER.info("{} ping in action", LOG_HEADER);
		return ResponseEntity.status(HttpStatus.OK).body("API is running ...");

	}

}
