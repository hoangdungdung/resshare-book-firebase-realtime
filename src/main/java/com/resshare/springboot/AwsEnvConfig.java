package com.resshare.springboot;

import com.resshare.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Chỉ khi profiles là "local" Thì Configuration dưới đây mới được khởi tạo
 */
@Configuration
@Profile("aws")
public class AwsEnvConfig {

	@Autowired
	private AwsConfig awsConfig;

	@Bean(name = "appConfig")
	public AppConfig appConfig() {

		// Inside some method
		// System.out.print(globalConfig.getWebsite());

		return awsConfig;
	}

}