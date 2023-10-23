package service.cache;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

//import org.apache.xalan.xsltc.runtime.Hashtable;

import model.Author;
import model.BookSites;
import model.Books;
import model.Category;
import model.Element;
import model.StateFlow;
import model.User;

public class Cache {
//  	public static final Map<String, User> mFirebaseUser = new HashMap<>();
//  	static public Map<String, Books> mBooks = new HashMap<>();
//  	static public Map<String, Books> mBooksUser = new HashMap<>();
//  	static public Map<String, String> mBookName = new HashMap<>();
// 	
// 	
//  	public static Map<String, User> mUser = new HashMap<>();
//  	public static Map<String, String> mUserName = new HashMap<>();
 //	 public static Map<String, String> mUserChannelCache = new HashMap<>();
//  	static public Map<String, String> mBookBarcode = new HashMap<>();
//  	public static Map<String, Author> mAuthor = new HashMap<>();
//  	public static Map<String, String> mAuthorName = new HashMap<>();
//  	public static Map<String, Category> mCategory = new HashMap<>();
//  	public static Map<String, String> mCategoryName = new HashMap<>();
 	
	
	
	static public Hashtable  mBookNameLock = new Hashtable ();
	static public Map<String, StateFlow> mLoanStateFlow = new HashMap<>();
	static public Map<String, StateFlow> mBorrowStateFlow = new HashMap<>();
	public static Map<String, StateFlow> mSaleStateFlow = new HashMap<>();
	public static Map<String, StateFlow> mPurchaseStateFlow = new HashMap<>();

	static public Map<String, Object> mBookSites = new HashMap<>();

}
