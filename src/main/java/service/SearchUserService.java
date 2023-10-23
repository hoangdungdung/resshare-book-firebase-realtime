package service;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.resshare.book.RefFireBaseBook;

import model.Dashboard;
import model.User;
import service.cache.Cache;

public class SearchUserService extends SearchServiceBase<User> {
	private final static int TIME_CHECK = 5000;
	// static private Map<String, User> mUser = Cache.mUser;
	// static private Map<String, String> mUserName = Cache.mUserName;

	public SearchUserService() {
		super();

	}



	public String getInterestTableName() {
		return "user_interest_user";
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
		// System.err.println("onChildRemoved " + snapshot.getKey());

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		// System.err.println("onChildMoved " + snapshot.getKey());
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// System.err.println("onChildChanged " + snapshot.getKey());
		//
		// UserInterestChilldService userInterestChilldService = new
		// UserInterestChilldService("user_interest/"+ snapshot.getKey());
		// userInterestChilldService.onStart();
		//// checkHasNewSearching(snapshot);
		// // TODO Auto-generated method stub

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.err.println("onChildAdded " + snapshot.getKey());
		checkHasNewSearching(snapshot);
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		System.err.println("onCancelled " + error.getMessage());
	}

	@Override
	public String getId(User book) {
		// TODO Auto-generated method stub
		return book.getUser_id();
	}

	@Override
	public HashMap<String, Object> getDashboard(String textSearch, User book) {
		// TODO Auto-generated method stub
		return Dashboard.getMapsUserInterest(textSearch, book);
	}

	 

	 

	@Override
	HashMap<String, Object> getTextNotFound(String textSearch) {
		{
			return Dashboard.getMapsUserNotFound(textSearch);
		}
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.SEARCHING_USER;
		 
	}



	@Override
	public Class<User> getSeachClassT() {
		// TODO Auto-generated method stub
		return User.class;
	}



	@Override
	public String getReferenceKeyNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.USERS_KEY;
	}



	@Override
	public String getReferenceNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.USERS;
	}

}
