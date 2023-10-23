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
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.DataUtil;

import model.BookItemsLoan;
import model.StateFlow;
import service.cache.Cache;

public class BorrowItemsChangeStateFlowService extends ServiceBase {

	public BorrowItemsChangeStateFlowService() {
		// TODO Auto-generated constructor stub
	}

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItemsLoan = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_LOAN);
	DatabaseReference refItemsBorrow = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_BORROW);
	DatabaseReference refBook = database.getReference(RefFireBaseBook.BOOK_DATA_BOOKS);

	@Override
	public void onChildAdded(DataSnapshot snapshotTemp, String previousChildName) {
		
		String book_items_id = snapshotTemp.child("data/book_items_id").getValue(String.class);
		String book_id = snapshotTemp.child("data/book_id").getValue(String.class);
		
		String user_id = snapshotTemp.child("user_id").getValue(String.class);
		
		
		refItemsBorrow.child(user_id+"/"+book_id+"/"+book_items_id).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				BookItemsLoan bookItem = snapshot.getValue(BookItemsLoan.class);
				bookItem.setBook_id(book_id);
				bookItem.setBook_item_id(book_items_id);
				StateFlow sStateflow = com.resshare.framework.core.service.Cache.configuration.child("system_setting/book_state_flow/book_items_borrow/"+ bookItem.getState_flow()).getValue(StateFlow.class);
				Map<String, Object> updateLoan =DataUtil.ConvertDataSnapshotToMap(snapshot);
				Map<String, Object> updateBorrow = DataUtil.ConvertDataSnapshotToMap(snapshot);
				
				
			//	StateFlow sStateflow = Cache.mBorrowStateFlow.get(bookItem.getState_flow());
		//
//				Map<String, Object> updateBorrow = new HashMap<>();
//				Map<String, Object> updateLoan = new HashMap<>();
				if (bookItem.getBorrowed_date() == null) {

					Date currentDate = new Date();

					// convert date to calendar
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);

					// manipulate date

					Date today = Calendar.getInstance().getTime();
					int day = 30;
					if (bookItem.getNumdays_borrow() != null) {
						day = bookItem.getNumdays_borrow();
						c.add(Calendar.DATE, day);

					} else {

						c.add(Calendar.MONTH, 1);
					}
					Date currentDatePlusOne = c.getTime();
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String sToday = dateFormat.format(currentDate);
					String sCurrentDatePlusOne = dateFormat.format(currentDatePlusOne);

					updateLoan.put("borrowed_date", sToday);
					updateLoan.put("return_date", sCurrentDatePlusOne);
					updateBorrow.put("borrowed_date", sToday);
					updateBorrow.put("return_date", sCurrentDatePlusOne);

				}

				String sNext_state = sStateflow.getNext_state();
				if (sNext_state != null) {
					if (sNext_state.endsWith("dang_doc")) {
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
						
					}

					updateBorrow.put("state_flow", sStateflow.getNext_state());
					refItemsBorrow.child(bookItem.getRenter_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
							.updateChildren(updateBorrow);
				}

				String sLoan_next_state = sStateflow.getLoan_next_state();
				if (sLoan_next_state != null) {

					updateLoan.put("state_flow", sStateflow.getLoan_next_state());
					refItemsLoan.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
							.updateChildren(updateLoan);
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		}); 
		
	
		database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_BORROW_CHANGE_STATE_FLOW).child(snapshotTemp.getKey())
				.removeValue();

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
		return RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_BORROW_CHANGE_STATE_FLOW;
	}

}
