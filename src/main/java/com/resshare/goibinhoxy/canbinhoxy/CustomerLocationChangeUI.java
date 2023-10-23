package com.resshare.goibinhoxy.canbinhoxy;

import com.resshare.core.screen.DirectionDynamicActivity;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;

public class CustomerLocationChangeUI implements IUIScript {

	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object location = uIBuilder.getScriptShadowParam("location");
			Object user_id1 = uIBuilder.getScriptShadowParam("user_id");
			Object sVehicleType1 = uIBuilder.getScriptShadowParam("vehicle_type");
			
			
		

			DirectionDynamicActivity screenLocationDynamicActivity = uIBuilder
					.<DirectionDynamicActivity>createShadow(DirectionDynamicActivity.class, "DirectionDynamicActivity");

			String position = uIBuilder.convert(String.class, location);
			String sVehicleType = uIBuilder.convert(String.class, sVehicleType1);
			String user_id = uIBuilder.convert(String.class, user_id1);
			screenLocationDynamicActivity.drawVehicle(sVehicleType, position, user_id);

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
