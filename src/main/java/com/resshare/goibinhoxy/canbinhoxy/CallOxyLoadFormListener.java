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

public class CallOxyLoadFormListener extends LoadFormOxyBaseListener {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "call_oxy_post_data";
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.call_oxy_post_data);

	}

	@Override
	public String getReferenceName() {
		String collection = ResFirebaseReference.getInputPathReference(FirebaseRefOxy.toicanbinhoxy);// "../draft/load_form_script/toicanbinhoxy";
		// TODO Auto-generated method stub
		System.out.println("collection:" + String.valueOf(collection));
		return collection;
	}

//	@Override
//	public Script getScript() {
//		CallOxyLoadFormUI joinOxySocialListenerUI = new CallOxyLoadFormUI();
//		return joinOxySocialListenerUI.getUIBuilder().getScript();
//
//	}

	@Override
	public String getScriptName() {
		// TODO Auto-generated method stub
		return CallOxyLoadFormUI.class.getName();
	}
	
	 
 
}
