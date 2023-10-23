package com.resshare.goibinhoxy.cobinhoxy;

import com.resshare.core.screen.XDynamicActivity;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;
import com.resshare.widget.GridViewAdapter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTransactionForSupplierUI implements IUIScript {

	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();
			EditText txtDateSeach = uIBuilder.<EditText>createShadow(EditText.class, "txtDateSeach");
			txtDateSeach.setText(DataUtil.getStringDay());

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");

			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					XDynamicActivity screen = uIBuilder.<XDynamicActivity>createShadow(XDynamicActivity.class,
							"Activity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_back_OnClicklistener");

			btn_back.setOnClickListener(btn_backListener);

			Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
			Object grid_view_layout_item = uIBuilder.getScriptShadowParam("grid_view_layout_item");

			// Object customer_address = uIBuilder.getScriptShadowParam("customer_address");
			// Object customer_phone_number =
			// uIBuilder.getScriptShadowParam("customer_phone_number");
			// Object customer_group_name = uIBuilder.getScriptShadowParam("customer_name");
			// Object order_master_id = uIBuilder.getScriptShadowParam("order_master_id");
			//
			// EditText txtCustomerName = uIBuilder.<EditText>createShadow(EditText.class,
			// "txtCustomerName");
			// String strcustomer_group_name = uIBuilder.convert(String.class,
			// customer_group_name);
			// txtCustomerName.setText(strcustomer_group_name);
			//
			// EditText txtCustomerPhoneNumber =
			// uIBuilder.<EditText>createShadow(EditText.class,
			// "txtCustomerPhoneNumber");
			// String strcustomer_phone_number = uIBuilder.convert(String.class,
			// customer_phone_number);
			// txtCustomerPhoneNumber.setText(strcustomer_phone_number);
			//
			//
			// EditText txtCustomerAddress =
			// uIBuilder.<EditText>createShadow(EditText.class,
			// "txtCustomerAddress");
			// String strcustomer_address = uIBuilder.convert(String.class,
			// customer_address);
			// txtCustomerAddress.setText(strcustomer_address);
			//
			//
			// TextView txtOrderMasterId = uIBuilder.<TextView>createShadow(TextView.class,
			// "txtOrderMasterId");
			// String strorder_master_id = uIBuilder.convert(String.class, order_master_id);
			// txtOrderMasterId.setText(strorder_master_id);
			//
			//
			// EditText txtDateSeach = uIBuilder.<EditText>createShadow(EditText.class,
			// "txtDateSeach");
			//
			// txtDateSeach.setText(DataUtil.getStringDay());
			//
			//
			//
			// buidButton(uIBuilder);
			//
			//

			// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
			// "simpleGridView");
			GridViewAdapter gridViewAdapter = uIBuilder.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"dashboardGridView.Adapter");

			gridViewAdapter.setLayout(grid_view_layout_item);

			UIBuilder uIBuilderItem = new UIBuilder();

			GridViewAdapter gridViewAdapterScript = uIBuilderItem.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"this");

			TextView txtCustomerPhoneNumber = uIBuilderItem.<TextView>createShadow(TextView.class,
					"txtCustomerPhoneNumber");
			Object customer_phone_number = gridViewAdapterScript.getSelectField("customer_phone_number");
			String customer_phone_number1 = uIBuilderItem.convert(String.class, customer_phone_number);
			txtCustomerPhoneNumber.setText(customer_phone_number1);

			TextView txtCustomerUserName = uIBuilderItem.<TextView>createShadow(TextView.class, "txtCustomerUserName");
			Object customer_user_name = gridViewAdapterScript.getSelectField("customer_user_name");
			String customer_user_name1 = uIBuilderItem.convert(String.class, customer_user_name);
			txtCustomerUserName.setText(customer_user_name1);

			TextView txtCustomerUserId = uIBuilderItem.<TextView>createShadow(TextView.class, "txtCustomerUserId");
			Object customer_user_id = gridViewAdapterScript.getSelectField("customer_user_id");
			String customer_user_id1 = uIBuilderItem.convert(String.class, customer_user_id);
			txtCustomerUserId.setText(customer_user_id1);

			TextView txtStatus = uIBuilderItem.<TextView>createShadow(TextView.class, "txtStatus");
			Object status = gridViewAdapterScript.getSelectField("status");
			String status1 = uIBuilderItem.convert(String.class, status);
			txtStatus.setText(status1);

			TextView txtorder_key = uIBuilderItem.<TextView>createShadow(TextView.class, "txtOrderKey");
			Object order_key = gridViewAdapterScript.getSelectField("order_key");
			String order_key1 = uIBuilderItem.convert(String.class, order_key);
			txtorder_key.setText(order_key1);

			Button btn_right = uIBuilderItem.<Button>createShadow(Button.class, "btnCall");

			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					uIBuilderItem.openDialContactPhone(txtCustomerPhoneNumber.getText());

				}
			};

			uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
			btn_right.setOnClickListener(boiling_point_klis);

			Button btnSendMessage = uIBuilderItem.<Button>createShadow(Button.class, "btnSendMessage");

			ViewOnClickListener btnSendMessagelis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					String str_Activity = "com.resshare.core.screen.DirectionDynamicActivity";
					String jsonPathFlowchart = "";
					String jsonPathForm = "../configuration/system_setting/layout/android/form/map_list_transaction_of_supplier";
					MapObject objMap = uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject");
					// MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class,
					// "mapObject");
					uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity, objMap);

					// uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity,
					// txtSupplierPhoneNumber.getText());

				}
			};

			uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(btnSendMessagelis, "OnClickListener2");
			btnSendMessage.setOnClickListener(btnSendMessagelis);

			gridViewAdapter.setScript(uIBuilderItem.getScript());

			gridViewAdapter.setData(grid_view_data);

			// mapReturnData.put("script", uIBuilder.getScript());

			// uupdate user_app_recently
			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// @Override
	// public UIBuilder getUIBuilder1() {}

}
