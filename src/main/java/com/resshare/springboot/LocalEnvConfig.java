package com.resshare.springboot;

import com.resshare.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Chỉ khi profiles là "local"
 * Thì Configuration dưới đây mới được khởi tạo
 */
@Configuration
@Profile("local")
public class LocalEnvConfig {

	 @Autowired
	  private LocalConfig localConfig;
 
    @Bean(name = "appConfig")
    public AppConfig appConfig() {
    	
        return localConfig;
    }
   
    
}