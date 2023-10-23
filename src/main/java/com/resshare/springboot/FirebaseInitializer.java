package com.resshare.springboot;

import java.io.FileInputStream;
import java.io.IOException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

 
public class FirebaseInitializer {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);

//	@PostConstruct
//	public void onStart() {
//		logger.info("Initializing Firebase App...");
//		try {
//			this.initializeFirebaseApp();
//		} catch (IOException e) {
//			logger.error("Initializing Firebase App {}", e);
//		}
//	}

	public static void start() {
		logger.info("Initializing Firebase App...");
		try {
			new FirebaseInitializer().initializeFirebaseApp();
		} catch (Exception e) {
			logger.error("Initializing Firebase App {}", e);
		}
	}

	private void initializeFirebaseApp() throws IOException {

		// if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
		// InputStream serviceAccount =
		// FirebaseInitializer.class.getResourceAsStream("service-account.json");
		// GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		// FirebaseOptions options = new FirebaseOptions.Builder()
		// .setCredentials(credentials)
		// .build();
		//
		// FirebaseApp.initializeApp(options);
		// }
		if ((FirebaseApp.getApps() == null) || (FirebaseApp.getApps().size() == 0))
			try {
				FileInputStream serviceAccount = new FileInputStream("service-account.json");
				FirebaseOptions options;
				options = new FirebaseOptions.Builder()
						.setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).build();
				FirebaseApp.initializeApp(options);
				System.out.println("success ");

				// loadHostInstance();
				// FirebaseDatabase.getInstance().getReference("draft").setValue(null);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}