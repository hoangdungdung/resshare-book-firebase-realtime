package com.resshare.book.search;

import java.util.ArrayList;
import java.util.List;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;

import android.view.View;
import android.widget.ImageView;

public class LoadFormSearchingUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
			String spost_collection = uIBuilder.convert(String.class, post_collection);

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");
			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					DynamicFragmentRecyclerView screen = uIBuilder.<DynamicFragmentRecyclerView>createShadow(
							DynamicFragmentRecyclerView.class, "DynamicFragmentRecyclerView");

					screen.close();

				}
			};
			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_backListener");

			btn_back.setOnClickListener(btn_backListener);

			DynamicFragmentRecyclerView dynamicFragmentRecyclerView = uIBuilder
					.<DynamicFragmentRecyclerView>createShadow(DynamicFragmentRecyclerView.class,
							"DynamicFragmentRecyclerView");
			String spinner_name = "@+id/SpinnerSeachBook";
			List<String> list_data = new ArrayList<String>();
			list_data.add("book");
//			list_data.add("category");
//			list_data.add("author");
//			list_data.add("user");

			dynamicFragmentRecyclerView.setSpinnerData(spinner_name, list_data);
			String search_view_name = "@+id/btnSearchView";
			dynamicFragmentRecyclerView.setupSearchView(search_view_name, spost_collection);

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
