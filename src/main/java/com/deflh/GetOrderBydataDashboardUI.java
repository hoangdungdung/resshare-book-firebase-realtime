package com.deflh;

import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;
import com.resshare.widget.GridViewAdapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetOrderBydataDashboardUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
			// Object grid_view_layout_item =
			// uIBuilder.getScriptShadowParam("grid_view_layout_item");

			Object order_master_id = uIBuilder.getScriptShadowParam("order_master_id");

			TextView txtOrderMasterId = uIBuilder.<TextView>createShadow(TextView.class, "txtOrderMasterId");
			String strorder_master_id = uIBuilder.convert(String.class, order_master_id);
			txtOrderMasterId.setText(strorder_master_id);

			// buidButton(uIBuilder);

			// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
			// "simpleGridView");
			GridViewAdapter gridViewAdapter = uIBuilder.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"dashboardGridView.Adapter");

			// gridViewAdapter.setLayout(grid_view_layout_item);

			UIBuilder uIBuilderItem = new UIBuilder();
			TextView txtDescriptionItem = uIBuilderItem.<TextView>createShadow(TextView.class, "txtDescriptionItem");

			GridViewAdapter gridViewAdapterScript = uIBuilderItem.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"this");
			Object descriptionValue1 = gridViewAdapterScript.getSelectField("description");

			String descriptionValue = uIBuilderItem.convert(String.class, descriptionValue1);
			txtDescriptionItem.setText(descriptionValue);

			gridViewAdapter.setScript(uIBuilderItem.getScript());
			//
			// UIBuilder uIBuilderItemClick = new UIBuilder();
			// GridViewAdapter gridViewAdapterScriptItemClick = uIBuilderItemClick
			// .<GridViewAdapter>createShadow(GridViewAdapter.class,
			// "dashboardGridView.Adapter");
			// Object jsonPathFormObj =
			// gridViewAdapterScriptItemClick.getSelectField("layout_form");
			// String jsonPathForm = uIBuilderItemClick.convert(String.class,
			// jsonPathFormObj);
			//
			// Object jsonPathFlowChart =
			// gridViewAdapterScriptItemClick.getSelectField("flow_chart");
			// String jsonPathFormflow_chart = uIBuilderItemClick.convert(String.class,
			// jsonPathFlowChart);
			//
			// Object activity = gridViewAdapterScriptItemClick.getSelectField("fragment");
			// String str_activity = uIBuilderItemClick.convert(String.class, activity);
			//
			// // uIBuilderItemClick.openFormFlowchart(jsonPathForm,
			// jsonPathFormflow_chart);
			// uIBuilderItemClick.openFormFlowchartFragment(jsonPathForm,
			// jsonPathFormflow_chart, str_activity);
			//
			// // openFormFlowchart
			//
			// Script script = uIBuilderItemClick.getScript();
			//
			//
			// gridViewAdapter.setScriptItemClick(script);

			gridViewAdapter.setData(grid_view_data);

			// mapReturnData.put("script", uIBuilder.getScript());

			// uupdate user_app_recently
			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public UIBuilder buidButton(UIBuilder uIBuilder) {
		uIBuilder = addClickButton(uIBuilder, "btnSend");
		uIBuilder = addClickButton(uIBuilder, "btnSeach");
		uIBuilder = addClickButton(uIBuilder, "btnDateSeach");

		// Button btnSeach = uIBuilder.<Button>createShadow(Button.class,
		// "btnSeach");
		//
		// Object post_collection_seach =
		// uIBuilder.getScriptShadowParam("post_collection_seach");
		// ViewOnClickListener boiling_point_klispost_collection_seach = new
		// ViewOnClickListener() {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void onClick(View v) {
		//
		// MapObject objMapSech =
		// uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObjectSeach");
		// uIBuilder.postData(objMapSech, post_collection_seach);
		//
		// }
		// };
		//
		// uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klispost_collection_seach,
		// "OnClickListenerSeach");
		// btnSeach.setOnClickListener(boiling_point_klispost_collection_seach);
		return uIBuilder;
	}

	private UIBuilder addClickButton(UIBuilder uIBuilder, String btnName) {
		Button btnSend = uIBuilder.<Button>createShadow(Button.class, btnName);

		Object post_collection = uIBuilder.getScriptShadowParam("post_collection" + btnName);
		ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(View v) {

				MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject" + btnName);
				uIBuilder.postData(objMap, post_collection);

			}
		};

		uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener" + btnName);
		btnSend.setOnClickListener(boiling_point_klis);
		return uIBuilder;
	}

}
