package com.resshare.goibinhoxy.cobinhoxy;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.service.map.driver.LocationUtil;

public class JoinSupplierOxyListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				String location = snapshot1.child("data").child("location").getValue(String.class);
				if (location != null) {
					String user_id = snapshot1.child("user_id").getValue(String.class);
					String phone_number = snapshot1.child("data/supplier_phone_number").getValue(String.class);
					String supplier_user_name = snapshot1.child("data/supplier_user_name").getValue(String.class);
					// insert data
					String keyCell = LocationUtil.getCellMapKeyByLatLong2km(location);
					String group_key = keyCell + "/" + snapshot1.getKey();
					FirebaseDatabase.getInstance()
							.getReference(
									ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier_location)
											+ "/" + keyCell + "/" + phone_number)
							.setValue(snapshot1.getValue());

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(
									FirebaseRefOxy.oxygen_supplier_phone_number) + "/" + phone_number)
							.setValue(user_id);
					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier)
									+ "/" + user_id + "/" + phone_number)
							.setValue(keyCell);

					String address = snapshot1.child("data/address").getValue(String.class);
					String address_original = snapshot1.child("data/address_original").getValue(String.class);

					if (address_original.equals(address)) {
						
						inssertAreaData(user_id,supplier_user_name, phone_number, keyCell, address,location);

					} else {
						inssertAreaData(user_id, supplier_user_name,phone_number, keyCell, address,location);
						inssertAreaData(user_id, supplier_user_name,phone_number, keyCell, address_original,location);
						

					}

				}

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inssertAreaData(String user_id,String user_name, String phone_number, String keyCell, String address, String location) {
		String[] arr_address_area = address.split(",");
		String sAddress = "";
		int k = arr_address_area.length-1;
		for (int i = k; i >= 0; i--) {
			
			sAddress=sAddress+arr_address_area[i].trim()+"/";
		}
		
//		String supplier_user_name = dataSnapshot.child("supplier_user_name")
//				.getValue(String.class);
//		String supplierLocation = dataSnapshot.child("location").getValue(String.class);
//		String supplier_user_id = dataSnapshot.child("supplier_user_id")
//				.getValue(String.class);
//		String key_cell_location = dataSnapshot.child("key_cell_location")
				HashMap hm=new HashMap();
		
		hm.put("supplier_user_name", user_name);
		hm.put("supplier_user_id", user_id);
		hm.put("key_cell_location", keyCell);
		hm.put("location", location);
		
		
		FirebaseDatabase.getInstance()
		.getReference(
				ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier_area)
						+ "/" + sAddress + "/supplier_phone_number/" + phone_number)
		.setValue( hm);
	}

	private String getRefData() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier);

	}

	private String getRefPhonNumberUserData() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier_phone_number);

	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.provide_oxy_post_data);
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
