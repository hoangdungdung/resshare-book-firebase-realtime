package com.resshare.book.bookcase;

import com.resshare.core.screen.DynamicFragmentRecyclerView;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadFormMyBookcaseUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
		
//			MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject" + "book_items");
//			objMap.setFieldValue("book_items_type", "book_items");
//			uIBuilder.postData(objMap, post_collection);
			DynamicFragmentRecyclerView screen = uIBuilder.<DynamicFragmentRecyclerView>createShadow(
					DynamicFragmentRecyclerView.class, "DynamicFragmentRecyclerView");
			postItems(uIBuilder, "book_items"  );
		 	clickBtn(uIBuilder, "book_items",screen);
		 	clickBtn(uIBuilder, "book_items_loan",screen);
		 	clickBtn (uIBuilder, "book_items_borrow",screen);
		 	
		 	clickBtn (uIBuilder, "book_items_purchase",screen);
		 	clickBtn (uIBuilder, "book_items_sell",screen);

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");
			ViewOnClickListener btn_backListener = new ViewOnClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					
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

	private void clickBtn(UIBuilder uIBuilder, String book_items ,DynamicFragmentRecyclerView screen ) {
		String nameControl = "@+id/" + book_items;
		TextView myBook = uIBuilder.<TextView>createShadow(TextView.class, nameControl);
		ViewOnClickListener btn_book_items = new ViewOnClickListener() {
			private static final long serialVersionUID = 1L;
			
//			clickBtn(uIBuilder, "book_items",screen);
//		 	clickBtn(uIBuilder, "book_items_loan",screen);
//		 	clickBtn (uIBuilder, "book_items_borrow",screen);
			
//		 	clickBtn (uIBuilder, "book_items_purchase",screen);
//		 	clickBtn (uIBuilder, "book_items_sell",screen);

			@Override
			public void onClick(View v) {
				uIBuilder.applyProperty("@+id/" + "book_items","background","@color/white");
				uIBuilder.applyProperty("@+id/" + "book_items_loan","background","@color/white");
				uIBuilder.applyProperty("@+id/" + "book_items_borrow","background","@color/white");
				uIBuilder.applyProperty("@+id/" + "book_items_purchase","background","@color/white");
				uIBuilder.applyProperty("@+id/" + "book_items_sell","background","@color/white");
				 
				screen.resetDashboard();
				uIBuilder.applyProperty(nameControl,"background","@color/app_color_grey");
				 
				postItems(uIBuilder, book_items );
			}

			
		};
		uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_book_items, "btn_" + book_items);
		myBook.setOnClickListener(btn_book_items);
	}

}
