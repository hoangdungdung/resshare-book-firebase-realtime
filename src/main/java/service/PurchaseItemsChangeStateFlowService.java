package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;

import bo.BooksItemsNoaBoImpl2;
import model.BookItemsSale;
import model.StateFlow;
import service.cache.Cache;

public class PurchaseItemsChangeStateFlowService extends ServiceBase {

	public PurchaseItemsChangeStateFlowService() {
		// TODO Auto-generated constructor stub
	}

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItemsSale = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_SALE);
	DatabaseReference refItemsPurchase = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_PURCHASE);
	DatabaseReference refBook = database.getReference(RefFireBaseBook.BOOK_DATA_BOOKS);

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			
		
		BookItemsSale bookItem = snapshot.getValue(BookItemsSale.class);
		StateFlow sStateflow = Cache.mPurchaseStateFlow.get(bookItem.getState_flow());

		Map<String, Object> updatePurchase = new HashMap<>();
		Map<String, Object> updateSale = new HashMap<>();
		if (bookItem.getPurchase_date() == null) {

			Date currentDate = new Date();

			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			// manipulate date

			Date today = Calendar.getInstance().getTime();
			int day = 30;

			Date currentDatePlusOne = c.getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String sToday = dateFormat.format(currentDate);
			String sCurrentDatePlusOne = dateFormat.format(currentDatePlusOne);

			updateSale.put("purchase_date", sToday);
			updateSale.put("return_date", sCurrentDatePlusOne);
			updatePurchase.put("purchase_date", sToday);
			updatePurchase.put("return_date", sCurrentDatePlusOne);

		}

		String sNext_state = sStateflow.getNext_state();
		if (sNext_state != null) {
			if (sNext_state.endsWith("da_nhan")) {
				refBook.child(bookItem.getBook_id()).addChildEventListener(new ChildEventListener() {
					
					@Override
					public void onChildRemoved(DataSnapshot snapshot) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
					if(snapshot.getKey().endsWith("views_count"))
					{
						Long views_count = snapshot.getValue(Long.class);
						
						views_count=views_count+1;
						refBook.child(bookItem.getBook_id()).child("views_count").setValue(views_count)	;
					}
					}
					
					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub
						
					}
				});
				
				BooksItemsNoaBoImpl2 booksItemsNoaBoImpl2=new BooksItemsNoaBoImpl2();
				booksItemsNoaBoImpl2.addItem(bookItem, bookItem.getRenter_id(), bookItem.getBook_id());
				
				
				refItemsPurchase.child(bookItem.getRenter_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id()).removeValue();
			//	FirebaseDatabase.getInstance().getReference().child("dashboard").child(bookItem.getRenter_id()).child( bookItem.getBook_id()).removeValue();
				 
				
				
				
			}
			
		
		
		}
		
		

		String sSale_next_state = sStateflow.getSale_next_state();
		if (sSale_next_state != null) {

			updateSale.put("state_flow", sStateflow.getSale_next_state());
			refItemsSale.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
					.updateChildren(updateSale);
		}
		database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_PURCHASE_CHANGE_STATE_FLOW).child(snapshot.getKey())
				.removeValue();
		
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		return RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_PURCHASE_CHANGE_STATE_FLOW;
	}

}
