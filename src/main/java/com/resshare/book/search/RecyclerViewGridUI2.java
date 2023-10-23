package com.resshare.book.search;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;

public class RecyclerViewGridUI2 implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object grid_view_data = uIBuilder.getScriptShadowParam("grid_view_data");
			Object grid_view_layout_item = uIBuilder.getScriptShadowParam("grid_view_layout_item");
			Object book_data = uIBuilder.getScriptShadowParam("book_data");
			Object post_collection_select_item = uIBuilder.getScriptShadowParam("post_collection_select_item");
			uIBuilder.setListFieldNameValue(book_data);

			// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
			// "simpleGridView");
			GridViewAdapter gridViewAdapter = uIBuilder.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"dashboardGridView.Adapter");

			gridViewAdapter.setLayout(grid_view_layout_item);

			UIBuilder uIBuilderItem = new UIBuilder();
			// TextView txtDescriptionItem =
			// uIBuilderItem.<TextView>createShadow(TextView.class, "txtDescriptionItem");

			GridViewAdapter gridViewAdapterScript = uIBuilderItem.<GridViewAdapter>createShadow(GridViewAdapter.class,
					"this");
			// Object descriptionValue1 =
			// gridViewAdapterScript.getSelectField("description");
			// Object icon = gridViewAdapterScript.getSelectField("icon");

			Object ItemData = gridViewAdapterScript.getItemData();
			uIBuilderItem.setListFieldNameValue(ItemData);

			// String descriptionValue = uIBuilderItem.convert(String.class,
			// descriptionValue1);
			// txtDescriptionItem.setText(descriptionValue);
			// String stringUrl = uIBuilderItem.convert(String.class, icon);
			// uIBuilderItem.showImageView("iconItem", stringUrl);

			gridViewAdapter.setScript(uIBuilderItem.getScript());

			UIBuilder uIBuilderItemClick = new UIBuilder();
			GridViewAdapter gridViewAdapterScriptItemClick = uIBuilderItemClick
					.<GridViewAdapter>createShadow(GridViewAdapter.class, "dashboardGridView.Adapter");
			//

			// uIBuilderItemClick.postListFieldValue("../select_book");

			Object owner_name = gridViewAdapterScriptItemClick.getSelectField("owner_name");
			Object sale_price = gridViewAdapterScriptItemClick.getSelectField("sale_price");
			Object owner_id = gridViewAdapterScriptItemClick.getSelectField("owner_id");
			Object renter_id = gridViewAdapterScriptItemClick.getSelectField("renter_id");
			Object renter_name = gridViewAdapterScriptItemClick.getSelectField("renter_name");
			Object free_loan = gridViewAdapterScriptItemClick.getSelectField("free_loan");
			Object renter_avatar = gridViewAdapterScriptItemClick.getSelectField("renter_avatar");
			Object book_item_id = gridViewAdapterScriptItemClick.getSelectField("book_item_id");
			Object book_id = gridViewAdapterScriptItemClick.getSelectField("book_id");

			MapObject objMap = uIBuilderItemClick.createListFieldNameValueShadow(MapObject.class, "mapObject1");
			objMap.setFieldValue("owner_name", owner_name);
			objMap.setFieldValue("sale_price", sale_price);
			objMap.setFieldValue("book_id", book_id);
			objMap.setFieldValue("book_item_id", book_item_id);
			objMap.setFieldValue("renter_avatar", renter_avatar);
			objMap.setFieldValue("free_loan", free_loan);
			objMap.setFieldValue("renter_name", renter_name);
			objMap.setFieldValue("renter_id", renter_id);
			objMap.setFieldValue("owner_id", owner_id);

			String str_Activity = "com.resshare.core.screen.XDynamicActivity";
			String jsonPathFlowchart = "";
			String jsonPathForm = "../configuration/system_setting/layout/android/form/message_request_book";
			// MapObject objMap =
			// uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject");
			uIBuilderItemClick.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity, objMap);

			// uIBuilderItemClick.postData(objMap, "../input/select/book");

			// Button btnSendMessage = uIBuilderItem.<Button>createShadow(Button.class,
			// "btnSendMessage");
			//
			// ViewOnClickListener btnSendMessagelis = new ViewOnClickListener() {
			// /**
			// *
			// */
			// private static final long serialVersionUID = 1L;
			//
			// @Override
			// public void onClick(View v) {
			//
			// String str_Activity="com.resshare.core.screen.XDynamicActivity";
			// String jsonPathFlowchart="";
			// String
			// jsonPathForm="../configuration/system_setting/layout/android/form/message_request_oxy";
			// MapObject objMap =
			// uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject");
			// uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart,
			// str_Activity,objMap);
			//
			// //uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity,
			// txtSupplierPhoneNumber.getText());
			//
			// }
			// };
			//
			// uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(btnSendMessagelis,
			// "OnClickListener2");
			// btnSendMessage.setOnClickListener(btnSendMessagelis);

//			DynamicFragmentRecyclerView screen = uIBuilder.<DynamicFragmentRecyclerView>createShadow(
//					DynamicFragmentRecyclerView.class, "DynamicFragmentRecyclerView");
//
//			screen.close();

			// gridViewAdapterScriptItemClick.set

			// String jsonPathForm = uIBuilderItemClick.convert(String.class,
			// jsonPathFormObj);
			// Object jsonPathFlowChart =
			// gridViewAdapterScriptItemClick.getSelectField("flow_chart");
			// String jsonPathFormflow_chart = uIBuilderItemClick.convert(String.class,
			// jsonPathFlowChart);
			// Object activity = gridViewAdapterScriptItemClick.getSelectField("fragment");
			// String str_activity = uIBuilderItemClick.convert(String.class, activity);
			// uIBuilderItemClick.openFormFlowchartFragment(jsonPathForm,
			// jsonPathFormflow_chart,str_activity);

			// openFormFlowchart

			Script script = uIBuilderItemClick.getScript();
			// System.out.println(script.getList_command());

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

}
