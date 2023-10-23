package service.cache;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Books;
import model.Category;
import service.ServiceBase;

public abstract class CacheServiceBase<T extends Serializable> extends ServiceBase {
	private final static int TIME_CHECK = 5000;
	private Map<String, T> mBooks ;
	public CacheServiceBase() {
		super();
	}
	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}
	
	public abstract Map<String, T>    getCacheObject();
 

	 
	 


	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		mBooks=	getCacheObject();
		// TODO Auto-generated method stub
		if (mBooks.containsKey(snapshot.getKey()))
			mBooks.remove(snapshot.getKey());
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			mBooks=	getCacheObject();
	 		T book =  snapshot.getValue(getClassT());
			 
		
			mBooks.put(snapshot.getKey(), book);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	
		
		 
		 
		
	}

 
	//public abstract String getKeyName(T obj) ;

	 public abstract Class<T> getClassT() ;

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	
	
	 
	 

}
