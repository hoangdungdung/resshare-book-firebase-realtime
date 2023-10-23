package com.resshare.goibinhoxy.canbinhoxy;

import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomerReceivedNotifyMsgDashboardUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
	 
 
			Object hash_field_value = uIBuilder.getScriptShadowParam("hash_field_value");
			uIBuilder.setListFieldNameValue(hash_field_value);
			
 
			
 

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
