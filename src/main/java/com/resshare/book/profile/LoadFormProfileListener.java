package com.resshare.book.profile;

import com.resshare.book.LoadFormProfileOxyUI;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.model.Script;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class LoadFormProfileListener extends LoadFormOxyBaseListener {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "profile";
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../load_form_script/toicobinhoxy");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference(FirebaseRefOxy.provide_oxy_post_data);
	}

//	@Override
//	public Script getScript() {
//		LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
//		return loadFormSupplierOxyUI.getUIBuilder().getScript();
//
//	}
	public String getScriptName() {
		// TODO Auto-generated method stub
		return LoadFormProfileOxyUI.class.getName();
	}
}
