package com.resshare.book.addbook;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Books;
import model.UtilBook;
import service.ServiceBase;
import service.cache.Cache;

public class BookService extends ServiceBase {
	public static final String keySplit = "#@#";
	private final static int TIME_CHECK = 5000;
	//private Map<String, Books> mBooks = Cache.mBooks;

	public BookService() {
		super();

	}
	@Override
	public void onStart() {

		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).setValue(null);
		super.onStart();

		
	}
	public static String removeAccent(String s) {
		  
		  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		  return pattern.matcher(temp).replaceAll("");
		 }
	public static String getKeyByName(Books book) {
		if(book==null)
			return "book_null";
		
		String keyEncode = book.getName();
		if(keyEncode==null)
			return "book_name_null";
		keyEncode=keyEncode.trim();
		String keyEncode7 = keyEncode.replaceAll("\\.", "");
		
		String keyEncode1 = StringUtil.removeAccent(keyEncode7);

		
		return keyEncode1;
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
//		if (mBooks.containsKey(snapshot.getKey()))
//			mBooks.remove(snapshot.getKey());
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
//		if (mBooks.containsKey(snapshot.getKey())) {
//			Books book = snapshot.getValue(Books.class);
//			book.setBook_id(snapshot.getKey());
//			mBooks.replace(snapshot.getKey(), book);
//		}
	}
	


	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			
	
		// TODO Auto-generated method stub
		Books book = snapshot.getValue(Books.class);
		
		book.setBook_id(snapshot.getKey());
	//	mBooks.put(snapshot.getKey(), book);
//		if(book.getISBN()!=null)
//		{
//			if(!"".equals(book.getISBN()))
//			{
//				if(!Cache.mBookBarcode.containsKey(book.getISBN()))
//				{
//					Cache.mBookBarcode.put(book.getISBN(), snapshot.getKey());
//				}
//				
//			}
//		}
		String keyEncode = getKeyByName(book);
		
		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
		
		.child(String.valueOf( book.getCover_price())).setValue( snapshot.getKey()    );
 
		
		String textSearchEncode1 = keyEncode+String.valueOf(book.getCover_price());
		if(Cache.mBookNameLock.containsKey(textSearchEncode1))
		Cache.mBookNameLock.remove(textSearchEncode1);
		
		
		
//		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode).addListenerForSingleValueEvent(new ValueEventListener() {
//			
//			@Override
//			public void onDataChange(DataSnapshot snapshot) {
//				if(snapshot.exists())
//				{
//
//					String arBook_id = snapshot.getValue(String.class);
//					if(!"".equals(book.getISBN()))
//					{
//						arBook_id=	book.getBook_id()+keySplit+arBook_id;
//						
//					}
//					else
//					{
//						arBook_id=	arBook_id+keySplit+book.getBook_id();
//						
//					}
//					FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
//					
//					.setValue(arBook_id);
////					 Cache.mBookName.remove(keyEncode);
////					 Cache.mBookName.put(keyEncode, arBook_id);
//					
//					
//				
//				}
//				else
//				{
//					FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
//					
//					.setValue(book.getBook_id());
//					// Cache.mBookName.put(keyEncode, book.getBook_id());
//					
//					
//					
//				}
//				
//			}
//			
//			@Override
//			public void onCancelled(DatabaseError error) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
		
//		if (Cache.mBookName.containsKey(keyEncode)) {
//			String arBook_id = Cache.mBookName.get(keyEncode);
//			if(!"".equals(book.getISBN()))
//			{
//				arBook_id=	book.getBook_id()+keySplit+arBook_id;
//				
//			}
//			else
//			{
//				arBook_id=	arBook_id+keySplit+book.getBook_id();
//				
//			}
//			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
//			
//			.setValue(arBook_id);
////			 Cache.mBookName.remove(keyEncode);
////			 Cache.mBookName.put(keyEncode, arBook_id);
//			
//			
//		}else
//		{
//		FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(keyEncode)
//		
//		.setValue(book.getBook_id());
//		// Cache.mBookName.put(keyEncode, book.getBook_id());
//		
//		}
		
		FirebaseDatabase database=	FirebaseDatabase.getInstance();
		
	 
		DatabaseReference refAuthorsBook  = database.getReference(RefFireBaseBook.BOOK_DATA_AUTHORS_BOOK);
		
		DatabaseReference refCategoriesBook = database.getReference(RefFireBaseBook.BOOK_DATA_CATEGORIES_BOOK);
		
		
		 
		//	refItemsNoa.child(book.getBook_id()).removeValue();
		//	HashMap<String, Object> mapsBooks = Books.getMapsBooks(book);
		//	refBook.child(book.getBook_id()).updateChildren(mapsBooks);
			
			HashMap<String, Object>  mapsShortBooks = UtilBook.getMapsBooksName(book);
			if((book.getAuthor_id()!=null) && (!"".equals( book.getAuthor_id())))
			refAuthorsBook.child(book.getAuthor_id()).child(book.getBook_id())
					.updateChildren(mapsShortBooks);
			if((book.getCategory_id()!=null) && (!"".equals( book.getCategory_id())))
			refCategoriesBook.child(book.getCategory_id()).child(book.getBook_id())
					.updateChildren(mapsShortBooks);
//			refBooKNameKey.child(keyEncode).setValue(book.getBook_id());
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
			
		 
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.BOOK_DATA_BOOKS;
	}

 

}
