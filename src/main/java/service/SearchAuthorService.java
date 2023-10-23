package service;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.resshare.book.RefFireBaseBook;

import model.Author;
import model.Dashboard;
import service.cache.Cache;

public class SearchAuthorService extends SearchServiceBase<Author> {
	private final static int TIME_CHECK = 5000;
	// static private Map<String, Author> mAuthor = Cache.mAuthor;
	// static private Map<String, String> mAuthorName = Cache.mAuthorName;

	public SearchAuthorService() {
		super();

	}

	public String getTableName() {
		return "searching_author";
	}

	public String getInterestTableName() {
		return "author_interest_author";
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
		// System.err.println("onChildRemoved " + snapshot.getKey());

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		// System.err.println("onChildMoved " + snapshot.getKey());
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// System.err.println("onChildChanged " + snapshot.getKey());
		//
		// AuthorInterestChilldService AuthorInterestChilldService = new
		// AuthorInterestChilldService("Author_interest/"+ snapshot.getKey());
		// AuthorInterestChilldService.onStart();
		//// checkHasNewSearching(snapshot);
		// // TODO Auto-generated method stub

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.err.println("onChildAdded " + snapshot.getKey());
		checkHasNewSearching(snapshot);
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		System.err.println("onCancelled " + error.getMessage());
	}

	@Override
	public String getId(Author book) {
		// TODO Auto-generated method stub
		return book.getAuthor_id(); 
	}

	@Override
	public HashMap<String, Object> getDashboard(String textSearch, Author book) {
		// TODO Auto-generated method stub
		return Dashboard.getMapsAuthorInterest(textSearch, book);
	}

 

	@Override
	HashMap<String, Object> getTextNotFound(String textSearch) {
		{
			return Dashboard.getMapsAuthorNotFound(textSearch);
		}
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.SEARCHING_AUTHOR;
	}

	@Override
	public Class<Author> getSeachClassT() {
		// TODO Auto-generated method stub
		return Author.class;
	}

	@Override
	public String getReferenceKeyNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_AUTHORS_KEY   ;
	}

	@Override
	public String getReferenceNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_AUTHORS;
	}

}
