package service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;

import model.BookItems;
import model.BookItemsNoa;
import model.Books;

public class BookNoa2Service extends ServiceBase {

	public BookNoa2Service() {

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {

			BookItemsNoa bookItemsNoa = snapshot.getValue(BookItemsNoa.class);
			BookItems bookItems = snapshot.getValue(BookItems.class);
			bookItemsNoa.setBook_item(bookItems);
			Books book = snapshot.getValue(Books.class);
			bookItemsNoa.setBook(book);
			firebaseDb.getReference(ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA))
					.push().setValue(bookItemsNoa);
			firebaseDb.getReference(getReferenceName()).child(snapshot.getKey()).removeValue();

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
		return RefFireBaseBook.BOOK_DATA_ITEMS_NOA2;
	}

}
