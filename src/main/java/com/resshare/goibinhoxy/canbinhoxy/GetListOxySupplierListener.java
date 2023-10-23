package com.resshare.goibinhoxy.canbinhoxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.DistanceCalculator;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.service.map.driver.LocationUtil;

public class GetListOxySupplierListener extends ListenerBase {

	private void getAreaData(String user_id, Map objJs, HashMap script_param, HashMap hmSupplier, ArrayList grid_view_data, String address, String customerLocation) {
		String[] arr_address_area = address.split(",");
		String sAddress = "";
		int k = arr_address_area.length - 1;
		for (int i = k; i >= 0; i--) {

			sAddress =sAddress+"/"+ arr_address_area[i].trim();
			FirebaseDatabase.getInstance()
					.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.oxygen_supplier_area) 
							+ sAddress + "/supplier_phone_number/")
					.addListenerForSingleValueEvent(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshot) {

							if (snapshot.exists()) {
								Iterable<DataSnapshot> chil = snapshot.getChildren();

								HashMap hmView = new HashMap<>();

								for (DataSnapshot dataSnapshot : chil) {
									String supplier_phone_number = dataSnapshot.getKey();
									String supplier_user_name = dataSnapshot.child("supplier_user_name")
											.getValue(String.class);
									String supplierLocation = dataSnapshot.child("location").getValue(String.class);
									String supplier_user_id = dataSnapshot.child("supplier_user_id")
											.getValue(String.class);
									String key_cell_location = dataSnapshot.child("key_cell_location")
											.getValue(String.class);

									String distance = DistanceCalculator.getDistance(customerLocation, supplierLocation,
											"K");

									HashMap hm = new HashMap<>();
									hm.put("supplier_user_name", supplier_user_name);
									hm.put("distance", " cách: " + distance + " km");

									hm.put("supplier_phone_number", supplier_phone_number);
									hm.put("supplier_user_id", supplier_user_id);
									hm.put("key_cell_location", key_cell_location);
									if(!hmSupplier.containsKey(supplier_phone_number))
									{
									hmSupplier.put(supplier_phone_number, hm);
									hmView.put(supplier_phone_number, hm);
									}
								 

								}
								viewUI(user_id, objJs, script_param, hmView, grid_view_data);
							}
							// TODO Auto-generated method stub

						}

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});
		}

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				String customerLocation = snapshot1.child("data").child("location").getValue(String.class);
				if (customerLocation != null) {
					String user_id = snapshot1.child("user_id").getValue(String.class);
					String phone_number = snapshot1.child("data/customer_phone_number").getValue(String.class);

					// insert data

					String key_cell_location = LocationUtil.getCellMapKeyByLatLong2km(customerLocation);

					// get data user có bình oxy
					// oxygen_supplier
					// hiển thị lên grid

					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
					Map objJsData = (Map) objJs.get("data");

					objJsData.put("key_cell_location", key_cell_location);
					objJs.put("data", objJsData);

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.customer_info))
							.child(user_id).setValue(objJs);

					FirebaseDatabase.getInstance()
							.getReference(
									ResFirebaseReference.getDataPathReference(FirebaseRefOxy.customer_phone_number))
							.child(phone_number).setValue(user_id);

					// Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
					// Object grid_view_layout_item =
					// uIBuilder.getScriptShadowParam("grid_view_layout_item");
					HashMap script_param = new HashMap<>();

					// TODO Auto-generated method stub

					Object grid_view_layout_item = Cache.configuration
							.child(FirebaseRefOxy.grid_layout_grid_oxy_supplier).getValue();
					script_param.put("grid_view_layout_item", grid_view_layout_item);

					String address = snapshot1.child("data/address").getValue(String.class);
					String address_original = snapshot1.child("data/address_original").getValue(String.class);
					ArrayList grid_view_data = new ArrayList<>();

					FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getDataPathReference(
									FirebaseRefOxy.oxygen_supplier_location + "/" + key_cell_location))
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									  HashMap hmSupplier=new HashMap<>();

									if (address_original.equals(address)) {

										getAreaData(  user_id, objJs, script_param, hmSupplier, grid_view_data,address,customerLocation);

									} else {
										 
										getAreaData( user_id, objJs, script_param, hmSupplier, grid_view_data, address,customerLocation);
										getAreaData( user_id, objJs, script_param, hmSupplier, grid_view_data, address_original,customerLocation);
									 

									}
									if (snapshot.exists()) {
										Iterable<DataSnapshot> chil = snapshot.getChildren();
									
										HashMap hmView = new HashMap<>();
										for (DataSnapshot dataSnapshot : chil) {

											String request_user_name = dataSnapshot.child("data/supplier_user_name")
													.getValue(String.class);
											String supplierLocation = dataSnapshot.child("data").child("location")
													.getValue(String.class);
											String distance = DistanceCalculator.getDistance(customerLocation,
													supplierLocation, "K");

											HashMap hm = new HashMap<>();
											hm.put("supplier_user_name", request_user_name);
											hm.put("distance", " cách: " + distance + " km");
											String supplier_phone_number = dataSnapshot
													.child("data/supplier_phone_number").getValue(String.class);
											hm.put("supplier_phone_number", supplier_phone_number);
											hm.put("supplier_user_id",
													dataSnapshot.child("user_id").getValue(String.class));
											hm.put("key_cell_location", key_cell_location);
											if(!hmSupplier.containsKey(supplier_phone_number))
											{
											hmSupplier.put(supplier_phone_number, hm);
											hmView.put(supplier_phone_number, hm);
											}
											

										}
										
										viewUI(user_id, objJs, script_param, hmView, grid_view_data);

									}

									// TODO Auto-generated method stub

								}


							

								 
								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub

								}
							});

					// FirebaseDatabase.getInstance()
					// .getReference(ResFirebaseReference.getConfigurationPath(FirebaseRefOxy.grid_oxy_supplier))
					// .addListenerForSingleValueEvent(new ValueEventListener() {
					//
					// @Override
					// public void onDataChange(DataSnapshot snapshot) {
					// }
					//
					// @Override
					// public void onCancelled(DatabaseError error) {
					// // TODO Auto-generated method stub
					//
					// }
					// });
				 

					 

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
	private void viewUI(String user_id, Map objJs, HashMap script_param, HashMap hmSupplier,
			ArrayList grid_view_data) {
		for (Object object : hmSupplier.values()) {
			grid_view_data.add(object);
			
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
	private Object getScript() {
		// TODO Auto-generated method stub
		return  GetListOxySupplierUI.class.getName();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.call_oxy_post_data);
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
