package com.resshare.goibinhoxy.cobinhoxy;

//com.resshare.covid19.uiscript.CreateVolunteersGroupListenerUI
//com.deflh.GetFlowchartUI


import com.resshare.core.screen.LocationDynamicActivity;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class LoadFormSupplierOxyUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");

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

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_backListener");

			btn_back.setOnClickListener(btn_backListener);

			// UIBuilder uIBuilder = new UIBuilder();

			Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btnSend");


			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
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

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
			btn_right.setOnClickListener(boiling_point_klis);

			LocationDynamicActivity screenLocationDynamicActivity = uIBuilder
					.<LocationDynamicActivity>createShadow(LocationDynamicActivity.class, "LocationDynamicActivity");

			screenLocationDynamicActivity.addControlViewTextAddress("txtAddress");
			screenLocationDynamicActivity.addControlViewTextAddress("txtAddressOriginal");

			screenLocationDynamicActivity.addControlViewLocationAddress("txtLocation");
			screenLocationDynamicActivity.viewAddress();

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
