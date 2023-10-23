package service;

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
import model.BookItemsPurchase;
import model.BookItemsSale;
import model.Books;
import model.TransBook;
import model.Dashboard.DashboardType;
import service.cache.Cache;

public class AcceptSaleBookService extends ServiceBase {

	private static final String STATE_FLOW_CHUA_NHAN = "chau_nhan";
	private static final String STATE_FLOW_CHUA_GIAO = "chua_giao";
	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItems = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS);
	DatabaseReference refItemsSale = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_SALE);
	DatabaseReference refItemsPurchase = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS_PURCHASE);
	DatabaseReference refTransRent = database.getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT);
	DatabaseReference refTransRentHis = database.getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT_HIS);
	DatabaseReference refBookUsers = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_USERS);
	DatabaseReference refDashboard = database.getReference(RefFireBaseBook.DASHBOARD);

	private final static int TIME_CHECK = 5000;
//	private Map<String, Books> mBooks = Cache.mBooks;

	public AcceptSaleBookService() {
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
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {

			// TODO Auto-generated method stub
			AcceptBook acceptBook = snapshot.getValue(AcceptBook.class);
			addMessageAcceptorBook(acceptBook);
			addMessageReciverBook(acceptBook);

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
									BookItemsSale bookItemSale = snapshot.getValue(BookItemsSale.class);
									bookItemSale.setRenter_id(acceptBook.getReceiver_id());
									bookItemSale.setRenter_name(acceptBook.getReceiver_name());
									bookItemSale.setOwner_id(acceptBook.getAcceptor_id());
									bookItemSale.setOwner_name(acceptBook.getAcceptor_name());
									bookItemSale.setState_flow(STATE_FLOW_CHUA_GIAO);
									refItemsSale.child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id())
											.child(acceptBook.getBook_item_id()).setValue(bookItemSale);
									
									addMessageChangeStatusSaleBookDashboard(acceptBook.getAcceptor_id(),acceptBook.getBook_id(),acceptBook.getBook_item_id());
									

									BookItemsPurchase bookItemPurchase = snapshot.getValue(BookItemsPurchase.class);
									bookItemPurchase.setRenter_id(acceptBook.getReceiver_id());
									bookItemPurchase.setRenter_name(acceptBook.getReceiver_name());
									bookItemPurchase.setOwner_id(acceptBook.getAcceptor_id());
									bookItemPurchase.setOwner_name(acceptBook.getAcceptor_name());
									bookItemPurchase.setState_flow(STATE_FLOW_CHUA_NHAN);
									refItemsPurchase.child(acceptBook.getReceiver_id()).child(acceptBook.getBook_id())
											.child(acceptBook.getBook_item_id()).setValue(bookItemPurchase);
									addMessageChangeStatusPurchaseBookDashboard(acceptBook.getReceiver_id(),acceptBook.getBook_id(),acceptBook.getBook_item_id());

									if (bookItem.getQuantity() == 1) {
									 
										refBookUsers.child(acceptBook.getBook_id()).child(acceptBook.getAcceptor_id())
												.child("book_items").child(acceptBook.getBook_item_id()).removeValue();
									}  
									Integer quantity = bookItem.getQuantity() - 1;
										 
										refItems.child(acceptBook.getAcceptor_id()).child(acceptBook.getBook_id())
												.child(acceptBook.getBook_item_id()).child("quantity").setValue(quantity);
									 

									// (bookID+PreferenceUtil.getUid()+ ownerID+item_id)
//									String sTranId = acceptBook.getBook_id() + acceptBook.getReceiver_id()
//											+ acceptBook.getAcceptor_id() + acceptBook.getBook_item_id();
//									refTransRent.child(sTranId).addValueEventListener(new ValueEventListener() {
//										
//										@Override
//										public void onDataChange(DataSnapshot snapshot) {
//										//	refTransRent.child(sTranId).removeValue();
//										//	refTransRentHis.child(sTranId).setValue(snapshot.getValue());
//											
//										}
//										
//										@Override
//										public void onCancelled(DatabaseError error) {
//											// TODO Auto-generated method stub
//											
//										}
//									});

							

								}
							} catch (Exception e) {
								System.out.println(e);
							}
						}

						

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});

			// deIncreateNumberIemtBook(acceptBook);
			//
			// addSaleItemBook(acceptBook);
			// addBorrowItemBook(acceptBook);

			// Book book = mBooks.get(transRent.getBook_id());

			// addMessageAcceptBook( book, transRent.getOwner_id() ) ;
		//	FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
		//	FirebaseDatabase.getInstance().getReference().child(getReferenceNameHis()).child(snapshot.getKey()).setValue(acceptBook);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void addMessageChangeStatusSaleBookDashboard(String acceptor_id, String book_id,
			String book_item_id) {
		HashMap<String, Object> dashboard = getMapsChangeStatusBookDashboard(acceptor_id, book_id,book_item_id,DashboardType.CHANGE_STATUS_SAlE.getType());
		 
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).child( book_id)
				.setValue(dashboard);
	}
	private void addMessageChangeStatusPurchaseBookDashboard(String acceptor_id, String book_id,
			String book_item_id) {
		HashMap<String, Object> dashboard = getMapsChangeStatusBookDashboard(acceptor_id, book_id,book_item_id,DashboardType.CHANGE_STATUS_PURCHASE.getType());
		 
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(acceptor_id).child(book_id)
				.setValue(dashboard);
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
//		Books book = mBooks.get(acceptBook.getBook_id());
//	 

	}

	private void addSaleItemBook(AcceptBook acceptBook) {
		//Books book = mBooks.get(acceptBook.getBook_id());
		 

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
		maps.put("type", DashboardType.ReciverSaleBook.getType());
		// acceptBook
		maps.put("ReciverBook", acceptBook);

		maps.put("text",
				acceptBook.getAcceptor_name() + ":  Đồng ý bán cho bạn quyển sách: " + acceptBook.getBook_name());
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

		maps.put("type", DashboardType.AcceptorSaleBook.getType());
		maps.put("AcceptorBook", acceptBook);

		// Có một quyển sách là &lt;b&gt; &lt;font color="#0f660a"&gt; %s
		// &lt;/font&gt;&lt;/b&gt; đang chờ bạn cho mượn
		maps.put("text", "Bạn đã đồng ý bán cho " + acceptBook.getReceiver_name() + " quyển sách: "
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
		return RefFireBaseBook.BOOK_DATA_ACCEPT_SALE_BOOK;
	}
	public String getReferenceNameHis() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_ACCEPT_SALE_BOOK;
	}
}
