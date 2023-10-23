package com.resshare.springboot;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.google.firebase.FirebaseApp;
import com.resshare.book.ServiceListenerBookStart;
import com.resshare.clienst.FileUploaderClient;
import com.resshare.framework.core.service.RequestClient;

@Component
public class EmbedServerCustomConfigration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	private static final Logger logger = LoggerFactory.getLogger(EmbedServerCustomConfigration.class);
	private String runNgok() {
		String backend_address;
		String ngrokPathconfig = "D:\\sunshiner\\Book\\20180318\\bookMap\\resshare-book\\ngrokconfig.properties";
		try {
			
			
			
		final NgrokClient ngrokClient = new NgrokClient.Builder().build();
		 final int port = StartServiceListenerCore.getPort();

		    final CreateTunnel createTunnel = new CreateTunnel.Builder()
		            .withAddr(port)
		            .build();
		    final Tunnel tunnel = ngrokClient.connect(createTunnel);
		    
		    System.out.println( tunnel.getPublicUrl() );
		    backend_address = tunnel.getPublicUrl().replaceFirst("http://", "");
		    Properties propconfigNgrok = StartServiceListenerCore.getConfig(ngrokPathconfig);
			 
		  propconfigNgrok.setProperty( "public_url", backend_address);
			try {
				OutputStream output = new FileOutputStream(ngrokPathconfig);

				propconfigNgrok.store(output, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
} catch (Exception e) {
		Properties propconfigNgrok = StartServiceListenerCore.getConfig(ngrokPathconfig);
		backend_address = propconfigNgrok.getProperty("public_url");
		
		System.out.println("ERROR: NgrokClient.");
		System.out.println(e.getMessage());

		 
}
		return backend_address;
	}



	public void customize(ConfigurableServletWebServerFactory factory) {
		if ((FirebaseApp.getApps() == null) || (FirebaseApp.getApps().size() == 0)) {
			try {


				Properties properties = StartServiceListenerCore.getConfig();

				String backend_address = properties.getProperty("backend_address");
				String app_name = properties.getProperty("app_name");
				String backend_key = properties.getProperty("backend_key");
				String ngrok = properties.getProperty("ngrok");
				if("true".equals(ngrok))
				backend_address = runNgok();


				Properties offsensiveProperties = StartServiceListenerCore.getConfig("offsensive.properties");
				ResshareBookApp.offsensive=  offsensiveProperties.getProperty("offsensive");
				
				
				
				FileUploaderClient.buildUIScript();
				RequestClient.registerApp(app_name, backend_key, backend_address);
			
				StartServiceListenerCore.startListener();
				ServiceListenerBookStart.startListener();

			} catch (Exception e) {
				System.out.println("ERROR: invalid service account credentials. See README.");
				System.out.println(e.getMessage());

				System.exit(1);
			}
		}
	}
}