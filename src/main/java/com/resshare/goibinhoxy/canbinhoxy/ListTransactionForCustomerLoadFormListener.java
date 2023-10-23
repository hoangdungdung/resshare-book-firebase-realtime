package com.resshare.goibinhoxy.canbinhoxy;

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

public class ListTransactionForCustomerLoadFormListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				String user_id = snapshot1.child("user_id_destination").getValue(String.class);

				 
			  {
				
				//	String phone_number = snapshot1.child("data/customer_phone_number").getValue(String.class);

					// insert data

				//	String key_cell_location = LocationUtil.getCellMapKeyByLatLong2km(location);

					// get data user có bình oxy
					// oxygen_supplier
					// hiển thị lên grid

					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
			

					// Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
					// Object grid_view_layout_item =
					// uIBuilder.getScriptShadowParam("grid_view_layout_item");
					HashMap script_param = new HashMap<>();

					// TODO Auto-generated method stub

					Object grid_view_layout_item = Cache.configuration
							.child(FirebaseRefOxy.grid_layout_list_transaction_of_customer).getValue();
					
					script_param.put("grid_view_layout_item", grid_view_layout_item);
					
//					script_param.put("direction_dynamic_activity", FirebaseRefOxy.DirectionDynamicActivity);
//					script_param.put("map_list_transaction_of_customer", FirebaseRefOxy.map_list_transaction_of_customer);
					
				 

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(
									FirebaseRefOxy.customer_transaction + "/" + user_id+"/"+DataUtil.getStringDay()))
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									if (snapshot.exists()) {
										Iterable<DataSnapshot> chil = snapshot.getChildren();
										ArrayList grid_view_data = new ArrayList<>();

										for (DataSnapshot dataSnapshot : chil) {

											String supplier_phone_number = dataSnapshot.child("supplier_phone_number")
													.getValue(String.class);
											
											String supplier_user_name = dataSnapshot.child("supplier_user_name")
													.getValue(String.class);
											
											 
											HashMap hm = new HashMap<>();
											 
											hm.put("supplier_phone_number", supplier_phone_number);
											hm.put("supplier_user_name", supplier_user_name);
											
											hm.put("order_key", dataSnapshot.getKey());
											String supplier_user_id  = dataSnapshot.child("supplier_user_id")
													.getValue(String.class);
											hm.put("supplier_user_id", supplier_user_id);
											String status = dataSnapshot.child("status")
													.getValue(String.class);
											hm.put("status", status  );
										
											grid_view_data.add(hm);

										}

										script_param.put("grid_view_data", grid_view_data);

										// goibinhoxy/configuration/system_setting/layout/android/grid_layout/grid_oxy_supplier

										// Object collection=getReferenceNamePostData();
										// "../draft/covid19/create_volunteers_group/post_data";
										// script_param.put("post_collection", collection);
										// objJs.put("user_id_destination", user_id);

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

					// String group_key = keyCell + "/" + snapshot1.getKey();
					// FirebaseDatabase.getInstance().getReference(getRefData() + "/" + keyCell +
					// "/" + phone_number)
					// .setValue(snapshot1.getValue());
					//
					// FirebaseDatabase.getInstance().getReference(getRefPhonNumberUserData() + "/"
					// + phone_number)
					// .setValue(user_id);

					// // inssert member
					// String pathMember = getRefMember() + "/" + keyCell + "/" + snapshot1.getKey()
					// + "/"
					// + snapshot1.child("user_id").getValue(String.class);
					// FirebaseDatabase.getInstance().getReference(pathMember)
					// .setValue(snapshot1.child("data/phone_number").getValue());
					// // insert owner
					// FirebaseDatabase.getInstance()
					// .getReference(
					// FirebaseRefCovid19.data_covid19 + "/" + getType() + "_admin_member_user/" +
					// user_id)
					// .setValue(group_key);
					// FirebaseDatabase.getInstance().getReference(
					// FirebaseRefCovid19.data_covid19 + "/" + getType() + "_owner/" + user_id + "/"
					// + group_key)
					// .setValue(phone_number);
					//
					// // insert phone number
					// FirebaseDatabase.getInstance()
					// .getReference(
					// FirebaseRefCovid19.data_covid19 + "/" + getType() + "_phone_number/" +
					// phone_number)
					// .setValue(group_key);

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
		return   ListTransactionForCustomerUI.class.getName();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.load_form_script_list_transaction_of_customer);
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
