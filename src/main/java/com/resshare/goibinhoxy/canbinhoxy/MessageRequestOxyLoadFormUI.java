package com.resshare.goibinhoxy.canbinhoxy;

import com.resshare.core.screen.LocationDynamicActivity;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageRequestOxyLoadFormUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");

			Object supplier_phone_number = uIBuilder.getScriptShadowParam("supplier_phone_number");
			Object supplier_user_id = uIBuilder.getScriptShadowParam("supplier_user_id");

			Object key_cell_location = uIBuilder.getScriptShadowParam("key_cell_location");

			TextView txtSupplierPhoneNumber = uIBuilder.<TextView>createShadow(TextView.class,
					"txtSupplierPhoneNumber");
			String supplier_phone_number1 = uIBuilder.convert(String.class, supplier_phone_number);
			txtSupplierPhoneNumber.setText(supplier_phone_number1);

			TextView txtSupplierUserId = uIBuilder.<TextView>createShadow(TextView.class, "txtSupplierUserId");
			String supplier_user_id1 = uIBuilder.convert(String.class, supplier_user_id);
			txtSupplierUserId.setText(supplier_user_id1);

			TextView txtKeyCellLocation = uIBuilder.<TextView>createShadow(TextView.class, "txtKeyCellLocation");

			String key_cell_location1 = uIBuilder.convert(String.class, key_cell_location);
			txtKeyCellLocation.setText(key_cell_location1);

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");

			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_back_OnClicklistener");

			btn_back.setOnClickListener(btn_backListener);

			Button btnCancel = uIBuilder.<Button>createShadow(Button.class, "btnCancel");

			ViewOnClickListener btnCancelViewOnClickListener = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");

					uIBuilder.postData(objMap, post_collection);

					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btnCancelViewOnClickListener,
					"btnCancelOnClickListener");
			btnCancel.setOnClickListener(btnCancelViewOnClickListener);

			/// bntOk
			Button btnOk = uIBuilder.<Button>createShadow(Button.class, "btnOk");

			ViewOnClickListener btnOkViewOnClickListener = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");

					uIBuilder.postData(objMap, post_collection);

					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btnOkViewOnClickListener,
					"btnOkOnClickListener");
			btnOk.setOnClickListener(btnOkViewOnClickListener);

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
