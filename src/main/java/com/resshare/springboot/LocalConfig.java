package com.resshare.springboot;

import com.resshare.AppConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("local")
@EnableAutoConfiguration
@PropertySource("classpath:local_config.properties")

public class LocalConfig extends AppConfig {

}
