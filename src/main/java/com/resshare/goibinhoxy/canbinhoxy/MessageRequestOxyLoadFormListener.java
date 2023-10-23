package com.resshare.goibinhoxy.canbinhoxy;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.framework.model.Script;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class MessageRequestOxyLoadFormListener extends LoadFormOxyBaseListener {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

				HashMap supplier_info = (HashMap) objJs.get("parameter");

				HashMap script_param = new HashMap<>();
				Object collection = getReferenceNamePostData();
				// "../draft/covid19/create_volunteers_group/post_data";
				script_param.put("post_collection", collection);

				script_param.put("supplier_phone_number", supplier_info.get("supplier_phone_number"));
				script_param.put("supplier_user_id", supplier_info.get("supplier_user_id"));
				script_param.put("key_cell_location", supplier_info.get("key_cell_location"));

				// objJs.put("user_id_destination", user_id);

				Map mapReturnData = new HashMap<>();

				mapReturnData.put("script", getScriptName());
				mapReturnData.put("script_param", script_param);

				objJs.put("data", mapReturnData);

				ResponseClient.sendResponseScriptUI(objJs);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "message_request_oxy_commit";
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.message_request_oxy_commit);

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.message_request_oxy_load_form);// "../draft/load_form_script/toicanbinhoxy";
	}

//	@Override
//	public Script getScript() {
//		MessageRequestOxyLoadFormUI joinOxySocialListenerUI = new MessageRequestOxyLoadFormUI();
//		return joinOxySocialListenerUI.getUIBuilder().getScript();
//
//	}

	@Override
	public String getScriptName() {
		// TODO Auto-generated method stub
		return MessageRequestOxyLoadFormUI.class.getName();
	}
}
