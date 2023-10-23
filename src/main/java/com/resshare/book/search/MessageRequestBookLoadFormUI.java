package com.resshare.book.search;

import com.resshare.core.screen.LocationDynamicActivity;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class MessageRequestBookLoadFormUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");
			
 
			Object book = uIBuilder.getScriptShadowParam("book");
			uIBuilder.setListFieldNameValue(book);
//			 
//			Object owner_id = uIBuilder.getScriptShadowParam("owner_id");
//			Object book_id = uIBuilder.getScriptShadowParam("book_id");
//			Object book_item_id = uIBuilder.getScriptShadowParam("book_item_id");
//
//			//Object key_cell_location = uIBuilder.getScriptShadowParam("key_cell_location");
//
//			TextView txtOwner_id = uIBuilder.<TextView>createShadow(TextView.class,
//					"txtOwner_id");
//			String owner_id1 = uIBuilder.convert(String.class, owner_id);
//			txtOwner_id.setText(owner_id1);
//
//			TextView txtBook_item_id = uIBuilder.<TextView>createShadow(TextView.class, "txtBook_item_id");
//			String book_item_id1 = uIBuilder.convert(String.class, book_item_id);
//			txtBook_item_id.setText(book_item_id1);
//
//			TextView txtBook_id = uIBuilder.<TextView>createShadow(TextView.class, "txtBook_id");
//
//			String book_id1 = uIBuilder.convert(String.class, book_id);
//			txtBook_id.setText(book_id1);

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");

			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_back_OnClicklistener");

			btn_back.setOnClickListener(btn_backListener);

			Button btnCancel = uIBuilder.<Button>createShadow(Button.class, "btnCancel");

			ViewOnClickListener btnCancelViewOnClickListener = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");

					uIBuilder.postData(objMap, post_collection);

					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btnCancelViewOnClickListener,
					"btnCancelOnClickListener");
			btnCancel.setOnClickListener(btnCancelViewOnClickListener);

			/// bntOk
			Button btnOk = uIBuilder.<Button>createShadow(Button.class, "btnOk");

			ViewOnClickListener btnOkViewOnClickListener = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");

					uIBuilder.postData(objMap, post_collection);

					LocationDynamicActivity screen = uIBuilder.<LocationDynamicActivity>createShadow(
							LocationDynamicActivity.class, "LocationDynamicActivity");

					screen.onBackPressed();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btnOkViewOnClickListener,
					"btnOkOnClickListener");
			btnOk.setOnClickListener(btnOkViewOnClickListener);

			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
