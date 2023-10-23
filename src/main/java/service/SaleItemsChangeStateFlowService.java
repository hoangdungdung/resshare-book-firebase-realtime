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

import bo.BooksItemsNoaBoImpl2;
import model.BookItems;
import model.BookItemsSale;
import model.StateFlow;
import service.cache.Cache;

public class SaleItemsChangeStateFlowService extends ServiceBase {

	public SaleItemsChangeStateFlowService() {
		// TODO Auto-generated constructor stub
	}

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItemsSale = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_SALE);
	DatabaseReference refItemsPurchase = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_PURCHASE);
	DatabaseReference refItems = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS);
	DatabaseReference refBookUsers = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_USERS);
	 
	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
		//	-LqdMOY85AjjVP8U_4um
		 
		
		BookItemsSale bookItem = snapshot.getValue(BookItemsSale.class);
		StateFlow sStateflow = Cache.mSaleStateFlow.get(bookItem.getState_flow());
		
		Map<String, Object> updateSALE = new HashMap<>();
		Map<String, Object> updatePurchase = new HashMap<>();
		String next_state = sStateflow.getNext_state();
		if (bookItem.getPurchase_date() == null) {

			Date currentDate = new Date();

			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			// manipulate date

	 
			Date currentDatePlusOne = c.getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String sToday = dateFormat.format(currentDate);
			String sCurrentDatePlusOne = dateFormat.format(currentDatePlusOne);

			updateSALE.put("purchase_date", sToday);
			updateSALE.put("return_date", sCurrentDatePlusOne);
			updatePurchase.put("purchase_date", sToday);
			updatePurchase.put("return_date", sCurrentDatePlusOne);

		}

		if (next_state != null) {
			updateSALE.put("state_flow", next_state);

			refItemsSale.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
					.updateChildren(updateSALE);
		}

		String sPurchase_next_state = sStateflow.getPurchase_next_state();
		if (sPurchase_next_state != null) {
			updatePurchase.put("state_flow", sPurchase_next_state);

			refItemsPurchase.child(bookItem.getRenter_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id())
					.updateChildren(updatePurchase);
		}
	
		if ((next_state != null)&&(next_state.equals("da_nhan_lai_sach"))) {
			
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
							
							refBookUsers.child(bookItem.getBook_id()).child(bookItem.getOwner_id()).child("book_items").child(bookItem.getBook_item_id()).setValue(book_item1);
						 
						 
					 }
					   lQuantity = lQuantity + 1;
					   refItems.child(bookItem.getOwner_id()).child(bookItem.getBook_id()).child(bookItem.getBook_item_id()).child("quantity").setValue(lQuantity);
				}
					
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
		}
				
				 
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
		database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_SALE_CHANGE_STATE_FLOW).child(snapshot.getKey()).removeValue();
		}
		catch (Exception e) {
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
		return RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_SALE_CHANGE_STATE_FLOW;
	}

}
