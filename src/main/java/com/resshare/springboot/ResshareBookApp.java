package com.resshare.springboot;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.google.firebase.FirebaseApp;
import com.resshare.book.ServiceListenerBookStart;
import com.resshare.clienst.FileUploaderClient;
import com.resshare.framework.core.service.RequestClient;

import ngrok.api.model.NgrokTunnel;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication(scanBasePackages = { "com.resshare" }) // same as @Configuration
																// @EnableAutoConfiguration @ComponentScan
														 		// combined
public class ResshareBookApp extends SpringBootServletInitializer {


	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		return  super.createRootApplicationContext(servletContext);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.err.println("------------------------------------");
		super.onStartup(servletContext);


	}

	static void initFireBase() {
		System.err.println("initFireBase------------------------------------");
		if ((FirebaseApp.getApps() == null) || (FirebaseApp.getApps().size() == 0)) {
			try {


				Properties properties = StartServiceListenerCore5.getConfig();

				String backend_address = properties.getProperty("backend_address");
				String app_name = properties.getProperty("app_name");
				String backend_key = properties.getProperty("backend_key");
				String ngrok = properties.getProperty("ngrok");
				if("true".equals(ngrok))
					backend_address = runNgok();


				Properties offsensiveProperties = StartServiceListenerCore.getConfig("offsensive.properties");
				offsensive=  offsensiveProperties.getProperty("offsensive");



				FileUploaderClient.buildUIScript();
				RequestClient.registerApp(app_name, backend_key, backend_address);

				StartServiceListenerCore5.startListener();
				ServiceListenerBookStart.startListener();

			} catch (Exception e) {
				System.out.println("ERROR: invalid service account credentials. See README.");
				System.out.println(e.getMessage());

				System.exit(1);
			}
		}
	}

	@Override
	protected SpringApplicationBuilder configure(	SpringApplicationBuilder builder) {



		return builder.sources(ResshareBookApp.class);
	}

	public static void main(String[] args) {
		System.err.println("------------------------------------");
		initFireBase();
		SpringApplication sa = new SpringApplication(
				ResshareBookApp.class);
		sa.run(args);
	}




	public static String offsensive;
	 
	public static String DATABASE_URL;
	// public static final Logger log =
	// LoggerFactory.getLogger(ResshareGoibinhOxyApp.class);
	//
	// public static final String MENU_APP = "system_settings/menu_config/data";
	// public static final String RESPONSES = "responses";
	// public static final String RESPONSES_HIS = "responses_his";
	// public static final String MENU_APP_HIS =
	// "system_settings/menu_config/his/data";
	// protected static final String REST_SERVICE_URI_CORE =
	// "http://localhost:8088/config";
	// public static String REST_SERVICE_URI = "http://localhost:8080/api";
	// public static String RESSHARE_REST_SERVICE_URI_DRIVER =
	// "http://localhost:8086/api";
	public static String APPLICATION_NAME;
	public static String KEY;

	// private static void postOutput(Output output) {
	// System.out.println("Testing postOutput Output API----------");
	// RestTemplate restTemplate = new RestTemplate();
	//
	// URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/output/", output,
	// Output.class);
	// System.out.println("Location : " + uri.toASCIIString());
	// }



		static 	private String runNgok() {
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
			};



}
