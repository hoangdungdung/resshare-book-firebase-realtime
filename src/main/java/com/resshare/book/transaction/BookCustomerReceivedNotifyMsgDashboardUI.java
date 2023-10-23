package com.resshare.book.transaction;

import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookCustomerReceivedNotifyMsgDashboardUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object hash_field_value = uIBuilder.getScriptShadowParam("hash_field_value");
			uIBuilder.setListFieldNameValue(hash_field_value);
		
			TextView txtSupplierPhoneNumber = uIBuilder.<TextView>createShadow(TextView.class,
					"lbsupplier_phone_number");
			Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btnCall");

			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					uIBuilder.openDialContactPhone(txtSupplierPhoneNumber.getText());
				}
			};
			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
			btn_right.setOnClickListener(boiling_point_klis);
			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
