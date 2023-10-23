package com.resshare.goibinhoxy.canbinhoxy;

import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetListOxySupplierUI implements IUIScript {

 
	public UIBuilder getUIBuilder1() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
			Object grid_view_layout_item = uIBuilder.getScriptShadowParam("grid_view_layout_item");

//			Object customer_address = uIBuilder.getScriptShadowParam("customer_address");
//			Object customer_phone_number = uIBuilder.getScriptShadowParam("customer_phone_number");
//			Object customer_group_name = uIBuilder.getScriptShadowParam("customer_name");
//			Object order_master_id = uIBuilder.getScriptShadowParam("order_master_id");
//
//			EditText txtCustomerName = uIBuilder.<EditText>createShadow(EditText.class, "txtCustomerName");
//			String strcustomer_group_name = uIBuilder.convert(String.class, customer_group_name);
//			txtCustomerName.setText(strcustomer_group_name);
//
//			EditText txtCustomerPhoneNumber = uIBuilder.<EditText>createShadow(EditText.class,
//					"txtCustomerPhoneNumber");
//			String strcustomer_phone_number = uIBuilder.convert(String.class, customer_phone_number);
//			txtCustomerPhoneNumber.setText(strcustomer_phone_number);
//			
//			
//			EditText txtCustomerAddress = uIBuilder.<EditText>createShadow(EditText.class,
//					"txtCustomerAddress");
//			String strcustomer_address = uIBuilder.convert(String.class, customer_address);
//			txtCustomerAddress.setText(strcustomer_address);
//			
//			
//			TextView txtOrderMasterId = uIBuilder.<TextView>createShadow(TextView.class, "txtOrderMasterId");
//			String strorder_master_id = uIBuilder.convert(String.class, order_master_id);
//			txtOrderMasterId.setText(strorder_master_id);
//			
//			
//			EditText txtDateSeach = uIBuilder.<EditText>createShadow(EditText.class, "txtDateSeach");
//		
//			txtDateSeach.setText(DataUtil.getStringDay());
//		 
//			
//			
//			buidButton(uIBuilder);
//			
//			
			
			
			
			
			
			
			
			
			
			

			// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
			// "simpleGridView");
			GridViewAdapter gridViewAdapter = uIBuilder.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"dashboardGridViewCall.Adapter");

			gridViewAdapter.setLayout(grid_view_layout_item);

			UIBuilder uIBuilderItem = new UIBuilder();
			TextView txtDescriptionItem = uIBuilderItem.<TextView>createShadow(TextView.class, "txtDescriptionItem");

			GridViewAdapter gridViewAdapterScript = uIBuilderItem.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"this");
			Object descriptionValue1 = gridViewAdapterScript.getSelectField("description");
		

			String descriptionValue = uIBuilderItem.convert(String.class, descriptionValue1);
			txtDescriptionItem.setText(descriptionValue);
		

		
			
			
			
			 
			
			
//			Button btn_right = uIBuilderItem.<Button>createShadow(Button.class, "btnSend");
//			// txtTerm1 txtTerm2 txtTotal
// 
////			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
////			
////			String string_post_collection = uIBuilder.convert(String.class, post_collection);
////			
//
//			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
//				/**
//				 * 
//				 */
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				public void onClick(View v) {
//
//					UIBuilder uIBuilderItemClick = new UIBuilder();
//					GridViewAdapter gridViewAdapterScriptItemClick = uIBuilderItemClick
//							.<GridViewAdapter>createShadow(GridViewAdapter.class, "dashboardGridView.Adapter");
//					Object supplier_phone_number = gridViewAdapterScriptItemClick.getSelectField("supplier_phone_number");
//				 
//
//					//uIBuilderItemClick.openFormFlowchart(jsonPathForm, jsonPathFormflow_chart);
//					uIBuilderItemClick.openDialContactPhone(supplier_phone_number);
//
//				}
//			};
//
//			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
//			btn_right.setOnClickListener(boiling_point_klis);
//			
//			gridViewAdapter.setScript(uIBuilderItem.getScript());
			
//			hm.put("supplier_phone_number", dataSnapshot.getKey());
//			hm.put("supplier_user_id",
//					dataSnapshot.child("data/request_user_id")
//							.getValue(String.class));
			
			//openDialContactPhone
			
//			Object supplier_phone_number = gridViewAdapterScript.getSelectField("supplier_phone_number");
//			Object supplier_user_id = gridViewAdapterScript.getSelectField("supplier_user_id");
			
			//senmessage
		//	uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity, str_param_loadForm);
//
		
			
		//	Object supplier_phone_number = gridViewAdapterScript.getSelectField("supplier_phone_number");
		//	Object supplier_user_id = gridViewAdapterScript.getSelectField("supplier_user_id");
			
			
//			String jsonPathForm = uIBuilderItemClick.convert(String.class, jsonPathFormObj);
//
//			Object jsonPathFlowChart = gridViewAdapterScriptItemClick.getSelectField("flow_chart");
//			String jsonPathFormflow_chart = uIBuilderItemClick.convert(String.class, jsonPathFlowChart);
//
//			Object activity = gridViewAdapterScriptItemClick.getSelectField("fragment");
//			String str_activity = uIBuilderItemClick.convert(String.class, activity);
//
//			// uIBuilderItemClick.openFormFlowchart(jsonPathForm, jsonPathFormflow_chart);
 		 
//
 			
 			UIBuilder uIBuilderItemClick = new UIBuilder();
			GridViewAdapter gridViewAdapterScriptItemClick = uIBuilderItemClick
					.<GridViewAdapter>createShadow(GridViewAdapter.class, "dashboardGridViewCall.Adapter");
			Object supplier_phone_number = gridViewAdapterScriptItemClick.getSelectField("supplier_phone_number");
		 

			//uIBuilderItemClick.openFormFlowchart(jsonPathForm, jsonPathFormflow_chart);
			uIBuilderItemClick.openDialContactPhone(supplier_phone_number);

			// openFormFlowchart

			Script script = uIBuilderItemClick.getScript();
			System.out.println(script.getList_command());

			gridViewAdapter.setScriptItemClick(script);
 			
 			
 			

			gridViewAdapter.setData(grid_view_data);

			// mapReturnData.put("script", uIBuilder.getScript());

			// uupdate user_app_recently
			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	@Override
	public UIBuilder getUIBuilder() {
	try {
		UIBuilder uIBuilder = new UIBuilder();

		Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
		Object grid_view_layout_item = uIBuilder.getScriptShadowParam("grid_view_layout_item");

		// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
		// "simpleGridView");
		GridViewAdapter gridViewAdapter = uIBuilder.<GridViewAdapter>createShadow(GridViewAdapter.class,
				"dashboardGridViewCall.Adapter");

		gridViewAdapter.setLayout(grid_view_layout_item);

		UIBuilder uIBuilderItem = new UIBuilder();
		
		
//		txtDistance
//		txtSupplierPhoneNumber
//		txtSupplierUserName
//		btnSendMessage

		GridViewAdapter gridViewAdapterScript = uIBuilderItem.<GridViewAdapter>createShadow(GridViewAdapter.class,
				"this");
	 
		TextView txtSupplierPhoneNumber = uIBuilderItem.<TextView>createShadow(TextView.class, "txtSupplierPhoneNumber");
		Object supplier_phone_number = gridViewAdapterScript.getSelectField("supplier_phone_number");
		String supplier_phone_number1 = uIBuilderItem.convert(String.class, supplier_phone_number);
		txtSupplierPhoneNumber.setText(supplier_phone_number1);
		
		
		TextView txtDistance = uIBuilderItem.<TextView>createShadow(TextView.class, "txtDistance");
		Object distance = gridViewAdapterScript.getSelectField("distance");
		String distance1 = uIBuilderItem.convert(String.class, distance);
		txtDistance.setText(distance1);
		
		
		TextView txtSupplierUserName = uIBuilderItem.<TextView>createShadow(TextView.class, "txtSupplierUserName");
		Object supplier_user_name = gridViewAdapterScript.getSelectField("supplier_user_name");
		String supplier_user_name1 = uIBuilderItem.convert(String.class, supplier_user_name);
		txtSupplierUserName.setText(supplier_user_name1);
		
		
		TextView txtSupplierUserId = uIBuilderItem.<TextView>createShadow(TextView.class, "txtSupplierUserId");
		Object supplier_user_id = gridViewAdapterScript.getSelectField("supplier_user_id");
		String supplier_user_id1 = uIBuilderItem.convert(String.class, supplier_user_id);
		txtSupplierUserId.setText(supplier_user_id1);
		
		
		TextView txtKeyCellLocation = uIBuilderItem.<TextView>createShadow(TextView.class, "txtKeyCellLocation");
		Object key_cell_location = gridViewAdapterScript.getSelectField("key_cell_location");
		String key_cell_location1 = uIBuilderItem.convert(String.class, key_cell_location);
		txtKeyCellLocation.setText(key_cell_location1);
		
		
		
		
		
		
		Button btnSendMessage = uIBuilderItem.<Button>createShadow(Button.class, "btnSendMessage");

		ViewOnClickListener btnSendMessagelis = new ViewOnClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(View v) {

				String str_Activity="com.resshare.core.screen.XDynamicActivity";
				String jsonPathFlowchart="";
				String jsonPathForm="../configuration/system_setting/layout/android/form/message_request_oxy";
				MapObject objMap = uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject");
				uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity,objMap);
				
				//uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity, txtSupplierPhoneNumber.getText());

			}
		};

		uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(btnSendMessagelis, "OnClickListener2");
		btnSendMessage.setOnClickListener(btnSendMessagelis);
		
		
		
		Button btn_right = uIBuilderItem.<Button>createShadow(Button.class, "btnCall");

		ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(View v) {

				uIBuilderItem.openDialContactPhone(txtSupplierPhoneNumber.getText());

			}
		};

		uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
		btn_right.setOnClickListener(boiling_point_klis);

		gridViewAdapter.setScript(uIBuilderItem.getScript());

		gridViewAdapter.setData(grid_view_data);

		return uIBuilder;

	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;

	}

}
