package service;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Books;
import model.Dashboard.DashboardType;
import service.cache.Cache;
import model.TransBook;
import utils.EncodeUtil;

public class TranRentService extends ServiceBase {
	private final static int TIME_CHECK = 5000;
//	private Map<String, Books> mBooks = Cache.mBooks;

	public TranRentService() {
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
		// TODO Auto-generated method stub
		TransBook transRent = snapshot.getValue(TransBook.class);
		
		FirebaseDatabase.getInstance().getReference().child(RefFireBaseBook.BOOK_DATA_BOOKS).child(transRent.getBook_id()).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(snapshot.exists())
				{
					Books book =snapshot.getValue(Books.class);
					
					if(	transRent.getFree_loan())

						addMessageRequestRentDashboard(book, transRent,snapshot.getKey());
					else
						addMessageRequestSaleDashboard(book, transRent,snapshot.getKey());
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		Books book = mBooks.get(transRent.getBook_id());
//		// transRent.getRenter_id();
//	if(	transRent.getFree_loan())
//
//		addMessageRequestRentDashboard(book, transRent,snapshot.getKey());
//	else
//		addMessageRequestSaleDashboard(book, transRent,snapshot.getKey());
	
	
	}

	private void addMessageRequestRentDashboard(Books book, TransBook transRent,String tranId) {

		System.out.println("addMessageAuthorDashboard " + book.getBook_id());
		transRent.setBook_name(book.getName());
		
		
		
		HashMap<String, Object> dashboard = getMapsRequestRent(book, transRent);
		 
	String key=	FirebaseDatabase.getInstance().getReference().child("dashboard").child(transRent.getOwner_id()).push().getKey();
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(transRent.getOwner_id()).child(key).setValue(dashboard);

	}
	private void addMessageRequestSaleDashboard(Books book, TransBook transRent,String tranId) {

		System.out.println("addMessageRequestSaleDashboard " + book.getBook_id());
		
		
		
		
		transRent.setBook_name(book.getName());
		
		
		
		HashMap<String, Object> dashboard = getMapsRequestSale(book, transRent);
		String key=	FirebaseDatabase.getInstance().getReference().child("dashboard").child(transRent.getOwner_id()).push().getKey();
		 
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(transRent.getOwner_id()).child(key)
				.setValue(dashboard);

	}

	private HashMap<String, Object> getMapsRequestRent(Books book, TransBook transRent) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book.getBook_id());
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.REQUEST_RENT.getType());
		maps.put("user_id", transRent.getRenter_id());
		maps.put("TransBook", transRent);
	//	maps.put("book_info", Book.getMapsBooks(book));
		return maps;
	}
	private HashMap<String, Object> getMapsRequestSale(Books book, TransBook transRent) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book.getBook_id());
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.REQUEST_SAlE.getType());
		maps.put("user_id", transRent.getRenter_id());
		maps.put("TransBook", transRent);
	//	maps.put("book_info", Book.getMapsBooks(book));
		return maps;
	}


	public static HashMap<String, Object> getMapsAccept(Books book, String user_id) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book.getBook_id());
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.REQUEST_RENT.getType());
		maps.put("user_id", user_id);
		maps.put("book_info", book.getBook_id());
		return maps;
	}

	private void addMessageAcceptDashboard(Books book, String authoriser) {
		System.out.println("addMessageAuthorDashboard " + book.getBook_id());
		HashMap<String, Object> dashboard = getMapsAccept(book, authoriser);
		String id = EncodeUtil.base64encode(DashboardType.REQUEST_RENT.getType()) + book.getBook_id();
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(authoriser).child(id)
				.setValue(dashboard);
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stubs

	}

//	@Override
//	public String getTableName() {
//		// TODO Auto-generated method stub
//		return "trans_rent";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_TRANS_RENT;
	}

}
