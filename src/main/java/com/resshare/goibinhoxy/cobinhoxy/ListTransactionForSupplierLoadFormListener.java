package com.resshare.goibinhoxy.cobinhoxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.service.map.driver.LocationUtil;

public class ListTransactionForSupplierLoadFormListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				String user_id = snapshot1.child("user_id_destination").getValue(String.class);

				 
			  {
				


					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
			


					HashMap script_param = new HashMap<>();

					// TODO Auto-generated method stub

					Object grid_view_layout_item = Cache.configuration
							.child(FirebaseRefOxy.grid_layout_list_transaction_of_supplier).getValue();
					
					script_param.put("grid_view_layout_item", grid_view_layout_item);

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(
									FirebaseRefOxy.supplier_transaction + "/" + user_id+"/"+DataUtil.getStringDay()))
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									if (snapshot.exists()) {
										Iterable<DataSnapshot> chil = snapshot.getChildren();
										ArrayList grid_view_data = new ArrayList<>();

										for (DataSnapshot dataSnapshot : chil) {

											String customer_phone_number = dataSnapshot.child("customer_phone_number")
													.getValue(String.class);
											
											String customer_user_name = dataSnapshot.child("customer_user_name")
													.getValue(String.class);
											
											 
											HashMap hm = new HashMap<>();
											 
											hm.put("customer_phone_number", customer_phone_number);
											hm.put("customer_user_name", customer_user_name);
											
											hm.put("order_key", dataSnapshot.getKey());
											String customer_user_id  = dataSnapshot.child("customer_user_id")
													.getValue(String.class);
											hm.put("customer_user_id", customer_user_id);
											String status = dataSnapshot.child("status")
													.getValue(String.class);
											hm.put("status", status  );
										
											grid_view_data.add(hm);

										}

										script_param.put("grid_view_data", grid_view_data);



										Map mapReturnData = new HashMap<>();

										mapReturnData.put("script", getScript());
										mapReturnData.put("script_param", script_param);

										objJs.put("user_id_destination", user_id);

										objJs.put("data", mapReturnData);

										ResponseClient.sendResponseScriptUI(objJs);

									}

									// TODO Auto-generated method stub

								}

								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub

								}
							});

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

	private Object getScript() {
		// TODO Auto-generated method stub
		return   ListTransactionForSupplierUI.class.getName();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.load_form_script_list_transaction_of_supplier);
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
