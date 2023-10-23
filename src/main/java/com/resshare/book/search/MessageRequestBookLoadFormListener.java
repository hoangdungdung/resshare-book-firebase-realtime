package com.resshare.book.search;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class MessageRequestBookLoadFormListener extends LoadFormOxyBaseListener {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);

				 Map supplier_info =  DataUtil.ConvertDataSnapshotToMap( (DataSnapshot) snapshot1.child("parameter"));
				
//				objMap.setFieldValue("owner_name", owner_name);
//				objMap.setFieldValue("sale_price", sale_price);
//				objMap.setFieldValue("book_id", book_id);
//				objMap.setFieldValue("book_item_id", book_item_id);
//				objMap.setFieldValue("renter_avatar", renter_avatar);
//				objMap.setFieldValue("free_loan", free_loan);
//				objMap.setFieldValue("renter_name", renter_name);
//				objMap.setFieldValue("renter_id", renter_id);
//				objMap.setFieldValue("owner_id", owner_id);

				HashMap script_param = new HashMap<>();
				Object collection = getReferenceNamePostData();
				// "../draft/covid19/create_volunteers_group/post_data";
				script_param.put("post_collection", collection);
				HashMap script_paramInfor = new HashMap<>();
				script_paramInfor.put("owner_id", supplier_info.get("owner_id"));
				script_paramInfor.put("book_id", supplier_info.get("book_id"));
				script_paramInfor.put("book_item_id", supplier_info.get("book_item_id"));
				script_paramInfor.put("owner_name", supplier_info.get("owner_name"));
				script_paramInfor.put("book_name", supplier_info.get("book_name"));
				script_paramInfor.put("free_loan", supplier_info.get("free_loan"));
				 
				script_param.put("book", script_paramInfor);

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
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_INPUT_post_collection_select_item);
				//ResFirebaseReference.getInputPathReference(FirebaseRefOxy.message_request_oxy_commit);

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.message_request_book_load_form);// "../draft/load_form_script/toicanbinhoxy";
	}

	// @Override
	// public Script getScript() {
	// MessageRequestOxyLoadFormUI joinOxySocialListenerUI = new
	// MessageRequestOxyLoadFormUI();
	// return joinOxySocialListenerUI.getUIBuilder().getScript();
	//
	// }

	@Override
	public String getScriptName() {
		// TODO Auto-generated method stub
		return MessageRequestBookLoadFormUI.class.getName();
	}
}
