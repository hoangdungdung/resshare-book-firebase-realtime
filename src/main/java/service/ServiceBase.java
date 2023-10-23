package service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;

public abstract class ServiceBase implements ChildEventListener {

	protected FirebaseDatabase firebaseDb;

	public void onStart() {
		firebaseDb=FirebaseDatabase.getInstance();
		firebaseDb.getReference(getReferenceName()).addChildEventListener(this);

	}

	public void onStop() {
		firebaseDb.getReference(getReferenceName()).removeEventListener(this);
	}

	public abstract String getReferenceName();

}
