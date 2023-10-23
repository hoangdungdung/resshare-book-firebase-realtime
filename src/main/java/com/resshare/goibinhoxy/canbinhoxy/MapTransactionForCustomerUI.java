package com.resshare.goibinhoxy.canbinhoxy;

import com.resshare.core.screen.DirectionDynamicActivity;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapTransactionForCustomerUI implements IUIScript {

	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object array_list_polyline = uIBuilder.getScriptShadowParam("array_list_polyline");
			Object order_key = uIBuilder.getScriptShadowParam("order_key");
			DirectionDynamicActivity screenLocationDynamicActivity = uIBuilder
					.<DirectionDynamicActivity>createShadow(DirectionDynamicActivity.class, "DirectionDynamicActivity");
			
			
			screenLocationDynamicActivity.addControlViewTextAddress("txtAddress");
			screenLocationDynamicActivity.addControlViewTextAddress("txtAddressOriginal");

			screenLocationDynamicActivity.addControlViewLocationAddress("txtLocation");
			screenLocationDynamicActivity.viewAddress();
			screenLocationDynamicActivity.setCollectionLocationChanged("../customer/location_changed");
			screenLocationDynamicActivity.setCollectionLocationOnStop("../customer/location_on_stop");
			

			screenLocationDynamicActivity.addPolyline(array_list_polyline);
			TextView txtOrderKey = uIBuilder.<TextView>createShadow(TextView.class, "txtOrderKey");
			String text =uIBuilder.convert(String.class, order_key) ;
			txtOrderKey.setText(text  );
			
			Object location = uIBuilder.getScriptShadowParam("location");
			Object user_id1 = uIBuilder.getScriptShadowParam("user_id");
			Object sVehicleType1 = uIBuilder.getScriptShadowParam("vehicle_type");
			
			
			String position = uIBuilder.convert(String.class, location);
			String sVehicleType = uIBuilder.convert(String.class, sVehicleType1);
			String user_id = uIBuilder.convert(String.class, user_id1);
		
			

			screenLocationDynamicActivity.addPolyline(array_list_polyline);
			screenLocationDynamicActivity.drawVehicle(sVehicleType, position, user_id);

			// mapReturnData.put("script", uIBuilder.getScript());

			// uupdate user_app_recently
			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");

			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					DirectionDynamicActivity screen = uIBuilder.<DirectionDynamicActivity>createShadow(
							DirectionDynamicActivity.class, "DirectionDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_back_OnClicklistener");

			btn_back.setOnClickListener(btn_backListener);
			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
