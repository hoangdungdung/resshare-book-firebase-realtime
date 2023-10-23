package com.resshare.book.addbook;


import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class LoadFormAddBookListener extends LoadFormOxyBaseListener {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "add_book";
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference("../load_form_script/add_book");
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA);
	}

	// @Override
	// public Script getScript() {
	// LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
	// return loadFormSupplierOxyUI.getUIBuilder().getScript();
	//
	// }
	public String getScriptName() {
		// TODO Auto-generated method stub
		return LoadFormAdBookUI.class.getName();
	}
}
