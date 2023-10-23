package com.resshare.goibinhoxy.canbinhoxy;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;

public class CustomerLocationChangeListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {
				  String user_id_actor = snapshot1.child("user_id").getValue(String.class);

				// Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
				String application = snapshot1.child("application").getValue(String.class);
				String event = snapshot1.child("event").getValue(String.class);

				String order_key = snapshot1.child("data/order_key").getValue(String.class);
				String location = snapshot1.child("data/location").getValue(String.class);

				HashMap currentHashMap = new HashMap<>();
				currentHashMap.put("location", location);
				currentHashMap.put("event", event);

				FirebaseDatabase.getInstance()
						.getReference(ResFirebaseReference.getDataPathReference(
								FirebaseRefOxy.order + "/" + order_key + "/customer_info/current"))
						.setValue(currentHashMap);

				
				FirebaseDatabase.getInstance()
				.getReference(ResFirebaseReference.getDataPathReference(
						FirebaseRefOxy.order + "/" + order_key+"/supplier_info" )).addListenerForSingleValueEvent(new ValueEventListener() {
							
							@Override
							public void onDataChange(DataSnapshot snapshot) {
								if(snapshot!=null)
								{
									if(snapshot.exists())
									{
										String user_id_destination = snapshot.child("user_id").getValue(String.class);
										String event_cus = snapshot.child("current/event").getValue(String.class);
										if(event_cus!=null)
										{
											HashMap viewHashMap = new HashMap<>();
											viewHashMap.put("application", application);
											viewHashMap.put("event", event_cus);
											viewHashMap.put("user_id_destination", user_id_destination);
											
											HashMap data = new HashMap<>();
											HashMap script_param = new HashMap<>();
											script_param.put("location", location);
											script_param.put("user_id", user_id_actor);
											script_param.put("vehicle_type", "car");

											data.put("script", getScriptName());
											data.put("script_param", script_param);
											
											viewHashMap.put("data", data);
											
											ResponseClient.sendResponseScriptUI (viewHashMap);
											
											
											
										}
									}
										
								}
								
								
							}
							
						

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub
								
							}
						});
				
				
				
				
				
				
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
		}
	}
	private String getScriptName() {
		// TODO Auto-generated method stub
		return CustomerLocationChangeUI.class.getName();
	}
	private Object getScript() {
		// TODO Auto-generated method stub
		return new CustomerLocationChangeUI().getUIBuilder().getScript();
	}

	public String getReferenceName() {

		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.customer_location_changed);
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
