package service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;

import model.BoomItems;
import model.BoomUser;

public class BoomService extends ServiceBase {
	public BoomService() {
		super();
	}

	
	@Override
	public void onChildAdded(DataSnapshot snapshot0, String previousChildName) {
		try {
			
			System.out.println("BoomService" + snapshot0.getKey());

		BoomItems boomItems = snapshot0.getValue(BoomItems.class);
		String key =snapshot0.getKey().toString();
		
		String phone_number = boomItems.getPhone_number();
		firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_BOOM_ITEMS+"/"+phone_number+"/"+key).setValue(boomItems);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	 

	 
	public void onChildAdded1(DataSnapshot snapshot0, String previousChildName) {
		try {
			
			System.out.println("BoomService" + snapshot0.getKey());

		BoomItems boomItems = snapshot0.getValue(BoomItems.class);
		
		String phone_number = boomItems.getPhone_number();
		 
		firebaseDb.getReference( RefFireBaseBook.BOOM_DATA_PHONE_NUMBER+"/"+String.valueOf( phone_number)).addListenerForSingleValueEvent(new ValueEventListener() {
			
		
			@Override
			public void onDataChange(DataSnapshot snapshot1) {
				System.out.println("BoomService" + snapshot1.getKey());
				try {
				if(snapshot1.exists())
				{
					String key =snapshot1.getValue().toString();
					
					String path = RefFireBaseBook.BOOM_DATA_USER+"/"+key;
					firebaseDb.getReference( path).addListenerForSingleValueEvent(new ValueEventListener() {
						
						@Override
						public void onDataChange(DataSnapshot snapshot2) {
							try {
								BoomUser boomUser = snapshot2.getValue(BoomUser.class);
								
							// TODO Auto-generated method stub
//							String nunber =snapshot2.getValue().toString();
//							int inunber = Integer.parseInt(nunber);
							
							int inunber = 	boomUser.getNumber_of_bombs();
							firebaseDb.getReference(path+"/number_of_bombs").setValue(inunber+1);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
						}
						
						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub
							
						}
					} );
					 
					firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_BOOM_ITEMS+"/"+key).push().setValue(boomItems);
					
				}
				else
				{
					BoomUser boomUser = snapshot0.getValue(BoomUser.class);
					boomUser.setNumber_of_bombs(1);
					String key =firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_USER).push().getKey();
					firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_USER+"/"+key).setValue(boomUser);
					firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_PHONE_NUMBER+"/"+phone_number).setValue(key);
					firebaseDb.getReference(RefFireBaseBook.BOOM_DATA_BOOM_ITEMS+"/"+key).push().setValue(boomItems);
					 
					
					
				}
				// TODO Auto-generated method stub
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		/*BookItems bookItems = snapshot.getValue(BookItems.class);
//		bookItemsNoa.setBook_item(bookItems);
//		Books book = snapshot.getValue(Books.class);
//		bookItemsNoa.setBook(book);
//		firebaseDb.getReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA).push().setValue(bookItemsNoa);
//		firebaseDb.getReference(getReferenceName()).child( snapshot.getKey()).removeValue();
//		*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		System.out.println("BoomService" + snapshot.getKey());


	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		System.out.println("BoomService" + snapshot.getKey());


	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		System.out.println("BoomService" + snapshot.getKey());


	}

	@Override
	public void onCancelled(DatabaseError error) {
		
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOM_DATA_ITEMS_NOA2;
	}

 




}
