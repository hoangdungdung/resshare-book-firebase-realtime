package service;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;

import model.Books;
import service.cache.Cache;

public class BooksBarcodeService extends ServiceBase {
	private final static int TIME_CHECK = 5000;
//	private Map<String, Books> mBooks = Cache.mBooks;

	public BooksBarcodeService() {
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);

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
		String sISBN = snapshot.getKey();
		String bookId = "BK" + sISBN;
		
		
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOKS).child(bookId).addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(!snapshot.exists())
				{
					String sUserId = snapshot.getValue(String.class);
					CreateBookOnlineByBarcode.getFieldValue(sISBN, sUserId);
					
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
//		if (!mBooks.containsKey(bookId)) {
//			
//
//			String sUserId = snapshot.getValue(String.class);
//			CreateBookOnlineByBarcode.getFieldValue(sISBN, sUserId);
//		//	FirebaseDatabase.getInstance().getReference(getReferenceName()).child( snapshot.getKey()).removeValue();
//			

		

		// book.setBook_id(snapshot.getKey());
		// mBooks.put(snapshot.getKey(), book);
		// String keyEncode = getKeyByName(book);
		// FirebaseDatabase.getInstance().getReference().child("books_key").child(keyEncode)
		// .setValue(book.getBook_id());
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_BARCODE_TMP;
	}

}
