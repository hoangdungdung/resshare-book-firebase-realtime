package bo;

import java.util.HashMap;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.springboot.ResshareBookApp;

import model.BookItems;
import model.BookItemsNoa;
import model.Books;
import model.Dashboard;
import model.Dashboard.DashboardType;
import rekognition.DetectModerationLabels;
import utils.EncodeUtil;

public class BooksItemsNoaBoImpl2 {

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference refItems = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_ITEMS);
	DatabaseReference refBook = database.getReference(RefFireBaseBook.BOOK_DATA_BOOKS);
	DatabaseReference refItemsNoa = database
			.getReference(ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA));
	DatabaseReference refBooKNameKey = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY);
	DatabaseReference refAuthorsBook1 = database.getReference(RefFireBaseBook.BOOK_DATA_AUTHORS_BOOK);
	DatabaseReference refBookUsers = database.getReference(RefFireBaseBook.BOOK_DATA_BOOK_USERS);
	DatabaseReference refCategoriesBook1 = database.getReference(RefFireBaseBook.BOOK_DATA_CATEGORIES_BOOK);

	public BooksItemsNoaBoImpl2() {
		super();
	}

	public void addItem(BookItems book_item, String userID, String bookId) {

		try {
			if (userID == null)
				return;
			String book_item_id = refItems.child(userID).child(bookId).push().getKey();
			book_item_id = "IT" + book_item_id;
			if ((book_item.getQuantity() == null) || (book_item.getQuantity() == 0))
				book_item.setQuantity(1);

			if (book_item.getSale_price() == null)
				book_item.setSale_price(0d);
			if (book_item.getFree_loan() == null)
				book_item.setFree_loan(true);

			if (book_item.getNumdays_borrow() == null)
				book_item.setNumdays_borrow(30);

			// book_item.setCreate_time(ServerValue.TIMESTAMP);

			HashMap<String, Object> hm = new HashMap<>();
			hm.put("quantity", book_item.getQuantity());
			hm.put("sale_price", book_item.getSale_price());
			hm.put("free_loan", book_item.getFree_loan());
			hm.put("numdays_borrow", book_item.getNumdays_borrow());
			hm.put("create_time", ServerValue.TIMESTAMP);

			refItems.child(userID).child(bookId).child(book_item_id).setValue(hm);

			// BookUsers bookUsers = new BookUsers();
			// bookUsers.setBook_item_id(book_item_id);
			// bookUsers.setSale_price(book_item.getSale_price());
			// bookUsers.setFree_loan(book_item.getFree_loan());
			// User user = Cache.mUser.get(userID);

			// HashMap<String, Object> bookItem = new HashMap<String, Object>();

			BookItems book_item1 = new BookItems();

			book_item1.setFree_loan(book_item.getFree_loan());
			book_item1.setSale_price(book_item.getSale_price());

			// bookItem.put(book_item_id, book_item1);

			// HashMap<String, Object> bookUserItem = new HashMap<>();
			// String user_name = "";
			// String avatar = "";
			// if (user != null) {
			// user_name = user.getUser_name();
			// avatar = user.getAvatar();
			// }
			// bookUserItem.put("user_name", user_name);
			// bookUserItem.put("avatar", avatar);
			// bookUserItem.put("book_items", bookItem);

			// refBookUsers.child(bookId).child(userID).updateChildren(bookUserItem);
			refBookUsers.child(bookId).child(userID).child("book_items").child(book_item_id).setValue(book_item1);
			addMessageBookStateDashboardCreator(bookId, userID);
			refBook.child(bookId).addChildEventListener(new ChildEventListener() {

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
						if (snapshot.getKey().endsWith("views_count")) {
							Long views_count = snapshot.getValue(Long.class);

							views_count = views_count + 1;
							refBook.child(bookId).child("views_count").setValue(views_count);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addBookBookItem(BookItemsNoa bookItemsNoa, String sKey) {

		// String sKey = "";
		//
		// if ((bookItemsNoa.getBook().getISBN() != null) &&
		// !"".equals(bookItemsNoa.getBook().getISBN())) {
		// sKey = bookItemsNoa.getBook().getISBN();
		// } else {
		// sKey = refBook.push().getKey();
		//
		// }
		//
		// sKey = sKey1;
		bookItemsNoa = checkOffsensive(bookItemsNoa);

		Books book = bookItemsNoa.getBook();

		book.setViews_count(0);
		refBook.child(sKey).setValue(book);
		// String user_name = "";
		// String avatar = "";
		// String userID = bookItemsNoa.getBook_item().getOwner_id();
		// User user = Cache.mUser.get(userID);
		// if (user != null) {
		// user_name = user.getUser_name();
		// avatar = user.getAvatar();
		// }
		// Map<String, String> bookUserItem =new HashMap<>();
		//
		//
		// bookUserItem.put("user_name", user_name);
		// bookUserItem.put("avatar", avatar);
		//
		// String bookId=sKey;
		// refBookUsers.child(bookId).child(userID).setValue(bookUserItem);
		addItem(bookItemsNoa.getBook_item(), book.getCreator(), sKey);

	}

	private BookItemsNoa checkOffsensive(BookItemsNoa bookItemsNoa) {
		String urlText = bookItemsNoa.getBook().getImage();
		try {
			boolean Offsensive = DetectModerationLabels.checkOffsensive(urlText);
			if (Offsensive) {
				String imageOffsensive = "https://firebasestorage.googleapis.com/v0/b/resshare-request-cb5e2.appspot.com/o/offensive%2Foffensive.jpg?alt=media&token=3aa5c7c9-447b-4a69-b0a2-ba9212b87bcd";
				bookItemsNoa.getBook().setImage(imageOffsensive);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ResshareBookApp.offsensive

		String sName = bookItemsNoa.getBook().getName();
		String name = checkStringOffsensive(sName);
		bookItemsNoa.getBook().setName(name);
		
		String author_name = bookItemsNoa.getBook().getAuthor_name();
		  setOffsensive(bookItemsNoa, author_name);
		
		String category_name = bookItemsNoa.getBook().getCategory_name() ;
		String category_name1 = checkStringOffsensive(category_name);
		bookItemsNoa.getBook().setCategory_name(category_name1);
		
		String plot_overview = bookItemsNoa.getBook().getPlot_overview();
		String plot_overview1 = checkStringOffsensive(plot_overview);
		bookItemsNoa.getBook().setPlot_overview(plot_overview1);
		
 
		return bookItemsNoa;
	}

	private void setOffsensive(BookItemsNoa bookItemsNoa, String author_name) {
		String author_name1 = checkStringOffsensive(author_name);
		bookItemsNoa.getBook().setAuthor_name(author_name1);
	}

	private String checkStringOffsensive(String sName) {
		if (sName != null) {
			String sNameLowerCase = sName.toLowerCase();
			String[] sArrayOffsensive = ResshareBookApp.offsensive.split(",");
			for (int i = 0; i < sArrayOffsensive.length; i++) {
				String soffsensive = sArrayOffsensive[i];
				if(sNameLowerCase.contains(soffsensive))
				{
					return "Phản cảm" ;
				}
				
			}

		}
		return sName;
	}

	private void addMessageBookStateDashboardCreator(String book_id, String userId) {
		System.out.println("addMessageAuthorDashboard " + book_id);
		HashMap<String, Object> dashboard = Dashboard.getMapsBookCreatedStatus(book_id, 1);

		// DatabaseReference ref =
		// FirebaseDatabase.getInstance().getReference().child("timeAdded");
		// // you can set TimeStamp to this reference like this
		//
		// Map<String, String> timeAddedLong = ServerValue.TIMESTAMP;
		//
		// ref.setValue(timeAddedLong);
		//
		//
		// // get previous time added
		//
		// ValueEventListener listner=getLister(ref);
		//
		// ref.addValueEventListener(listner);

		// ServerValue.TIMESTAMP
		String id = EncodeUtil.base64encode(DashboardType.BOOK_CREATED_STATUS.getType()) + book_id;

		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(id)
				.updateChildren(dashboard);
	}
	// ValueEventListener listener;
	// private ValueEventListener getLister(DatabaseReference ref) {
	// listener = new ValueEventListener() {
	//
	// @Override
	// public void onDataChange(DataSnapshot snapshot) {
	// if(snapshot.getValue()!=null) {
	// long timeadded=snapshot.getValue(Long.class);
	//
	//
	// }
	// ref.removeEventListener(listener);
	//
	// }
	//
	// @Override
	// public void onCancelled(DatabaseError error) {
	// // TODO Auto-generated method stub
	//
	// }
	// };
	// return listener;
	// }

}
