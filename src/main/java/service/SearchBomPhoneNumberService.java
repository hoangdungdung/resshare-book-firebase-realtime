package service;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;

import model.Dashboard;
import model.Dashboard.DashboardType;

public class SearchBomPhoneNumberService extends ServiceBase {
	private final static int TIME_CHECK = 5000;
	// static private Map<String, User> mUser = Cache.mUser;
	// static private Map<String, String> mUserName = Cache.mUserName;

	public SearchBomPhoneNumberService() {
		super();

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

	public void addInterestNotFoundBookToDashboard(String userId, String textSearch) {
		System.out.println("addInterestNotFoundBookToDashboard " + textSearch);
		HashMap<String, Object> dashboard = getTextNotFound(textSearch);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(textSearch)
				.updateChildren(dashboard);
	}
	
	 

	private HashMap<String, Object> getTextNotFound(String textSearch) {
		// TODO Auto-generated method stub
		{
			return Dashboard.getMapsBomPhoneNunberNotFound(textSearch);
		}
	}

	 
	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		System.err.println("onChildAdded " + snapshot.getKey());

		String userId = (String) snapshot.child("user_id").getValue();
		String text = (String) snapshot.child("text").getValue();
		String sKey = snapshot.getKey();
		FirebaseDatabase.getInstance().getReference().child("/boom_data/boom_items").child(text)
				.addValueEventListener(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot1) {
						
						if(snapshot1.exists())
						{
							//T book  = snapshot.getValue(getSeachClassT());
							 Dashboard.addPhonNumberToDashboard(  userId,   text);
							
						}else
						{
							addInterestNotFoundBookToDashboard(userId, text);
						}
						 

					}

					
					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void onCancelled(DatabaseError error) {
		System.err.println("onCancelled " + error.getMessage());
	}

	@Override
	public String getReferenceName() {
		return RefFireBaseBook.SEARCHING_COM_Phone_number;

	}

}
