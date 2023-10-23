package service;

import java.util.HashMap;
import java.util.Iterator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.resshare.book.RefFireBaseBook;

import service.cache.Cache;

public class BookSitesService extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

		// BookSites bs= snapshot.getValue(BookSites.class);
		// Cache.mBookSites.put(snapshot.getKey(), bs );
		// TODO Auto-generated method stub

		String key = "";
		String keyMaster = snapshot.getKey();

		String value = "";
		HashMap<String, Object> hsBookchild = new HashMap<>();
		for (Iterator iterator = snapshot.getChildren().iterator(); iterator.hasNext();) {

			DataSnapshot snapshot1 = (DataSnapshot) iterator.next();
			key = snapshot1.getKey();

			switch (key) {
			case "element":
				createHashElement(hsBookchild, key, snapshot1);
				break;
			case "detail":
				createHashDetail(hsBookchild, key, snapshot1);
				break;
			case "validation":
				createHashElement(hsBookchild, key, snapshot1);
				break;
			// case "detail":
			// createHashDetail(hsBookchild,key, snapshot1);
			//
			// break;
			case "url":
				keyMaster = snapshot1.getValue(String.class);
				break;
			default:
				value = snapshot1.getValue(String.class);
				hsBookchild.put(key, value);
				break;
			}

		}

		Cache.mBookSites.put(keyMaster, hsBookchild);

	}

	private void createHashElement(HashMap<String, Object> hsBookchild, String key, DataSnapshot snapshot1) {
		HashMap<String, String> hsBookElemnet = new HashMap<>();
		for (Iterator iterator1 = snapshot1.getChildren().iterator(); iterator1.hasNext();) {
			DataSnapshot snapshot2 = (DataSnapshot) iterator1.next();
			hsBookElemnet.put(snapshot2.getKey(), snapshot2.getValue(String.class));

		}
		hsBookchild.put(key, hsBookElemnet);
	}

	private void createHashDetail(HashMap<String, Object> hsBookchild, String key, DataSnapshot snapshot1) {
		HashMap<String, String> hsBookElemnet = new HashMap<>();
		for (Iterator iterator1 = snapshot1.getChildren().iterator(); iterator1.hasNext();) {
			DataSnapshot snapshot2 = (DataSnapshot) iterator1.next();
			hsBookElemnet.put(snapshot2.getValue(String.class), snapshot2.getKey());

		}
		hsBookchild.put(key, hsBookElemnet);
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_SITES;
	}
}
