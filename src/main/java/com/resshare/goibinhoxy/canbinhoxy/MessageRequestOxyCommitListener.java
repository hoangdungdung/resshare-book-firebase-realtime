package com.resshare.goibinhoxy.canbinhoxy;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.cobinhoxy.SupplierAcceptOrderMsgDashboardUI;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class MessageRequestOxyCommitListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			if (snapshot.child("processing").getValue() == null) {
		
		String customer_user_id = snapshot.child("user_id").getValue(String.class);
		String supplier_phone_number = snapshot.child("data/supplier_phone_number").getValue(String.class);
		String supplier_user_id = snapshot.child("data/supplier_user_id").getValue(String.class);
		String supplier_key_cell_location = snapshot.child("data/key_cell_location").getValue(String.class);

		/// supplier_key_cell_location+"/"+supplier_phone_number

		FirebaseDatabase.getInstance()
				.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.customer_info))
				.child(customer_user_id).addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot customerSnapshot) {
						FirebaseDatabase.getInstance()
								.getReference(ResFirebaseReference
										.getDataPathReference(FirebaseRefOxy.oxygen_supplier_location + "/"
												+ supplier_key_cell_location + "/" + supplier_phone_number))
								.addListenerForSingleValueEvent(new ValueEventListener() {

									@Override
									public void onDataChange(DataSnapshot supplierSnapshot) {

										String order_key = FirebaseDatabase.getInstance()
												.getReference(
														ResFirebaseReference.getDataPathReference(FirebaseRefOxy.order))
												.push().getKey();
										HashMap order_value = new HashMap();
										order_value.put("supplier_info", supplierSnapshot.getValue());
										order_value.put("customer_info", customerSnapshot.getValue());
										String customer_user_id =	customerSnapshot.child("user_id").getValue(String.class);
										String customer_address =	customerSnapshot.child("data/address").getValue(String.class);
										String customer_phone_number =	customerSnapshot.child("data/customer_phone_number").getValue(String.class);
										String customer_user_name =	customerSnapshot.child("data/customer_user_name").getValue(String.class);
										
										
										String supplier_phone_number =	supplierSnapshot.child("data/supplier_phone_number").getValue(String.class);
										String supplier_user_name =	supplierSnapshot.child("data/supplier_user_name").getValue(String.class);
										
								
										
										// save order
										FirebaseDatabase.getInstance()
												.getReference(
														ResFirebaseReference.getDataPathReference(FirebaseRefOxy.order))
												.child(order_key).setValue(order_value);
										// send dasboard message supplier

										DashboardMessage dashboardMessage = new DashboardMessage();
										dashboardMessage.setApplication(
												supplierSnapshot.child("application").getValue(String.class));
										dashboardMessage.setDelete(0);
										dashboardMessage.setEvent(customerSnapshot.child("event_dashboard_current_day")
												.getValue(String.class));
										String supplier_user_id =	supplierSnapshot.child("user_id").getValue(String.class);
									
										dashboardMessage.setUser_id_destination(supplier_user_id);

										Map objJs = dashboardMessage.totHashMap();
										Map hashFieldValue = new HashMap<>();
										 
										hashFieldValue.put("description", "Bạn đồng ý cho "+customer_user_name +" muon bình oxy bấm Xác nhận ");
										hashFieldValue.put("customer_user_id", customer_user_id);
										hashFieldValue.put("customer_phone_number",customer_phone_number);
										hashFieldValue.put("customer_user_name", customer_user_name);
										hashFieldValue.put("customer_address", customer_address);
										
										 
										
										hashFieldValue.put("supplier_user_name",supplier_user_name);
										hashFieldValue.put("supplier_phone_number", supplier_phone_number);
										hashFieldValue.put("order_key", order_key);
 
										
										

										Map mapReturnData = new HashMap<>();
										Map script_param = new HashMap<>();

										// script_param.put("description_change","Tổ covid cộng đồng đã xác nhận thông
										// tin của bạn. Bạn hãy vào mục hỗ trợ mua hàng để mua hàng rồi gửi cho họ");

										String path = FirebaseRefOxy.dashboard_layout_accept_order;

										Object cfgLayout = Cache.configuration.child(path).getValue();

										mapReturnData.put("layout", cfgLayout);
										script_param.put("hash_field_value",hashFieldValue);
										
										script_param.put("description_change", "Xác nhận thành công");

										script_param.put("post_collection", FirebaseRefOxy.accept_order);

										mapReturnData.put("layout", cfgLayout);
									//	SupplierAcceptOrderMsgDashboardUI supplierAcceptOrderMsgDashboardUI = new SupplierAcceptOrderMsgDashboardUI();
										mapReturnData.put("script",
												SupplierAcceptOrderMsgDashboardUI.class.getName());

										mapReturnData.put("script_param", script_param);

										objJs.put("data", mapReturnData);

										ResponseClient.sendResponseScriptUI(objJs);

									}

									@Override
									public void onCancelled(DatabaseError error) {
										// TODO Auto-generated method stub

									}
								});

					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});
		FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
		.child("processing").setValue("done");
			}
		
	} catch (Exception e) {
		e.printStackTrace();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
		.child("processing").setValue("error");
	}

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

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.message_request_oxy_commit);
	}

}
