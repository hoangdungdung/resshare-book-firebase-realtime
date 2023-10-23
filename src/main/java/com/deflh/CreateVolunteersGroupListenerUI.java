package com.deflh;


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

public class CreateVolunteersGroupListenerUI implements IUIScript {



	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");
			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(LocationDynamicActivity.class, "LocationDynamicActivity");
					
					 
					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_backListener");

			btn_back.setOnClickListener(btn_backListener);

			// UIBuilder uIBuilder = new UIBuilder();

			Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btnSend");
			// txtTerm1 txtTerm2 txtTotal
 
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
			
			String string_post_collection = uIBuilder.convert(String.class, post_collection);
			

			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");
					

					// FireBaseReference.draft_core_flowchart_execute
				
				//	uIBuilder.postData(objMap, string_post_collection);
					String DRAFT_APPOINTMENT_ADD_NEW = "draft/covid19/create_volunteers_group/post_data";
					uIBuilder.postData(objMap, DRAFT_APPOINTMENT_ADD_NEW);

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
