package com.resshare.book.transaction;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

import model.AcceptBook;

public class BookSupplierAcceptOrderListener extends ListenerBase {

	// update order status
	// insert my transaction customer, supplier
	// send notify dasboard customer

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		
		try {
			if (snapshot.child("processing").getValue() == null) {

		// data
		// customer_address:
		// "customer_address"
		// customer_phone_number:
		// "customer_phone_number"
		// customer_user_id:
		// "-MnOTCqi0a6WocZ5AOd3"
		// customer_user_name:
		// "customer_user_name"
		// description:
		// "Bạn đồng ý cho customer_user_name muon bình oxy..."
		// order_key:
		// "BK-Mo1sxezs-MjhbENB7v0-MnOTCqi0a6WocZ5AOd3-MnOT..."
		// supplier_phone_number:
		// "supplier_phone_number"
		// supplier_user_name:
		// "supplier_user_name"
		// type:
		// "anti_covid_group"

		AcceptBook acceptBook = new AcceptBook();
		String user_id = snapshot.child("user_id").getValue(String.class);
		String application = snapshot.child("application").getValue(String.class);
		String supplier_user_name = snapshot.child("data/supplier_user_name").getValue(String.class);
		boolean free_loan = Boolean.parseBoolean(    snapshot.child("data/free_loan").getValue( ).toString());

		acceptBook.setAcceptor_id(user_id);
		acceptBook.setBook_id(snapshot.child("data/book_id").getValue(String.class));
		acceptBook.setBook_item_id(snapshot.child("data/book_item_id").getValue(String.class));
		String customer_user_id = snapshot.child("data/customer_user_id").getValue(String.class);

		acceptBook.setReceiver_id(customer_user_id);
		acceptBook.setFree_loan(free_loan);
		
		
		FirebaseDatabase.getInstance()
		.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile)
				+ "/" + user_id)
		.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshotSupplier) {
				
				if(snapshotSupplier.exists())
				{
					String  supplier_user_name = snapshotSupplier.child("user_name")
							.getValue(String.class);
					
					String address = snapshotSupplier.child("address")
							.getValue(String.class);
					String phone_number = snapshotSupplier.child("phone_number")
							.getValue(String.class);
					
					
					Map map = DataUtil.ConvertDataSnapshotToMap(snapshot);
					map.put("data", acceptBook);
					String sType="";
					String sRefFire="";
					 
					if(free_loan)
						{
						sType =	" đồng ý cho bạn mươn sách";
						sRefFire = RefFireBaseBook.BOOK_DATA_ACCEPT_RENT_BOOK;
						}
						
						else
						{	
							sType=	" đồng ý  bán sách cho bạn  ";
							sRefFire = RefFireBaseBook.BOOK_DATA_ACCEPT_SALE_BOOK;
						
						}
					
					FirebaseDatabase.getInstance().getReference(sRefFire).push().setValue(map);
					
				    

					// send notify dasboard customer

					DashboardMessage dashboardMessage = new DashboardMessage();
					dashboardMessage.setApplication(snapshot.child("application").getValue(String.class));
					dashboardMessage.setDelete(1);
					dashboardMessage.setEvent(snapshot.child("event_dashboard_current_day").getValue(String.class));

					dashboardMessage.setUser_id_destination(customer_user_id);

					Map objJs = dashboardMessage.totHashMap();
					Map hashFieldValue = new HashMap<>();
					
				
					

					hashFieldValue.put("description", supplier_user_name +" sdt: "+phone_number +" địa chỉ: "+ address+ sType);

					hashFieldValue.put("supplier_user_name", supplier_user_name);
					hashFieldValue.put("supplier_phone_number", phone_number);

					Map mapReturnData = new HashMap<>();
					Map script_param = new HashMap<>();

					// script_param.put("description_change","Tổ covid cộng đồng đã xác nhận thông
					// tin của bạn. Bạn hãy vào mục hỗ trợ mua hàng để mua hàng rồi gửi cho họ");

					String path = FirebaseRefOxy.dashboard_layout_customer_received_notify_accept_order;

					Object cfgLayout = Cache.configuration.child(path).getValue();

					mapReturnData.put("layout", cfgLayout);
					script_param.put("hash_field_value", hashFieldValue);

					mapReturnData.put("layout", cfgLayout);
					// CustomerReceivedNotifyMsgDashboardUI customerReceivedNotifyMsgDashboardUI =
					// new CustomerReceivedNotifyMsgDashboardUI();
					mapReturnData.put("script", BookCustomerReceivedNotifyMsgDashboardUI.class.getName());

					mapReturnData.put("script_param", script_param);

					objJs.put("data", mapReturnData);
					objJs.put("on_top", 1);

					ResponseClient.sendResponseScriptUI(objJs);
					
					
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});

		
		
		
		
		
		
		
		
		
		
		
		

	

		String messageKey = snapshot.child("message_key").getValue(String.class);

		ResponseClient.removeResponseMsg(application, user_id, messageKey);
		
		
		

		FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
		.child("processing").setValue("done");
}
} catch (Exception e) {
	FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
	.child("processing").setValue("error");
e.printStackTrace();
}

// TODO Auto-generated method stub

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
