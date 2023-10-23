package com.resshare.book.bookcase;


import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class LoadFormMyBoocaseListener extends LoadFormOxyBaseListener {

	 
	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../load_form_script/my_bookcase");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		  return  ResFirebaseReference.getInputPathReference(RefFireBaseBook.my_bookcase); 
		//"../requets/searching");
	}

	// @Override
	// public Script getScript() {
	// LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
	// return loadFormSupplierOxyUI.getUIBuilder().getScript();
	//
	// }
	public String getScriptName() {
		// TODO Auto-generated method stub
		return LoadFormMyBookcaseUI.class.getName();
	}
	
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
//				LoadFormMyBookcaseUI loadFormMyBookcaseUI= new LoadFormMyBookcaseUI();
//				loadFormMyBookcaseUI.getUIBuilder().getScript();

				mapReturnData.put("script", getScriptName());
				mapReturnData.put("script_param", script_param);

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
}
