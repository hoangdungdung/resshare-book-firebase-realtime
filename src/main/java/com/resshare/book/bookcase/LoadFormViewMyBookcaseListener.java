package com.resshare.book.bookcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.book.search.RecyclerViewBookcaseUI;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.LoadFormOxyBaseListener;

public class LoadFormViewMyBookcaseListener extends LoadFormOxyBaseListener {

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		// draft/covid19/load_form_script/essential_food
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.my_bookcase);
		// FirebaseRefCovid19.draft_covid19 + "/load_form_script/" + getType();
	}

	@Override
	public String getReferenceNamePostData() {
		// "../draft/covid19/create_volunteers_group/post_data";
		return ResFirebaseReference.getInputPathReference("jjjjjjjjjjj");
		// "../requets/searching");
	}

	// @Override
	// public Script getScript() {
	// LoadFormSupplierOxyUI loadFormSupplierOxyUI = new LoadFormSupplierOxyUI();
	// return loadFormSupplierOxyUI.getUIBuilder().getScript();
	//
	// }
	public String getScriptName() {
		// TODO Auto-generated method stub
		return ViewMyBookcaseUI.class.getName();
	}
	// public static void viewUI(DataSnapshot snapshot, DataSnapshot snapshotBook)
	// {}

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		try {
			if (snapshot1.child("processing").getValue() == null) {

				DashboardMessage dashboardMessage = new DashboardMessage();
				dashboardMessage.setApplication(snapshot1.child("application").getValue(String.class));
				dashboardMessage.setDelete(0);
				dashboardMessage.setEvent(snapshot1.child("event_recycler_view").getValue(String.class));

				dashboardMessage.setUser_id_destination(snapshot1.child("user_id").getValue(String.class));

				Map msgDashboardMessage = dashboardMessage.totHashMap();

				Map script_param = new HashMap<>();

				String path = "system_setting/layout/android/my_bookcase/layout";
				// String grid_view_layout_item_path =
				// "system_setting/layout/android/search_dashboard_layout/grid_view_layout_item";
				// String grid_view_data_path=
				// "system_setting/layout/android/search_dashboard_layout/grid_view_data/list_item";

				// Object grid_view_layout_item = snapshot.child("grid_view_layout_item")
				// .getValue();
				// Object grid_view_data = snapshot.child("grid_view_data").child("list_item")
				// .getValue();

				Object cfgLayout = com.resshare.framework.core.service.Cache.configuration.child(path).getValue();
				// Object grid_view_layout_item =
				// com.resshare.framework.core.service.Cache.configuration.child(grid_view_layout_item_path).getValue();
				// Object grid_view_data =
				// com.resshare.framework.core.service.Cache.configuration.child(grid_view_data_path).getValue();

				List<HashMap<String, Serializable>> grid_view_data = new ArrayList<HashMap<String, Serializable>>();
				// script_param.put("grid_view_layout_item",grid_view_layout_item);

				Map objJs = DataUtil.ConvertDataSnapshotToMap(snapshot1);
				String user_id = (String) objJs.get("user_id");
				String book_items_type = snapshot1.child("data/book_items_type").getValue(String.class);
				
				
			

				// btnActionBook

				FirebaseDatabase.getInstance()
						.getReference(ResFirebaseReference.getAbsolutePath("../data/" + book_items_type)).child(user_id)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot2) {
								Iterable<DataSnapshot> snapshotBooks = snapshot2.getChildren();
								for (DataSnapshot dataSnapshotBook : snapshotBooks) {

									String book_id = dataSnapshotBook.getKey();

									FirebaseDatabase.getInstance()
											.getReference(ResFirebaseReference.getAbsolutePath("../data/books"))
											.child(book_id).addListenerForSingleValueEvent(new ValueEventListener() {

												@Override
												public void onDataChange(DataSnapshot snapshotBookData) {

													// Iterable<DataSnapshot> snapshotItems = dataSnapshotBook
													// .getChildren();
													//
													// for (DataSnapshot dataSnapshotItemdetail : snapshotItems) {
													//
													// String book_item_id = dataSnapshotItemdetail.getKey();
													//
													// Boolean free_loan = dataSnapshotItemdetail.child("free_loan")
													// .getValue(Boolean.class);
													// Double sale_price = dataSnapshotItemdetail.child("sale_price")
													// .getValue(Double.class);
													//
													// HashMap<String, Serializable> params = new HashMap<String,
													// Serializable>();
													// params.put("book_id", book_id);
													// // params.put("book_name", item.getName());
													// params.put("owner_name", "owner_name");
													//
													// params.put("renter_name", "renter_name");
													// params.put("rent_date", ServerValue.TIMESTAMP.toString());
													// // params.put("status", item.getStatus());
													// params.put("free_loan", free_loan);
													// params.put("sale_price", sale_price);
													// params.put("renter_avatar", "renter_avatar");
													// params.put("book_item_id", book_item_id);
													// grid_view_data.add(params);
													//
													// }

													// script_param.put("grid_view_data", grid_view_data);
													Iterable<DataSnapshot> snapshotItems = dataSnapshotBook
															.getChildren();
													for (DataSnapshot dataSnapshotItemdetail : snapshotItems) {
														Map<String, Object> hmBook = new HashMap<String, Object>();

														Object state_flow = dataSnapshotItemdetail.child("state_flow")
																.getValue();
														Object state_flow_title = "------";
														if (state_flow == null) {
															state_flow = "";
															state_flow_title = "Xóa";
														} else {
															state_flow_title = com.resshare.framework.core.service.Cache.configuration
																	.child("system_setting/book_state_flow/"
																			+ book_items_type + "/" + state_flow
																			+ "/button")
																	.getValue();
															if (state_flow_title == null)
																state_flow_title = "";

														}

														hmBook.put("state_flow", state_flow);
														hmBook.put("state_flow_title", state_flow_title);

														hmBook.put("name", "Tên sách: " + snapshotBookData.child("name")
														 
																.getValue(String.class));

														hmBook.put("image",  snapshotBookData.child("image")
														 
																.getValue(String.class));
														hmBook.put("category_name", "Loại: " + snapshotBookData
																.child("category_name").getValue(String.class));
														hmBook.put("author_name", "Tác giả: " + snapshotBookData
																.child("author_name").getValue(String.class));
														hmBook.put("views_count", "Lượt xem: "
																+ snapshotBookData.child("views_count").getValue());
														hmBook.put("cover_price", "Giá bìa: "
																+ snapshotBookData.child("cover_price").getValue());

														hmBook.put("quantity", "Số lượng: "
																+ dataSnapshotItemdetail.child("quantity").getValue());

														if (dataSnapshotItemdetail.child("free_loan")
																.getValue(Boolean.class))

										{
															hmBook.put("free_loan", "Sách cho mượn");
															hmBook.put("value_price", "Số ngày mượn: "
																	+ String.valueOf(dataSnapshotItemdetail
																			.child("numdays_borrow").getValue()));
														} else {
															hmBook.put("free_loan", "Sách bán");
															hmBook.put("value_price",
																	"Giá bán: "
																			+ String.valueOf(dataSnapshotItemdetail
																					.child("sale_price").getValue())
																			+ " đ");
														}
													 
														hmBook.put("book_items_id",
																dataSnapshotItemdetail.getKey());
														hmBook.put("book_id", dataSnapshotBook.getKey());
													 

														// book_items_type
														// item id: dataSnapshotItemdetail.getKey()
														script_param.put("book_data", hmBook);
														script_param.put("book_items_type", book_items_type);
														script_param.put("book_items_id",
																dataSnapshotItemdetail.getKey());
														script_param.put("book_id", dataSnapshotBook.getKey());

														script_param.put("post_collection__change_state_flow",
																"book/data/" + book_items_type + "_change_state_flow");

														Map mapReturnData = new HashMap<>();
														mapReturnData.put("layout", cfgLayout);
														// RecyclerViewGridUI recyclerViewGridUI=new
														// RecyclerViewGridUI();
														// Script script =
														// recyclerViewGridUI.getUIBuilder().getScript();
														// System.out.print( script.toString());
														// script.

														mapReturnData.put("script",
																RecyclerViewBookcaseUI.class.getName());
														mapReturnData.put("script_param", script_param);

														msgDashboardMessage.put("data", mapReturnData);

														ResponseClient.sendResponseScriptUI(msgDashboardMessage);
													}

												}

												@Override
												public void onCancelled(DatabaseError error) {
													// TODO Auto-generated method stub

												}

											});

								}

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

				// ResponseClient.sendResponse(objJs);

				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
						.child("processing").setValue("done");
			}
		} catch (Exception e) {
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
			e.printStackTrace();
		}
	}
}
