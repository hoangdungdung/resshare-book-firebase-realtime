package com.resshare.goibinhoxy.canbinhoxy;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.RequestClient;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class MapTransactionForCustomerLoadFormListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				String user_id = snapshot1.child("user_id_destination").getValue(String.class);

				{

					// String phone_number =
					// snapshot1.child("data/customer_phone_number").getValue(String.class);

					// insert data

					// String key_cell_location = LocationUtil.getCellMapKeyByLatLong2km(location);

					// get data user có bình oxy
					// oxygen_supplier
					// hiển thị lên grid

					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

					String order_key = snapshot1.child("parameter/order_key").getValue(String.class);

					// Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
					// Object grid_view_layout_item =
					// uIBuilder.getScriptShadowParam("grid_view_layout_item");
					HashMap script_param = new HashMap<>();
					//
					// // TODO Auto-generated method stub
					//
					// Object grid_view_layout_item = Cache.configuration
					// .child(FirebaseRefOxy.grid_layout_list_customer_transaction).getValue();
					//
					// script_param.put("grid_view_layout_item", grid_view_layout_item);

					FirebaseDatabase.getInstance()
							.getReference(
									ResFirebaseReference.getDataPathReference(FirebaseRefOxy.order + "/" + order_key))
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									if (snapshot.exists()) {

										Object order = snapshot.getValue();
										// supplier_info/data/location
										// customer_info/data/location
										HashMap path = new HashMap<>();
										path.put("start_location",
												snapshot.child("supplier_info/data/location").getValue());
										path.put("end_location",
												snapshot.child("customer_info/data/location").getValue());

										script_param.put("path", path);
										script_param.put("order_key", order_key);
										
									//	HashMap script_param = new HashMap<>();
										script_param.put("location", snapshot.child("supplier_info/current/location").getValue());
										script_param.put("user_id", snapshot.child("supplier_info/user_id").getValue());
										script_param.put("vehicle_type", "car");

										// goibinhoxy/configuration/system_setting/layout/android/grid_layout/grid_oxy_supplier

										// Object collection=getReferenceNamePostData();
										// "../draft/covid19/create_volunteers_group/post_data";
										// script_param.put("post_collection", collection);
										// objJs.put("user_id_destination", user_id);

										Map data = new HashMap<>();

										data.put("script", getScript());
										data.put("script_param", script_param);

										objJs.put("user_id_destination", user_id);

										objJs.put("data", data);

										// ResponseClient.sendResponse(objJs);

										String secretKey = "123";
										String sDataCollection = FirebaseRefOxy.location_draw_path;
										String application = "resshare_driver_location";
										RequestClient.sendRequest(application, secretKey, sDataCollection, objJs);

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
		return   MapTransactionForCustomerUI.class.getName();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.load_form_script_map_list_transaction_of_customer);
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
