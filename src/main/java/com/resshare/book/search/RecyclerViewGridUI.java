package com.resshare.book.search;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;
import com.resshare.widget.RecyclerAdapter;

public class RecyclerViewGridUI implements IUIScript {

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
					"recyclerView.Adapter");



			
			recyclerAdapter.setLayout(grid_view_layout_item);
			recyclerAdapter.setListData(list_data); 

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
