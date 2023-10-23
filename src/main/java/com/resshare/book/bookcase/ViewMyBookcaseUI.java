package com.resshare.book.bookcase;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewMyBookcaseUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
		
//			MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject" + "book_items");
//			objMap.setFieldValue("book_items_type", "book_items");
//			uIBuilder.postData(objMap, post_collection);
			postItems(uIBuilder, "book_items"  );
		 	clickBtn(uIBuilder, "book_items");
		 	clickBtn(uIBuilder, "book_items_loan");
		 	clickBtn (uIBuilder, "book_items_borrow");
		 	clickBtn (uIBuilder, "book_items_purchase");
		 	clickBtn (uIBuilder, "book_items_sell");

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");
			ViewOnClickListener btn_backListener = new ViewOnClickListener() {
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

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	 
	private void postItems(UIBuilder uIBuilder, String book_items ) {
		Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
	 //	String spost_collection = uIBuilder.convert(String.class, post_collection);
		MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject" + book_items);
		objMap.setFieldValue("book_items_type", book_items);
		uIBuilder.postData(objMap, post_collection);
	}

	private void clickBtn(UIBuilder uIBuilder, String book_items  ) {
		TextView myBook = uIBuilder.<TextView>createShadow(TextView.class, "@+id/" + book_items);
		ViewOnClickListener btn_book_items = new ViewOnClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(View v) {

				postItems(uIBuilder, book_items );
			}

			
		};
		uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_book_items, "btn_" + book_items);
		myBook.setOnClickListener(btn_book_items);
	}

}
