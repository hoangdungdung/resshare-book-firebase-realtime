package service;

import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Author;
import service.cache.Cache;
import service.cache.CacheServiceKeyBase;
 
public class AuthorService extends ServiceKeyBase<Author> {

	public AuthorService() {
		super();
	}

	 

	@Override
	public Class<Author> getClassT() {
		// TODO Auto-generated method stub
		return Author.class;
	}

	
//	@Override
//	public String getTableName() {
//		// TODO Auto-generated method stub
//		return "authors";
//	}

	@Override
	
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			
	
		// TODO Auto-generated method stub
		super.onChildAdded(snapshot, previousChildName);
		
//		String keyEncode = "gggggggg";
//		if ((keyEncode != null) && (!"".equals(keyEncode)))
//			{
//			
//				FirebaseDatabase.getInstance().getReference("book_data1").child("authors4444").child(keyEncode)
//					.setValue("lll");
//				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
//				
//				.setValue("arBook_id");
//			}
		Author book = snapshot.getValue(getClassT());
		String keyEncode = getKeyName(book);
		 
		
		String keyNickEncode = getKeyNickName(book);
		
		if ((keyNickEncode!=null) && (!"".equals(keyNickEncode)) && (!keyEncode.equals(keyNickEncode)))
		{
			FirebaseDatabase.getInstance().getReference(getReferenceKeyName()).child(keyNickEncode)
			.setValue(snapshot.getKey());
	
			
		}
		 
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
//	public String getTableNameKey() {
//		// TODO Auto-generated method stub
//		return "author_key";
//	}
//
//
//	private String getKeyName(Author book) {
//		String keyEncode = book.getName();
//		String keyEncode1 = StringUtil.removeAccent(keyEncode);
//		return keyEncode1;
//	}

	private String getKeyNickName(Author book) {
		String keyEncode = book.getNick_name();
		String keyEncode1 = StringUtil.removeAccent(keyEncode);
		return keyEncode1;
	}

	@Override
	public void setId(String author_id, Author book) {
		book.setAuthor_id(author_id);
		
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_AUTHORS;
	}

	@Override
	public String getReferenceKeyName() {
		return RefFireBaseBook.BOOK_DATA_AUTHORS_KEY;
	}

	@Override
	public String getKeyName(Author obj) {
		String keyEncode = obj.getName();
		String keyEncode1 = StringUtil.removeAccent(keyEncode);
		return keyEncode1;
	}

 
}
