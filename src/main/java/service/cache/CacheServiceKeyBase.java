package service.cache;

import java.io.Serializable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public abstract class CacheServiceKeyBase<T extends Serializable> extends CacheServiceBase<T> {

	public CacheServiceKeyBase() {
		super();

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		try {
			T book = snapshot.getValue(getClassT());
			setId(snapshot.getKey(), book);

			super.onChildAdded(snapshot, previousChildName);
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

	public abstract String getKeyName(T book);

	public abstract void setId(String key, T book);

	public abstract String getReferenceKeyName();

}
