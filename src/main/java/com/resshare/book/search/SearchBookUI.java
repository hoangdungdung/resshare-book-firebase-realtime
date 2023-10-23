package com.resshare.book.search;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;
import com.resshare.widget.RecyclerAdapter;

import android.view.View;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchBookUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object list_data = uIBuilder.getScriptShadowParam("grid_view_data");
			Object grid_view_layout_item = uIBuilder.getScriptShadowParam("grid_view_layout_item");
			Object book_data = uIBuilder.getScriptShadowParam("book_data");
			Object post_collection_select_item = uIBuilder.getScriptShadowParam("post_collection_select_item");
			uIBuilder.setListFieldNameValue(book_data);

			RecyclerAdapter recyclerAdapter = uIBuilder.<RecyclerAdapter>createShadow(RecyclerAdapter.class,
					"recycler_item.Adapter");



			
			recyclerAdapter.setLayout(grid_view_layout_item);
			
			//
			UIBuilder uIBuilderItem = new UIBuilder();
			// @+id/image 
			  CircleImageView circleImageView1 = uIBuilderItem.createShadow( CircleImageView.class, "@+id/image");
			  
			   
			  
				ViewOnClickListener btn_backListener = new ViewOnClickListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(View v) {
						MapObject objMap = uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject1");
						 

						String str_Activity = "com.resshare.core.screen.XDynamicActivity";
						String jsonPathFlowchart = "";
						String jsonPathForm = "../configuration/system_setting/layout/android/form/message_request_book";
						// MapObject objMap =
						// uIBuilderItem.createListFieldNameValueShadow(MapObject.class, "mapObject");
						uIBuilderItem.openActivity(jsonPathForm, jsonPathFlowchart, str_Activity, objMap);

					}
				};
				uIBuilderItem.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_backListener");

				circleImageView1.setOnClickListener(btn_backListener);
				
				recyclerAdapter.setScript(uIBuilderItem.getScript());

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
