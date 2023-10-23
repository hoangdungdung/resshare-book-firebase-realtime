package com.resshare.book.search;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.model.MapObject;
import com.resshare.framework.model.Script;
import com.resshare.widget.GridViewAdapter;
import com.resshare.widget.RecyclerAdapter;

public class SearchBookViewItemUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {
			UIBuilder uIBuilder = new UIBuilder();

			Object item_data = uIBuilder.getScriptShadowParam("item_data");
		 
			RecyclerAdapter recyclerAdapter = uIBuilder.<RecyclerAdapter>createShadow(RecyclerAdapter.class,
					"recycler_item.Adapter");



			
			recyclerAdapter.addData(item_data);
			 

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
