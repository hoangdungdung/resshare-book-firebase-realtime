
//com.resshare.book/LoadFormProfileOxyUI
//AddProfileListener

package com.resshare.book.profile;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.VNCharacterUtils;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.service.map.driver.LocationUtil;

public class AddProfileListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				String location = snapshot1.child("data").child("location").getValue(String.class);
				if (location != null) {
					String user_id = snapshot1.child("user_id").getValue(String.class);
					String phone_number = snapshot1.child("data/phone_number").getValue(String.class);
					
					DataSnapshot data = snapshot1.child("data") ; 
					
					Map dataHm = DataUtil.ConvertDataSnapshotToMap(data);
					
					String supplier_user_name = snapshot1.child("data/user_name").getValue(String.class);
					// insert data
					String keyCell = LocationUtil.getCellMapKeyByLatLong2km(location);
					dataHm.put("cell", keyCell);
					String group_key = keyCell + "/" + snapshot1.getKey();
					
					
					
					
					FirebaseDatabase.getInstance()
							.getReference(
									ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile_location)
											+ "/" + keyCell + "/" + user_id)
							.setValue(phone_number);

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(
									FirebaseRefOxy.profile_phone_number) + "/" + phone_number)
							.setValue(user_id);
					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile)
									+ "/" + user_id  )
							.setValue(dataHm);

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

	private void inssertAreaData(String user_id,String user_name, String phone_number, String keyCell, String address_native, String location) {
		
		if(address_native==null)
			return;
		
		String address1 = VNCharacterUtils.removeAccent(address_native ).toLowerCase();
		
		String address = address1.replaceAll("  "," ");
		
		
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
		
	
		hm.put("user_id", user_id);
		
		hm.put("location", location);
		String rssAddress = DigestUtils.sha256Hex(sAddress);
		
		FirebaseDatabase.getInstance()
		.getReference(
				ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile_area)
						+ "/" + rssAddress + "/phone_number/" + phone_number)
		.setValue( hm);
	}

	private String getRefData() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile);

	}

	private String getRefPhonNumberUserData() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile_phone_number);

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
