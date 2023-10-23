package service;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;

import model.Category;
import model.Dashboard;
import service.cache.Cache;

public class SearchCategoryService extends SearchServiceBase<Category> {
	private final static int TIME_CHECK = 5000;
	// static private Map<String, Category> mCategory = Cache.mCategory;
	// static private Map<String, String> mCategoryName = Cache.mCategoryName;

	public SearchCategoryService() {
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);
	}

	 

//	public String getInterestTableName() {
//		return "category_interest_category";
//	}

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
		// CategoryInterestChilldService CategoryInterestChilldService = new
		// CategoryInterestChilldService("Category_interest/"+ snapshot.getKey());
		// CategoryInterestChilldService.onStart();
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
	public String getId(Category book) {
		// TODO Auto-generated method stub
		return book.getCategory_id(); 
	}

	@Override
	public HashMap<String, Object> getDashboard(String textSearch, Category book) {
		// TODO Auto-generated method stub
		return Dashboard.getMapsCategoryInterest(textSearch, book);
	}

//	@Override
//	public Map<String, Category> getListObject() {
//		// TODO Auto-generated method stub
//		return Cache.mCategory;
//	}

//	@Override
//	public Map<String, String> getListName() {
//		// TODO Auto-generated method stub
//		return Cache.mCategoryName;
//	}

	@Override
	HashMap<String, Object> getTextNotFound(String textSearch) {
		{
			return Dashboard.getMapsCategoryNotFound(textSearch);
		}
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.SEARCHING_CATEGORY;
	}



	@Override
	public Class<Category> getSeachClassT() {
		// TODO Auto-generated method stub
		return Category.class;
	}



	@Override
	public String getReferenceKeyNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_CATEGORY_KEY;
	}



	@Override
	public String getReferenceNameSeach() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_CATEGORIES;
	}



	

}
