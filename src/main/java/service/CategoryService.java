package service;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Category;
import service.cache.Cache;
import service.cache.CacheServiceKeyBase;
 
public class CategoryService extends  ServiceKeyBase<Category> {

	public CategoryService() {
		super();
	}

	 

	@Override
	public Class<Category> getClassT() {
		// TODO Auto-generated method stub
		return Category.class;
	}

	


//	@Override
//	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
//		// TODO Auto-generated method stub
//		super.onChildAdded(snapshot, previousChildName);
//		Category book = snapshot.getValue(getClassT());
//		String keyEncode = getKeyName(book);
//		FirebaseDatabase.getInstance().getReference().child(getTableNameKey()).child(keyEncode)
//				.setValue(snapshot.getKey());
//	}
	
//	public String getTableNameKey() {
//		// TODO Auto-generated method stub
//		return RefFireBaseBook.BOOK_DATA_CATEGORY_KEY;
//	}


//	private String getKeyName(Category book) {
//		String keyEncode = book.getName();
//		String keyEncode1 = StringUtil.removeAccent(keyEncode);
//		return keyEncode1;
//	}

 

	@Override
	public void setId(String Category_id, Category book) {
		book.setCategory_id(Category_id);
		
	}
	
	
//	@Override
//	public String getTableName() {
//		// TODO Auto-generated method stub
//		return "categories";
//	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_CATEGORIES;
	}

@Override
public String getReferenceKeyName() {
	// TODO Auto-generated method stub
	  return RefFireBaseBook.BOOK_DATA_CATEGORY_KEY;
}

@Override
public String getKeyName(Category category) {
 	String keyEncode = category.getName();
 	String keyEncode1 = StringUtil.removeAccent(keyEncode);
 	return keyEncode1;
 
}

 
}
