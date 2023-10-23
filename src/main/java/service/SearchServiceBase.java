package service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.sshare.core.StringUtil;

import model.Dashboard;
import model.RequestUsers;
import model.User;
import model.UserDistance;

public abstract class SearchServiceBase<T extends Serializable> extends ServiceBase {

	private final static int TIME_CHECK = 5000;
	private Map<String, T> mEntity;

	private Map<String, String> mKeyName;

//	public abstract String getInterestTableName();
	public SearchServiceBase() {
		 
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.err.println("onChildAdded " + snapshot.getKey());
		checkHasNewSearching(snapshot);
		
		FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
		
		
	}

	public void checkHasNewSearching(DataSnapshot snapshot) {
		try {
			// for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
			// long timeStamp = (long) snapshot.child("time_stamp").getValue();
			// long current = new Date().getTime();

			// if (current - timeStamp < TIME_CHECK) {
			String userId = (String) snapshot.child("user_id").getValue();
			String text = (String) snapshot.child("text").getValue();
			String sKey = snapshot.getKey();

//			String sCollection = getInterestTableName();
//			FirebaseDatabase.getInstance().getReference().child(sCollection).child(userId).child(sKey).setValue(text);

			// long type = (long) snapshot.child("type").getValue();
			// switch (SearchData.getTypeEnum((int) type)) {
			// case BOOK:
			onSearchBook(userId, text);
			// break;
			// case AUTHOR:
			// break;
			// case USER:
			// break;
			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onSearchBook(String userId, String textSearch) {
		System.out.println("onSearchBook " + textSearch);
//		mKeyName = getListName();
//		mEntity = getListObject();
		String textSearchEncode = StringUtil.removeAccent(textSearch);
		T book;
		FirebaseDatabase.getInstance().getReference().child(getReferenceKeyNameSeach()).child(textSearchEncode).addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
			if(snapshot.exists())
			{
				String skeyName =snapshot.getValue(String.class);
				FirebaseDatabase.getInstance().getReference().child(getReferenceNameSeach()).child(skeyName).addValueEventListener(new ValueEventListener() {
					
					@Override
					public void onDataChange(DataSnapshot snapshot) {
						if(snapshot.exists())
						{
							T book  = snapshot.getValue(getSeachClassT());
							addInterestBookToDashboard(userId, textSearch, book, skeyName);
							
						}else
						{
							addInterestNotFoundBookToDashboard(userId, textSearch);
						}
						
					}
					
				 
					private boolean getClassSeach() {
						// TODO Auto-generated method stub
						return false;
					}


					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			else
			{
				addInterestNotFoundBookToDashboard(userId, textSearch);
			}
				
			}
			
		

			 

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
//		if (mKeyName.containsKey(textSearchEncode)) {
//			String skeyName = mKeyName.get(textSearchEncode);
//			if (mEntity.containsKey(skeyName)) {
//				book = mEntity.get(skeyName);
//				addInterestBookToDashboard(userId, textSearch, book, skeyName);
//				return;
//
//			}
//		}

		 
	}
	 public abstract Class<T> getSeachClassT() ;
	public abstract String getReferenceKeyNameSeach();
	public abstract String getReferenceNameSeach();
	
//	public abstract Map<String, T> getListObject();
//
//	public abstract Map<String, String> getListName();

	public void addInterestNotFoundBookToDashboard(String userId, String textSearch) {
		System.out.println("addInterestNotFoundBookToDashboard " + textSearch);
		HashMap<String, Object> dashboard = getTextNotFound(textSearch);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(textSearch)
				.updateChildren(dashboard);
	}

	  abstract HashMap<String, Object> getTextNotFound(String textSearch) ;

	public void addInterestBookToDashboard(String userId, String textSearch, T obj,String  mKeyName) {
		// System.out.println("addInterestBookToDashboard " + book.getBook_id());
		HashMap<String, Object> dashboard = getDashboard(textSearch, obj);
	//	String id = getId(obj);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(mKeyName)
				.updateChildren(dashboard);
	//	getListUserByBookId(getId(book), userId);

	}

	public abstract String getId(T obj);

	public abstract HashMap<String, Object> getDashboard(String textSearch, T obj);

	private static final String TABLE_REQUEST_USER_DISTANCE = "request_user_distance";

	private static void getListUserByBookId(String bookId, String userId) {

		FirebaseDatabase.getInstance().getReference().child("books_users").child(bookId)
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						if (dataSnapshot != null && dataSnapshot.getValue() != null) {

							FirebaseDatabase database = FirebaseDatabase.getInstance();

							DatabaseReference firebaseLocationTemp = database.getReference(TABLE_REQUEST_USER_DISTANCE);

							String key_id_temp = firebaseLocationTemp.push().getKey();

							RequestUsers RequestUsers = new RequestUsers();
							RequestUsers.request_userId = userId;

							firebaseLocationTemp.runTransaction(new Handler() {

								@Override
								public void onComplete(DatabaseError error, boolean committed,
										DataSnapshot currentData) {
									// TODO Auto-generated method stub

								}

								@Override
								public Result doTransaction(MutableData currentData) {
									currentData.child(key_id_temp).child("request_userId").setValue(userId);

									for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
										User user = snapshot.getValue(User.class);
										user.setUser_id(snapshot.getKey());
										UserDistance userDistance = new UserDistance();
										userDistance.setUser_id(user.getUser_id());
										userDistance.setMail_address(user.getMail_address());
										// userDistance.setDistance(0);

										currentData.child(key_id_temp).child("Z-listUser")
												.child(userDistance.getUser_id()).setValue(0);

									}

									return Transaction.success(currentData);
								}
							});

						}

					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});

		// return listUsersDistance;
		// TODO Auto-generated method stub

	}

}
