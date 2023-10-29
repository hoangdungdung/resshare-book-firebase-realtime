package com.resshare.goibinhoxy.service.listener;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ListenerBase;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.framework.model.Script;

public abstract class LoadFormOxyBaseListener extends ListenerBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

				HashMap script_param = new HashMap<>();
				Object collection = getReferenceNamePostData();
				System.out.println("collection:" + String.valueOf(collection));
				// "../draft/covid19/create_volunteers_group/post_data";
				script_param.put("post_collection", collection);
				// objJs.put("user_id_destination", user_id);

				Map mapReturnData = new HashMap<>();

				mapReturnData.put("script", getScriptName());
				mapReturnData.put("script_param", script_param);
				mapReturnData.put("script_control_condition", getScriptControlCondition());
				

				objJs.put("data", mapReturnData);

				System.out.println("event:" + objJs.get("event"));
				ResponseClient.sendResponseScriptUI(objJs);

				// ResponseClient.sendResponse(objJs);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract   String getScriptName();
	
	public     String getScriptControlCondition() {
		return "";
	}

	public   String getType()
	{
		return null;
		
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

	public abstract String getReferenceNamePostData();

	 
}
