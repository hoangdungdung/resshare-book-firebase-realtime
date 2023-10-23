package com.resshare.book.transaction;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.AcceptBook;
import model.BookItems;
import model.BookItemsBorrow;
import model.BookItemsLoan;
import model.Books;
import model.TransBook;
import model.Dashboard.DashboardType;
import service.ServiceBase;
import service.cache.Cache;

public class AcceptRentBookService extends ServiceBase {

	private static final String STATE_FLOW_CHUA_NHAN = "chau_nhan";
	private static final String STATE_FLOW_CHUA_GIAO = "chua_giao";
	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItems = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS);
	DatabaseReference refItemsLoan = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_LOAN);
	DatabaseReference refItemsBorrow = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_BORROW);
	DatabaseReference refTransRent = database.getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT);
	DatabaseReference refTransRentHis = database.getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT_HIS);
	DatabaseReference refBookUsers = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_USERS);
	DatabaseReference refDashboard = database.getReference(RefFireBaseBook.DASHBOARD);

	private final static int TIME_CHECK = 5000;
	//private Map<String, Books> mBooks = Cache.mBooks;

	public AcceptRentBookService() {
		super();

	}



	public static String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public static String getKeyByName(Books book) {
		String keyEncode = book.getName();
		String keyEncode1 = StringUtil.removeAccent(keyEncode);

		return keyEncode1;
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
	}

	
	@Override
	public void onChildAdded(DataSnapshot snapshotRoot, String previousChildName) {
		try {
			DataSnapshot snapshot = snapshotRoot.child("data");
			// TODO Auto-generated method stub
			AcceptBook acceptBook = snapshot.getValue(AcceptBook.class);
		
			 String tranId= acceptBook.getBook_id()+acceptBook.getReceiver_id()+acceptBook.getAcceptor_id()+acceptBook.getBook_item_id();
			 firebaseDb.getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT).child(tranId).setValue(null);

			refItems.child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id())
					.addChildEventListener(new ChildEventListener() {

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
							try {
								if (snapshot.getKey().equals(acceptBook.getBook_item_id())) {

									BookItems bookItem = snapshot.getValue(BookItems.class);
									
									if (bookItem.getQuantity() <1)
									{
										addMessageBookItemNotExist(acceptBook);
										
										return;
									}
									
								//	addMessageAcceptorBook(acceptBook);
								//	addMessageReciverBook(acceptBook);
									
									
									BookItemsLoan bookItemLoan = snapshot.getValue(BookItemsLoan.class);
									bookItemLoan.setRenter_id(acceptBook.getReceiver_id());
									bookItemLoan.setRenter_name(acceptBook.getReceiver_name());
									bookItemLoan.setOwner_id(acceptBook.getAcceptor_id());
									bookItemLoan.setOwner_name(acceptBook.getAcceptor_name());
									bookItemLoan.setState_flow(STATE_FLOW_CHUA_GIAO);
									refItemsLoan.child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id())
											.child(acceptBook.getBook_item_id()).setValue(bookItemLoan);
									
									 
								//	addMessageChangeStatusLoanBookDashboard(acceptBook.getAcceptor_id(),acceptBook.getBook_id(),acceptBook.getBook_item_id());
									BookItemsBorrow bookItemBorrow = snapshot.getValue(BookItemsBorrow.class);
									bookItemBorrow.setRenter_id(acceptBook.getReceiver_id());
									bookItemBorrow.setRenter_name(acceptBook.getReceiver_name());
									bookItemBorrow.setOwner_id(acceptBook.getAcceptor_id());
									bookItemBorrow.setOwner_name(acceptBook.getAcceptor_name());
									bookItemBorrow.setState_flow(STATE_FLOW_CHUA_NHAN);
									refItemsBorrow.child(acceptBook.getReceiver_id()).child(acceptBook.getBook_id())
											.child(acceptBook.getBook_item_id()).setValue(bookItemBorrow);
									
								//	addMessageChangeStatusBorrowBookDashboard(acceptBook.getReceiver_id(),acceptBook.getBook_id(),acceptBook.getBook_item_id());
									 
									

									if (bookItem.getQuantity() == 1) {
									 
										refBookUsers.child(acceptBook.getBook_id()).child(acceptBook.getAcceptor_id())
												.child("book_items").child(acceptBook.getBook_item_id()).removeValue();
									}  
									Integer quantity = bookItem.getQuantity() - 1;
										 
										refItems.child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id())
												.child(acceptBook.getBook_item_id()).child("quantity").setValue(quantity);
									 



								}
							} catch (Exception e) {
								e.printStackTrace();
								//System.out.println(e.);
							}
						}

						private void addMessageChangeStatusBorrowBookDashboard(String acceptor_id, String book_id,
								String book_item_id) {

							HashMap<String, Object> dashboard = getMapsChangeStatusBookDashboard(acceptor_id, book_id,book_item_id,DashboardType.CHANGE_STATUS_BORROW.getType());
							 
//							FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).child( book_id)
//									.setValue(dashboard);
							FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).push()
							.setValue(dashboard);
						
							
						}

		 

						private void addMessageChangeStatusLoanBookDashboard(String acceptor_id, String book_id,
								String book_item_id) {

							HashMap<String, Object> dashboard = getMapsChangeStatusBookDashboard(acceptor_id, book_id,book_item_id,DashboardType.CHANGE_STATUS_LOAN.getType());
							 
//							FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).child( book_id)
//									.setValue(dashboard);
							FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).push().setValue(dashboard);
						
							
						}

					 

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});

			// deIncreateNumberIemtBook(acceptBook);
			//
			// addLoanItemBook(acceptBook);
			// addBorrowItemBook(acceptBook);

			// Book book = mBooks.get(transRent.getBook_id());

			// addMessageAcceptBook( book, transRent.getOwner_id() ) ;
			
			//FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
		//	FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
		//	FirebaseDatabase.getInstance().getReference().child(getReferenceNameHis()).child(snapshot.getKey()).setValue(acceptBook);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	


	protected void addMessageBookItemNotExist(AcceptBook acceptBook) {
		 
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", acceptBook.getBook_id());
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());
		maps.put("user_id",acceptBook.getReceiver_id());
		maps.put("book_item_id", acceptBook.getBook_item_id());
		maps.put("text", acceptBook.getAcceptor_name()  +" đã hết sách "+acceptBook.getBook_name() + ", bạn quay lại lần sau");
		String key=FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptBook.getAcceptor_id()).push().getKey();
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptBook.getReceiver_id())
				.child(key).setValue(maps);
		
		HashMap<String, Object> maps2 = new HashMap<>();
		maps2.put("book_id", acceptBook.getBook_id());
		maps2.put("create_time", ServerValue.TIMESTAMP);
		maps2.put("type", DashboardType.DEFAULT.getType());
		maps2.put("user_id",acceptBook.getAcceptor_id());
		maps2.put("book_item_id", acceptBook.getBook_item_id());
		
		maps2.put("text", "Bạn đã hết số lượng sách "+acceptBook.getBook_name());
		
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptBook.getAcceptor_id())
				.child(key).setValue(maps2);
		
	}



	private HashMap<String, Object> getMapsChangeStatusBookDashboard(String acceptor_id, String book_id,
			String book_item_id,String type) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book_id);
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", type);
		maps.put("user_id",acceptor_id);
		maps.put("book_item_id", book_item_id);
	//	maps.put("book_info", Book.getMapsBooks(book));
		return maps;
		 
	}


	private void addBorrowItemBook(AcceptBook acceptBook) {
	//	Books book = mBooks.get(acceptBook.getBook_id());
	
	}

	private void addLoanItemBook(AcceptBook acceptBook) {
	//	Books book = mBooks.get(acceptBook.getBook_id());
 

	}

	private void deIncreateNumberIemtBook(AcceptBook acceptBook) {

		// refItems.child(acceptBook.getAcceptor_id())
		// .child(acceptBook.getBook_id()).addListenerForSingleValueEvent(new
		// ValueEventListener() {
		//
		// @Override
		// public void onDataChange(DataSnapshot snapshot) {
		// Books book = snapshot.getValue(Books.class);
		// if (book.getCount() > 1) {
		// book.setCount(book.getCount() - 1);
		// refItems
		// .child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id()).updateChildren(Books.getMapsBooks(book));
		//
		// }
		//
		// else {
		// refItems
		// .child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id()).removeValue();
		// refBookUsers
		// .child(acceptBook.getBook_id()).child(acceptBook.getAcceptor_id()).removeValue();
		// }
		//
		// }
		//
		// @Override
		// public void onCancelled(DatabaseError error) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

	}

	public static HashMap<String, Object> getMapsBookReciver(AcceptBook acceptBook) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());
		maps.put("type", DashboardType.ReciverRentBook.getType());
		// acceptBook
		maps.put("ReciverBook", acceptBook);

		maps.put("text",
				acceptBook.getAcceptor_name() + ":  Đồng ý cho bạn mượn quyển sách: " + acceptBook.getBook_name());
		return maps;
	}

	private void addMessageReciverBook(AcceptBook acceptBook) {

		HashMap<String, Object> dashboard = getMapsBookReciver(acceptBook);

		FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptBook.getReceiver_id())
				.child(acceptBook.getDashboar_id()).setValue(dashboard);
	}

	public static HashMap<String, Object> getMapsBookAcceptor(AcceptBook acceptBook) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);

		maps.put("type", DashboardType.AcceptorRentBook.getType());
		maps.put("AcceptorBook", acceptBook);

		// Có một quyển sách là &lt;b&gt; &lt;font color="#0f660a"&gt; %s
		// &lt;/font&gt;&lt;/b&gt; đang chờ bạn cho mượn
		maps.put("text", "Bạn đã đồng ý cho " + acceptBook.getReceiver_name() + " mượn quyển sách: "
				+ acceptBook.getBook_name());
		return maps;
	}

	private void addMessageAcceptorBook(AcceptBook acceptBook) {

		HashMap<String, Object> dashboard = getMapsBookAcceptor(acceptBook);

		refDashboard.child(acceptBook.getAcceptor_id()).child(acceptBook.getDashboar_id()).setValue(dashboard);
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stubs

	}

	// @Override
	// public String getTableName() {
	// // TODO Auto-generated method stub
	// return "accept_book";
	// }

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_ACCEPT_RENT_BOOK;
	}
	 
	public String getReferenceNameHis() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_ACCEPT_RENT_BOOK_HIS;
	}

}
