package com.resshare.book.transaction;


import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookSupplierAcceptOrderMsgDashboardUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
			Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btnSend");
			Object hash_field_value = uIBuilder.getScriptShadowParam("hash_field_value");
			uIBuilder.setListFieldNameValue(hash_field_value);
			
//			Object group_name = uIBuilder.getScriptShadowParam("group_name");
//			TextView lbDescription = uIBuilder.<TextView>createShadow(TextView.class, "lbGroupName");
//			String convert = uIBuilder.convert(String.class, group_name);
//			lbDescription.setText(convert);
//
//			Object group_key = uIBuilder.getScriptShadowParam("group_key");
//			TextView lbGroupKey = uIBuilder.<TextView>createShadow(TextView.class, "lbGroupKey");
//			String convertGroupKey = uIBuilder.convert(String.class, group_key);
//			lbGroupKey.setText(convertGroupKey);
//
//			Object request_user_id = uIBuilder.getScriptShadowParam("request_user_id");
//			TextView lbrequest_user_id = uIBuilder.<TextView>createShadow(TextView.class, "lbrequest_user_id");
//			String request_user_idcv = uIBuilder.convert(String.class, request_user_id);
//			lbrequest_user_id.setText(request_user_idcv);
//
//			Object request_phone_number = uIBuilder.getScriptShadowParam("request_phone_number");
//			TextView lbrequest_phone_number = uIBuilder.<TextView>createShadow(TextView.class,
//					"lbrequest_phone_number");
//			String request_phone_numbercv = uIBuilder.convert(String.class, request_phone_number);
//			lbrequest_phone_number.setText(request_phone_numbercv);
//
//			Object request_user_name = uIBuilder.getScriptShadowParam("request_user_name");
//			TextView lbrequest_user_name = uIBuilder.<TextView>createShadow(TextView.class, "lbrequest_user_name");
//			String request_user_namecv = uIBuilder.convert(String.class, request_user_name);
//			lbrequest_user_name.setText(request_user_namecv);
//
//			Object description = uIBuilder.getScriptShadowParam("description");
//			TextView lbdescription = uIBuilder.<TextView>createShadow(TextView.class, "lbDescription");
//			String descriptioncv = uIBuilder.convert(String.class, description);
//			lbdescription.setText(descriptioncv);
			

			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");

					uIBuilder.postData(objMap, post_collection);
					Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btnSend");
					btn_right.setVisibility(View.GONE);
					TextView lbdescription = uIBuilder.<TextView>createShadow(TextView.class, "lbDescription");
					lbdescription.setVisibility(View.GONE);

					TextView lbDescriptionChange = uIBuilder.<TextView>createShadow(TextView.class,
							"lbDescriptionChange");

					Object obj_description_change = uIBuilder.getScriptShadowParam("description_change");

					String description_change = uIBuilder.convert(String.class, obj_description_change);

					lbDescriptionChange.setText(description_change);

					// uIBuilder.dashboardRemoveCurrentMessage();
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
