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

import bo.BooksItemsNoaBoImpl2;
import model.BookItems;
import model.BookItemsLoan;
import model.StateFlow;
import service.cache.Cache;

public class LoanItemsChangeStateFlowService extends ServiceBase {

	public LoanItemsChangeStateFlowService() {
		// TODO Auto-generated constructor stub
	}

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItemsLoan = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_LOAN);
	DatabaseReference refItemsBorrow = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_BORROW);
	DatabaseReference refItems = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS);
	DatabaseReference refBookUsers = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_USERS);
	 
	@Override
	public void onChildAdded(DataSnapshot snapshotTemp, String previousChildName) {
		try {
			
			String book_items_id = snapshotTemp.child("data/book_items_id").getValue(String.class);
			String book_id = snapshotTemp.child("data/book_id").getValue(String.class);
			
			String user_id = snapshotTemp.child("user_id").getValue(String.class);
			
			
			refItemsLoan.child(user_id+"/"+book_id+"/"+book_items_id).addListenerForSingleValueEvent(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot snapshot) {
					BookItemsLoan bookItem = snapshot.getValue(BookItemsLoan.class);
					bookItem.setBook_id(book_id);
					bookItem.setBook_item_id(book_items_id);
					StateFlow sStateflow = com.resshare.framework.core.service.Cache.configuration.child("system_setting/book_state_flow/book_items_loan/"+ bookItem.getState_flow()).getValue(StateFlow.class);
					Map<String, Object> updateLoan =DataUtil.ConvertDataSnapshotToMap(snapshot);
					Map<String, Object> updateBorrow = DataUtil.ConvertDataSnapshotToMap(snapshot);
					String next_state = sStateflow.getNext_state();
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

					if (next_state != null) {
						updateLoan.put("state_flow", next_state);

						refItemsLoan.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
								.updateChildren(updateLoan);
					}

					String borrow_next_state = sStateflow.getBorrow_next_state();
					if (borrow_next_state != null) {
						updateBorrow.put("state_flow", borrow_next_state);

						refItemsBorrow.child(bookItem.getRenter_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
								.updateChildren(updateBorrow);
					}
				
					if ((next_state!=null) && next_state.equals("da_nhan_lai_sach")) {
						
						refItems.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id()).addChildEventListener(new ChildEventListener() {
							
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
							if(snapshot.getKey().equals("quantity"))
							{
								Long  lQuantity=  ( snapshot.getValue(Long.class)); 
								 if(lQuantity<=0)
								 {
									 BookItems book_item1= new BookItems();
										
										book_item1.setFree_loan(bookItem.getFree_loan());
										book_item1.setSale_price(bookItem.getSale_price());
										book_item1.setQuantity(0);
										
										refBookUsers.child(bookItem.getBook_id()).child(bookItem.getOwner_id()).child("book_items").child(bookItem.getBook_item_id()).setValue(book_item1);
									 
									 
								 }
								   lQuantity = lQuantity + 1;
								   refItems.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id()).child("quantity").setValue(lQuantity);
								   refItemsLoan.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id()).setValue(null);
							}
								
							}
							
							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub
								
							}
						});
						
						
					}
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			
		

				
				 
//					.addValueEventListener(new ValueEventListener() {
//
//						@Override
//						public void onDataChange(DataSnapshot snapshot) {
//							boolean itemExist = false;
//							for (DataSnapshot iterable  : snapshot.getChildren()) {
//
//								if (iterable.getKey().equals(bookItem.getBook_item_id())) {
//									itemExist = true;
//								 
//									BookItems item = iterable.getValue(BookItems.class); 
//									// update Item
//									Map<String, Object> updateItem = new HashMap<>();
//									 updateItem.put("quantity",item.getQuantity()+1 );
//
//								}
//							}
//							// TODO Auto-generated method stub
//							if (!itemExist) {
//								// add item
//								BooksItemsNoaBoImpl2 booksItemsNoaBo = new BooksItemsNoaBoImpl2();
//								bookItem.setQuantity(1L);
//								booksItemsNoaBo.addItem(bookItem, bookItem.getOwner_id(), bookItem.getBook_id());
//
//							}
//
//						}
//
//						@Override
//						public void onCancelled(DatabaseError error) {
//							// TODO Auto-generated method stub
//
//						}
//					});
//
//		}
		database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_LOAN_CHANGE_STATE_FLOW).child(snapshotTemp.getKey())
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
		return RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_LOAN_CHANGE_STATE_FLOW;
	}

}
