package com.resshare.goibinhoxy.cobinhoxy;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.canbinhoxy.CustomerReceivedNotifyMsgDashboardUI;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class SupplierAcceptOrderListener extends ListenerBase {

	// update order status
	// insert my transaction customer, supplier
	// send notify dasboard customer
	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
			if (snapshot.child("processing").getValue() == null) {
				String application = snapshot.child("application").getValue(String.class);
				String user_id = snapshot.child("user_id").getValue(String.class);

				String customer_user_id = snapshot.child("data/customer_user_id").getValue(String.class);
				String customer_phone_number = snapshot.child("data/customer_phone_number").getValue(String.class);
				String customer_user_name = snapshot.child("data/customer_user_name").getValue(String.class);

				String supplier_phone_number = snapshot.child("data/supplier_phone_number").getValue(String.class);
				String supplier_user_name = snapshot.child("data/supplier_user_name").getValue(String.class);
				String supplier_user_id = snapshot.child("user_id").getValue(String.class);
				// update order status
				String order_key = snapshot.child("data/order_key").getValue(String.class);
				String order_path = ResFirebaseReference.getDataPathReference(FirebaseRefOxy.order);

				FirebaseDatabase.getInstance().getReference(order_path).child(order_key).child("status")
						.setValue("accepted");
				// insert my transaction customer,
				HashMap tranvalueSupplierCust = new HashMap();
				tranvalueSupplierCust.put("supplier_user_id", supplier_user_id);
				tranvalueSupplierCust.put("supplier_phone_number", supplier_phone_number);
				tranvalueSupplierCust.put("supplier_user_name", supplier_user_name);
				tranvalueSupplierCust.put("status", "accepted");

				
				String customer_tran_path = ResFirebaseReference
						.getDataPathReference(FirebaseRefOxy.customer_transaction);
				customer_tran_path = customer_tran_path + "/" + customer_user_id + "/" + DataUtil.getStringDay() + "/"
						+ order_key;
				FirebaseDatabase.getInstance().getReference(customer_tran_path).setValue(tranvalueSupplierCust);

				// insert my transaction supplier,
				HashMap tranvalueCust = new HashMap();
				tranvalueCust.put("customer_user_id", customer_user_id);
				tranvalueCust.put("customer_phone_number", customer_phone_number);
				tranvalueCust.put("customer_user_name", customer_user_name);
				
				tranvalueCust.put("status", "accepted");
				String supplier_tran_path = ResFirebaseReference
						.getDataPathReference(FirebaseRefOxy.supplier_transaction);
				supplier_tran_path = supplier_tran_path + "/" + supplier_user_id + "/" + DataUtil.getStringDay() + "/"
						+ order_key;
				FirebaseDatabase.getInstance().getReference(supplier_tran_path).setValue(tranvalueCust);

				// insert my transaction supplier_group_user,

				String supplier_group_user_path = ResFirebaseReference
						.getDataPathReference(FirebaseRefOxy.supplier_group_user);

				FirebaseDatabase.getInstance().getReference(supplier_group_user_path).child(supplier_user_id)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot supplier_group_user_path_snapshot) {

								if (supplier_group_user_path_snapshot.exists()) {
									String supplier_group_key = supplier_group_user_path_snapshot
											.getValue(String.class);
									// insert data transaction supplier_group
									String supplier_group_tran_path = ResFirebaseReference
											.getDataPathReference(FirebaseRefOxy.supplier_group_transaction);
									supplier_group_tran_path = supplier_group_tran_path + "/" + supplier_group_key + "/"
											+ DataUtil.getStringDay() + "/" + supplier_user_id + "/" + order_key;
									FirebaseDatabase.getInstance().getReference(supplier_group_tran_path)
											.setValue(tranvalueCust);

								}

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

				// send notify dasboard customer

				DashboardMessage dashboardMessage = new DashboardMessage();
				dashboardMessage.setApplication(snapshot.child("application").getValue(String.class));
				dashboardMessage.setDelete(1);
				dashboardMessage.setEvent(snapshot.child("event_dashboard_current_day").getValue(String.class));

				dashboardMessage.setUser_id_destination(customer_user_id);

				Map objJs = dashboardMessage.totHashMap();
				Map hashFieldValue = new HashMap<>();

				hashFieldValue.put("description", supplier_user_name + " dong y cho muon bạn mươn bình oxy");

				hashFieldValue.put("supplier_user_name", supplier_user_name);
				hashFieldValue.put("supplier_phone_number", supplier_phone_number);

				Map mapReturnData = new HashMap<>();
				Map script_param = new HashMap<>();

				// script_param.put("description_change","Tổ covid cộng đồng đã xác nhận thông
				// tin của bạn. Bạn hãy vào mục hỗ trợ mua hàng để mua hàng rồi gửi cho họ");

				String path = FirebaseRefOxy.dashboard_layout_customer_received_notify_accept_order;

				Object cfgLayout = Cache.configuration.child(path).getValue();

				mapReturnData.put("layout", cfgLayout);
				script_param.put("hash_field_value", hashFieldValue);

				mapReturnData.put("layout", cfgLayout);
				//CustomerReceivedNotifyMsgDashboardUI customerReceivedNotifyMsgDashboardUI = new CustomerReceivedNotifyMsgDashboardUI();
				mapReturnData.put("script", CustomerReceivedNotifyMsgDashboardUI.class.getName());

				mapReturnData.put("script_param", script_param);

				objJs.put("data", mapReturnData);

				ResponseClient.sendResponseScriptUI(objJs);

				String messageKey = snapshot.child("message_key").getValue(String.class);
				 
				ResponseClient.removeResponseMsg(application,user_id,messageKey);
			//	ResponseClient.removeResponse(messageKey);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
						.child("processing").setValue("done");
			}

		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey()).child("processing")
					.setValue("error");
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
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.accept_order);
	}

}
