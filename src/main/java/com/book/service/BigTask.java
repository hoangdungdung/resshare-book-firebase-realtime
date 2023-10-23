package com.book.service;

import com.resshare.book.FirebaseBookServices;

public class BigTask implements Runnable {
	private Thread bigTaskThread;

	public void startBigTask() {
		bigTaskThread = new Thread(this);
		bigTaskThread.start();
	}

	public void stopBigTask() {
		// Interrupt the thread so it unblocks any blocking call
		bigTaskThread.interrupt();

		// Wait until the thread exits
		try {
			bigTaskThread.join();
		} catch (InterruptedException ex) {
			// Unexpected interruption
			ex.printStackTrace();
			// System.exit(1);
		}
	}

	public void run() {

		FirebaseBookServices firebaseServices = new FirebaseBookServices();
		firebaseServices.onStart();

	}
}