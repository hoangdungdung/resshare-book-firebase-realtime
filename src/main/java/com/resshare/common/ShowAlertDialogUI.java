package com.resshare.common;
//com.resshare.common.ShowAlertDialogUI

import com.resshare.core.screen.DynamicFragmentRecyclerView;

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

public class ShowAlertDialogUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
		 
			DynamicFragmentRecyclerView screen = uIBuilder.<DynamicFragmentRecyclerView>createShadow(
					DynamicFragmentRecyclerView.class, "DynamicFragmentRecyclerView");
			screen.showAlertDialog("Không có sách bạn cần tìm");

		//	screen.onBackPressed();

		 

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
