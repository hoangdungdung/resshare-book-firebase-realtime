package com.book.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.FirebaseBookServices;

import htmldriver.Phantom;

/**
 * Auth snippets for documentation.
 *
 * See: https://firebase.google.com/docs/auth/admin
 */
public class DeleteDataManager {

	// private static final String DATABASE_URL =
	// "https://book2-320f8.firebaseio.com/";
	// private static final//
	static String DATABASE_URL;// ="https://resshare-prd-v1-00-00.firebaseio.com";

	public static void main(String[] args) throws InterruptedException {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			DATABASE_URL = prop.getProperty("database");
			Phantom.phantomjs = prop.getProperty("phantomjs");
			System.out.println(DATABASE_URL);

			// System.out.println(prop.getProperty("dbuser"));
			// System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("main execution started");

		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("service-account.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).setDatabaseUrl(DATABASE_URL)
					.build();
			FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

			FirebaseAuth.getInstance().getUserByEmail("hoangdung1008@gmail.com").addOnSuccessListener(userRecord -> {
				// See the UserRecord reference doc for the contents of
				// userRecord.
				System.out.println("Successfully fetched user data: " + userRecord.getUid());
			}).addOnFailureListener(e -> {
				System.err.println("Error fetching user data: " + e.getMessage());
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FirebaseBookServices firebaseServices = new FirebaseBookServices();
		FirebaseDatabase database = FirebaseDatabase.getInstance();

		// DatabaseReference firebaseServiceManager =
		// database.getReference("service_manager");
		//
		//
		// firebaseServiceManager.addChildEventListener(new ChildEventListener() {
		//
		// @Override
		// public void onChildRemoved(DataSnapshot snapshot) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// if (snapshot.getKey().equals("status")) {
		// String value = snapshot.getValue(String.class);
		// if ("start".equals(value)) {
		// firebaseServices.onStart();
		//
		// } else {
		// if ("stop".equals(value))
		//
		// firebaseServices.onStop();
		// }
		// }
		// }
		//
		// @Override
		// public void onCancelled(DatabaseError error) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		try {
			
			DatabaseReference firebaseusers = database.getReference("users").child("hghghghg");
			
			
			firebaseusers.addValueEventListener(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot snapshot) {
					if(!snapshot.exists())
					{
						System.out.println("not eixit");
					}else
					{
						System.out.println("  eixit");
					}
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub
					
				}
			});
			
			System.out.println("  service_manager");
			  database.getReference("map").setValue(null);


		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
