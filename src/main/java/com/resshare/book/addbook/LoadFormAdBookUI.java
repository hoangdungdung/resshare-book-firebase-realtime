package com.resshare.book.addbook;

import com.resshare.core.screen.DynamicFragment;
import com.resshare.framework.core.service.IUIScript;
import com.resshare.framework.core.service.UIBuilder;
import com.resshare.framework.core.service.ViewOnClickListener;
import com.resshare.framework.model.MapObject;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoadFormAdBookUI implements IUIScript {

	@Override
	public UIBuilder getUIBuilder() {

		try {

			UIBuilder uIBuilder = new UIBuilder();
			Object post_collection = uIBuilder.getScriptShadowParam("post_collection");

			ImageView btn_back = uIBuilder.<ImageView>createShadow(ImageView.class, "btn_back");
			ViewOnClickListener btn_backListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					DynamicFragment screen = uIBuilder.<DynamicFragment>createShadow(DynamicFragment.class, "screen");

					screen.close();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(btn_backListener, "btn_backListener");

			btn_back.setOnClickListener(btn_backListener);

			// UIBuilder uIBuilder = new UIBuilder();

			Button btn_right = uIBuilder.<Button>createShadow(Button.class, "btn_right");

			ViewOnClickListener boiling_point_klis = new ViewOnClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					DynamicFragment screen = uIBuilder.<DynamicFragment>createShadow(DynamicFragment.class,
							"screen");
				
					Object image_url = screen.getImageUrl();
					Object image_storage_path = screen.getImageStoragePath();

					MapObject objMap = uIBuilder.createListFieldNameValueShadow(MapObject.class, "mapObject");
					objMap.setFieldValue("image", image_url);
					 

					uIBuilder.postData(objMap, post_collection);

				 

					screen.close();

				}
			};

			uIBuilder.<ViewOnClickListener>createShadowOnClickListener(boiling_point_klis, "OnClickListener1");
			btn_right.setOnClickListener(boiling_point_klis);
			
			
			
			
			
			
			
			
			
			UIBuilder uIBuilder2 = uIBuilder;

			android.widget.ImageView imageViewScreen = uIBuilder2.<android.widget.ImageView>createShadow(
					android.widget.ImageView.class, "imageViewScreen");

			ViewOnClickListener pickImageListener = new ViewOnClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(View v) {
					DynamicFragment screen = uIBuilder2.<DynamicFragment>createShadow(DynamicFragment.class,
							"screen");
					screen.pickUploadImage("imageViewScreen");
				 

				}
			};

			uIBuilder2.<ViewOnClickListener>createShadowOnClickListener(pickImageListener, "pickImageListener");
			imageViewScreen.setOnClickListener(pickImageListener);
//
//			android.widget.ImageView imageUpload = uIBuilder2.<android.widget.ImageView>createShadow(
//					android.widget.ImageView.class, "imageUpload");
//
//			ViewOnClickListener imageUploadListener = new ViewOnClickListener() {
//
//				/**
//				 * 
//				 */
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				public void onClick(View v) {
//					DynamicFragment screen = uIBuilder2.<DynamicFragment>createShadow(DynamicFragment.class,
//							"screen");
//					screen.uploadImage();
//
//				}
//			};
//
//			uIBuilder2.<ViewOnClickListener>createShadowOnClickListener(imageUploadListener, "imageUploadListener");
//
//			imageUpload.setOnClickListener(imageUploadListener);
			


			return uIBuilder;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
