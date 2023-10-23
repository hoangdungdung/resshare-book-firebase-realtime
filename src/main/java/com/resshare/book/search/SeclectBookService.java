package com.resshare.book.search;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.book.transaction.BookSupplierAcceptOrderMsgDashboardUI;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

import service.ServiceBase;

public class SeclectBookService extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot customerSnapshot, String previousChildName) {

		try {
			if (customerSnapshot.child("processing").getValue() == null) {

				String book_id = customerSnapshot.child("data/book_id").getValue(String.class);
				// String renter_id =
				// customerSnapshot.child("data/renter_id").getValue(String.class);
				String owner_id = customerSnapshot.child("data/owner_id").getValue(String.class);
				String book_item_id = customerSnapshot.child("data/book_item_id").getValue(String.class);
				String renter_id = customerSnapshot.child("user_id").getValue(String.class);
				String book_name = customerSnapshot.child("data/book_name").getValue(String.class);
				boolean free_loan = Boolean.parseBoolean(customerSnapshot.child("data/free_loan").getValue( ).toString());
				 
				String tran_id = book_id + renter_id + owner_id + book_item_id;

				FirebaseDatabase.getInstance()
						.getReference(
								ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile) + "/" + renter_id)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshotRenter) {
								String customer_user_name ="Không có thông tin";
								String address  ="Không có thông tin";
								String phone_number  ="Không có thông tin";
								if (snapshotRenter.exists()) {
									  customer_user_name = snapshotRenter.child("user_name")
											.getValue(String.class);
									
									  address = snapshotRenter.child("address")
											.getValue(String.class);
									  phone_number = snapshotRenter.child("phone_number")
											.getValue(String.class);
									 
									
									
									

								}
								FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_TRANS_RENT).child(tran_id)
								.setValue(customerSnapshot.child("data").getValue());

						// send dasboard message supplier

						DashboardMessage dashboardMessage = new DashboardMessage();
						dashboardMessage.setApplication(customerSnapshot.child("application").getValue(String.class));
						dashboardMessage.setDelete(0);
						dashboardMessage.setEvent(customerSnapshot.child("event_dashboard_current_day").getValue(String.class));
						String supplier_user_id = owner_id;

						dashboardMessage.setUser_id_destination(supplier_user_id);

						Map objJs = dashboardMessage.totHashMap();
						Map hashFieldValue = new HashMap<>();
						String sType="";
						if(free_loan)
						sType=	" muốn mượn quyển sách: ";
						else
							sType=	" muốn mua quyển sách: ";

						hashFieldValue.put("description",
								customer_user_name +" sdt: "+phone_number+" tại: " + address + sType +book_name+"  của bạn ");
						hashFieldValue.put("customer_user_id", renter_id);
						hashFieldValue.put("customer_phone_number", phone_number);
						hashFieldValue.put("customer_user_name", customer_user_name);
						hashFieldValue.put("customer_address", address);

						hashFieldValue.put("supplier_user_id", supplier_user_id);
						hashFieldValue.put("supplier_user_name", "supplier_user_name");
						hashFieldValue.put("supplier_phone_number", "supplier_phone_number");
						hashFieldValue.put("order_key", tran_id);
						hashFieldValue.put("book_id", book_id);
						hashFieldValue.put("book_item_id", book_item_id);
						hashFieldValue.put("free_loan", free_loan);



						Map mapReturnData = new HashMap<>();
						Map script_param = new HashMap<>();

						// script_param.put("description_change","Tổ covid cộng đồng đã xác nhận thông
						// tin của bạn. Bạn hãy vào mục hỗ trợ mua hàng để mua hàng rồi gửi cho họ");

						String path = FirebaseRefOxy.dashboard_layout_accept_order;

						Object cfgLayout = Cache.configuration.child(path).getValue();

						mapReturnData.put("layout", cfgLayout);
						script_param.put("hash_field_value", hashFieldValue);

						script_param.put("description_change", "Giao dịch thành công");

						script_param.put("post_collection", FirebaseRefOxy.accept_order);

						mapReturnData.put("layout", cfgLayout);
						// SupplierAcceptOrderMsgDashboardUI supplierAcceptOrderMsgDashboardUI = new
						// SupplierAcceptOrderMsgDashboardUI();
						mapReturnData.put("script", BookSupplierAcceptOrderMsgDashboardUI.class.getName());

						mapReturnData.put("script_param", script_param);

						objJs.put("data", mapReturnData);
						objJs.put("on_top", 1);

						ResponseClient.sendResponseScriptUI(objJs);

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});



				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(customerSnapshot.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_INPUT_post_collection_select_item);
	}
}
