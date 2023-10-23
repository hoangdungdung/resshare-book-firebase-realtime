package com.resshare.book.search;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;

import android.view.View;

public class RecyclerViewBookcaseUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

		
			Object book_data = uIBuilder.getScriptShadowParam("book_data");
			//Object post_collection_select_item = uIBuilder.getScriptShadowParam("post_collection_select_item");
			uIBuilder.setListFieldNameValue(book_data);

			// GridView simpleGridView = uIBuilder.<GridView>createShadow(GridView.class,
		
			android.widget.TextView btnActionBook=uIBuilder.createShadow(android.widget.TextView.class,"@+id/btnActionBook"  );
			
			ViewOnClickListener btnActionBookListener = new ViewOnClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					Object post_collection = uIBuilder.getScriptShadowParam("post_collection__change_state_flow");
					  
					Object book_items_type = uIBuilder.getScriptShadowParam("book_items_type");
					Object book_items_id = uIBuilder.getScriptShadowParam("book_items_id");
					Object book_id = uIBuilder.getScriptShadowParam("book_id");
					 
					 //	String spost_collection = uIBuilder.convert(String.class, post_collection);
						MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject" + book_items_type);
						objMap.setFieldValue("book_items_type", book_items_type);
						objMap.setFieldValue("book_items_id", book_items_id);
						objMap.setFieldValue("book_id", book_id);
						uIBuilder.postData(objMap, post_collection);
				}
			};
			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btnActionBookListener, "btnActionBookListener");
			btnActionBook.setOnClickListener(btnActionBookListener);
			
			
			DynamicFragmentRecyclerView screen = uIBuilder.<DynamicFragmentRecyclerView>createShadow(
					DynamicFragmentRecyclerView.class, "DynamicFragmentRecyclerView");

		//	screen.close();

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


			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
