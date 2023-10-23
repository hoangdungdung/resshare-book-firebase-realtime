package com.resshare.common;

import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;

import android.widget.TextView;

public class ShowMsgDashboardUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {
		UIBuilder uIBuilder = new UIBuilder();
		Object msg = uIBuilder.getScriptShadowParam("msg");

		TextView TextView = uIBuilder.<TextView>createShadow(TextView.class, "lbProduct");
		CharSequence text = uIBuilder.convert(String.class, msg);
		TextView.setText(text);
		return uIBuilder;
	}

}
