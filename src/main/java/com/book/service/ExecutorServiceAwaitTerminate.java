package com.book.service;

import java.io.FileInputStream;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.FirebaseBookServices;

public class ExecutorServiceAwaitTerminate {

	private static final String DATABASE_URL = "https://book2-320f8.firebaseio.com/";

	public static void main(String[] args) throws InterruptedException {
		System.out.println("main execution started");

		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("service-account.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).setDatabaseUrl(DATABASE_URL)
					.build();
			FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FirebaseBookServices firebaseServices = new FirebaseBookServices();

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference firebaseServiceManager = database.getReference("service_manager").child("status");

		firebaseServiceManager.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (snapshot.getKey().equals("status")) {
					String value = snapshot.getValue(String.class);
					if ("start".equals(value)) {
						firebaseServices.onStart();

					} else {
						if ("stop".equals(value))
							
							firebaseServices.onStop();
					}
				}
				// TODO Auto-generated method stub

			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});

	
	 

	}
}