package service;

import java.io.Serializable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public abstract class ServiceKeyBase<T extends Serializable>    extends ServiceBase {

	public ServiceKeyBase() {
		super();

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		try {
			T book = snapshot.getValue( getClassT() );
			setId(snapshot.getKey(), book);

		 
			String keyEncode = getKeyName(book);
			if ((keyEncode != null) && (!"".equals(keyEncode))) {
				{
					FirebaseDatabase.getInstance().getReference(getReferenceKeyName()).child(keyEncode)
							.setValue(snapshot.getKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	 public abstract Class<T> getClassT() ;
 

	public abstract String getKeyName(T book);

	public abstract void setId(String key, T book);

	public abstract String getReferenceKeyName();
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

}
