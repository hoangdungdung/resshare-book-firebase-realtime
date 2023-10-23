package com.resshare.goibinhoxy.cobinhoxy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class SupplierLocationOnStopListener extends ListenerBase {
	//SupplierLocationChangeListener
	//SupplierLocationOnStopListener

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				// String user_id = snapshot1.child("user_id").getValue(String.class);

				{

					// Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

					String order_key = snapshot1.child("data/order_key").getValue(String.class);

					// HashMap script_param = new HashMap<>();

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference
									.getDataPathReference(FirebaseRefOxy.order + "/" + order_key + "/supplier_info/current/event"))
							.setValue(null);

					;

				}

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
		}
	}



	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.supplier_location_on_stop);
	}

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
