package com.resshare.goibinhoxy.cobinhoxy;

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

public class MapTransactionForSupplierLoadFormListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				String user_id = snapshot1.child("user_id_destination").getValue(String.class);

				{



					Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

					String order_key = snapshot1.child("parameter/order_key").getValue(String.class);


					HashMap script_param = new HashMap<>();

					FirebaseDatabase.getInstance()
							.getReference(
									ResFirebaseReference.getDataPathReference(FirebaseRefOxy.order + "/" + order_key))
							.addListenerForSingleValueEvent(new ValueEventListener() {

								@Override
								public void onDataChange(DataSnapshot snapshot) {
									if (snapshot.exists()) {

										Object order = snapshot.getValue();

										HashMap path=new HashMap<>();
										path.put("start_location", snapshot.child("supplier_info/data/location").getValue());
										path.put("end_location", snapshot.child("customer_info/data/location").getValue());
								

										script_param.put("path", path);
										script_param.put("order_key", order_key);
										Map data = new HashMap<>();

										data.put("script", getScript());
										data.put("script_param", script_param);

										objJs.put("user_id_destination", user_id);

										objJs.put("data", data);



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
		return   MapTransactionForSupplierUI.class.getName();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.load_form_script_map_list_transaction_of_supplier);
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
