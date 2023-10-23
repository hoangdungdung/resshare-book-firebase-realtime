package com.resshare.book.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.common.ShowAlertDialogUI;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.goibinhoxy.service.listener.FirebaseRefOxy;
import com.resshare.map.DistanceCalculator;
import com.sshare.core.StringUtil;

import model.Books;
import model.Dashboard;
import model.RequestUsers;
import model.User;
import model.UserDistance;
import service.CreateBookOnlineByBarcode;
import service.ServiceBase;

public class SearchBookService extends ServiceBase {
	private final static int TIME_CHECK = 5000;
	// static private Map<String, Books> mBooks = Cache.mBooks;
	// static private Map<String, String> mBooksName = Cache.mBookName;
	// static private Map<String, Author> mAuthor = Cache.mAuthor;
	// static private Map<String, Category> mCategory = Cache.mCategory;

	public SearchBookService() {
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);
	}

	// public String getTableName() {
	// return ResFirebaseReference.getInputPathReference("../searching_book" ) ;
	// }

	private void checkHasNewSearching(DataSnapshot snapshot) {
		try {
			// for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
			// long timeStamp = (long) snapshot.child("time_stamp").getValue();
			// long current = new Date().getTime();

			// if (current - timeStamp < TIME_CHECK) {
			String userId = (String) snapshot.child("user_id").getValue();
			String text = snapshot.child("data/text_seach").getValue(String.class);
			String sKey = snapshot.getKey();

			String sCollection = "user_interest_book";
			if (userId != null)
				FirebaseDatabase.getInstance().getReference().child(sCollection).child(userId).child(sKey)
						.setValue(text);

			// long type = (long) snapshot.child("type").getValue();
			// switch (SearchData.getTypeEnum((int) type)) {
			// case BOOK:
			onSearchBook(userId, text, snapshot);

			// break;
			// case AUTHOR:
			// break;
			// case USER:
			// break;
			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void viewUI(DataSnapshot snapshot, DataSnapshot snapshotBook) {

		try {

			DashboardMessage dashboardMessage = new DashboardMessage();
			dashboardMessage.setApplication(snapshot.child("application").getValue(String.class));
			dashboardMessage.setDelete(0);
			dashboardMessage.setEvent(snapshot.child("event_recycler_view").getValue(String.class));
			String user_id = snapshot.child("user_id").getValue(String.class);
			dashboardMessage.setUser_id_destination(user_id);

			Map msgDashboardMessage = dashboardMessage.totHashMap();

			Map script_param = new HashMap<>();

			String path = "system_setting/layout/android/search_dashboard_layout/layout";
			String grid_view_layout_item_path = "system_setting/layout/android/search_dashboard_layout/grid_view_layout_item";
			// String
			// grid_view_data_path="system_setting/layout/android/search_dashboard_layout/grid_view_data/list_item";

			// Object grid_view_layout_item = snapshot.child("grid_view_layout_item")
			// .getValue();
			// Object grid_view_data = snapshot.child("grid_view_data").child("list_item")
			// .getValue();

			Object cfgLayout = com.resshare.framework.core.service.Cache.configuration.child(path).getValue();
			Object grid_view_layout_item = com.resshare.framework.core.service.Cache.configuration
					.child(grid_view_layout_item_path).getValue();
			// Object grid_view_data =
			// com.resshare.framework.core.service.Cache.configuration.child(grid_view_data_path).getValue();

			List<HashMap<String, Serializable>> grid_view_data = new ArrayList<HashMap<String, Serializable>>();
			script_param.put("grid_view_layout_item", grid_view_layout_item);

			String book_id = snapshotBook.getKey();

			// FirebaseDatabase.getInstance().getReference(
			// ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS_NEW+"/"+book_id))

			FirebaseDatabase.getInstance().getReference()
					.child(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOK_USERS_NEW))
					.child(book_id).addListenerForSingleValueEvent(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshotItems) {

							// script_param.put("grid_view_data",grid_view_data);

							Date dt = new Date();
							String id = String.valueOf(dt.getTime());

							Map<String, Object> hmBook = new HashMap<String, Object>();
							 hmBook.put("book_id", book_id);
							String bookName = snapshotBook.child("name").getValue(String.class);
							 Object image = snapshotBook.child("image").getValue();
							 hmBook.put("image", image);
							
							 
							hmBook.put("name", "Tên sách: " + bookName);
							hmBook.put("category_name",
									"Loại: " + snapshotBook.child("category_name").getValue(String.class));
							hmBook.put("author_name",
									"Tác giả: " + snapshotBook.child("author_name").getValue(String.class));
							if(snapshotBook.child("rating").getValue()!=null)
							hmBook.put("rating", "Đánh giá: " + snapshotBook.child("rating").getValue()+" *");
							else 	hmBook.put("rating", "Chưa có đánh giá" );
							hmBook.put("cover_price", "Giá bìa: " + snapshotBook.child("cover_price").getValue());

							script_param.put("book_data", hmBook);
							script_param.put("post_collection_select_item",
									RefFireBaseBook.BOOK_INPUT_post_collection_select_item);

							Map mapReturnData = new HashMap<>();
							mapReturnData.put("layout", cfgLayout);
							// RecyclerViewGridUI recyclerViewGridUI=new RecyclerViewGridUI();
							// Script script = recyclerViewGridUI.getUIBuilder().getScript();
							// System.out.print( script.toString());
							// script.

							mapReturnData.put("script", SearchBookUI.class.getName());
							mapReturnData.put("script_param", script_param);

							msgDashboardMessage.put("data", mapReturnData);
							msgDashboardMessage.put("id", id);
							msgDashboardMessage.put("on_top", 1);

							ResponseClient.sendResponseScriptUI(msgDashboardMessage);

							if (snapshotItems.exists()) {

								FirebaseDatabase.getInstance()
										.getReference(ResFirebaseReference.getDataPathReference(FirebaseRefOxy.profile)
												+ "/" + user_id)
										.addListenerForSingleValueEvent(new ValueEventListener() {

											@Override
											public void onDataChange(DataSnapshot snapshotMyProfile) {

												Iterable<DataSnapshot> snapshotChildren = snapshotItems.getChildren();
												for (DataSnapshot dataSnapshot : snapshotChildren) {
													String owner_id = dataSnapshot.getKey();
													
													FirebaseDatabase.getInstance().getReference()
													.child(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.data_blacklist_user_book)).child(user_id).
													child(owner_id).addListenerForSingleValueEvent(new ValueEventListener() {
														
														@Override
														public void onDataChange(DataSnapshot snapshotdata_blacklist_user_book) {
															if (snapshotdata_blacklist_user_book.exists())
															{}
															else
															{
																viewDetailOwner(snapshot, msgDashboardMessage, book_id, id,
																		bookName, snapshotMyProfile, dataSnapshot);
															}
															// TODO Auto-generated method stub
															
														}
														
														@Override
														public void onCancelled(DatabaseError error) {
															// TODO Auto-generated method stub
															
														}
													});
													
													
													

													

												}

											}

											private void viewDetailOwner(DataSnapshot snapshot, Map msgDashboardMessage,
													String book_id, String id, String bookName,
													DataSnapshot snapshotMyProfile, DataSnapshot dataSnapshot) {
												String owner_id = dataSnapshot.getKey();
												FirebaseDatabase.getInstance()
														.getReference(ResFirebaseReference.getDataPathReference(
																FirebaseRefOxy.profile) + "/" + owner_id)
														.addListenerForSingleValueEvent(new ValueEventListener() {

															@Override
															public void onDataChange(
																	DataSnapshot snapshotOwnerBook) {
																Iterable<DataSnapshot> dataSnapshotItemdetailChildren = dataSnapshot
																		.child("book_items").getChildren();
																for (DataSnapshot dataSnapshotItemdetail : dataSnapshotItemdetailChildren) {

																	String book_item_id = dataSnapshotItemdetail
																			.getKey();
																	Boolean free_loan = dataSnapshotItemdetail
																			.child("free_loan")
																			.getValue(Boolean.class);
																	Double sale_price = dataSnapshotItemdetail
																			.child("sale_price")
																			.getValue(Double.class);
																	String sSale_price = "";
																	if (free_loan == true) {
																		sSale_price = "Cho mượn";
																	} else
																		sSale_price = "Bán: " + sale_price + " đ";

																	HashMap<String, Serializable> params = new HashMap<String, Serializable>();
																	params.put("book_id", book_id);
																	String owner_name = "Chưa rõ";
																	String owner_namelocation = null;
																	String owner_image = null;
																	
																	if (snapshotOwnerBook.exists()) {
																		owner_name = snapshotOwnerBook
																				.child("user_name")
																				.getValue(String.class);
																		owner_namelocation = snapshotOwnerBook
																				.child("location")
																				.getValue(String.class);
																		owner_image = snapshotOwnerBook
																				.child("image")
																				.getValue(String.class);
																	}
																	// params.put("book_name", item.getName());
																	params.put("owner_name", owner_name);
																	params.put("book_name", bookName);
																	
																	params.put("owner_id", owner_id);
																	params.put("renter_id",
																			snapshot.child("user_id")
																					.getValue(String.class));
																	String renter_name = "Chưa rõ";
																	String mylocation = null;
																	if (snapshotMyProfile.exists()) {
																		renter_name = snapshotMyProfile
																				.child("user_name")
																				.getValue(String.class);
																		mylocation = snapshotMyProfile
																				.child("location")
																				.getValue(String.class);
																	}
																	double distance = DistanceCalculator.distance(
																			mylocation, owner_namelocation);

																	params.put("distance", "Cách~"
																			+ Math.round(distance) + "km");

																	params.put("renter_name",
																			"renter_name" + renter_name);
																	params.put("rent_date",
																			String.valueOf(ServerValue.TIMESTAMP));
																	// params.put("status", item.getStatus());
																	params.put("free_loan", free_loan);
																	params.put("sale_price", sSale_price);
																	params.put("renter_avatar", "renter_avatar");
																	params.put("book_item_id", book_item_id);
																	params.put("image", owner_image);
																	
																	Map mapDataItemm = new HashMap<>();
																	mapDataItemm.put("script",
																			SearchBookViewItemUI.class.getName());
																	Map script_param = new HashMap<>();
																	script_param.put("item_data", params);
																	mapDataItemm.put("script_param", script_param);
																	msgDashboardMessage.put("data", mapDataItemm);
																	msgDashboardMessage.put("message_type",
																			"RecyclerViewGrid");
																	msgDashboardMessage.put("id", id);
																	ResponseClient.sendResponseScriptUI(
																			msgDashboardMessage);

																}

															}

															@Override
															public void onCancelled(DatabaseError error) {
																// TODO Auto-generated method stub

															}
														});
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void onSearchBook(String userId, String textSearch, DataSnapshot snapshotRoot) {
		System.out.println("onSearchBook " + textSearch);

		System.out.println("onSearchBook " + textSearch);

		String textSearchEncode = StringUtil.removeAccent(textSearch);
		Books book;

		FirebaseDatabase.getInstance().getReference().child(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY)
				.child(textSearchEncode).addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {
						try {

							if (snapshot.exists()) {

								Iterable<DataSnapshot> snapshotChildren = snapshot.getChildren();
								for (DataSnapshot dataSnapshot : snapshotChildren) {

									String bookId = dataSnapshot.getValue(String.class);
									addInterestBookToDashboard(userId, textSearch, bookId, snapshotRoot);
								}

								// String bookId = sArrBookId;
								// if (sArrBookId.contains(BookService.keySplit)) {
								// String[] arrBookId = sArrBookId.split(BookService.keySplit);
								//
								// for (int i = 0; i < arrBookId.length; i++) {
								// bookId = arrBookId[i];
								//
								// addInterestBookToDashboard(userId, textSearch, bookId);
								//
								// }
								// return;
								// } else
								//
								// addInterestBookToDashboard(userId, textSearch, bookId);

							} else {
								
								
								
								DashboardMessage dashboardMessage = new DashboardMessage();
								dashboardMessage.setApplication(snapshotRoot.child("application").getValue(String.class));
								dashboardMessage.setDelete(0);
								dashboardMessage.setEvent(snapshotRoot.child("event").getValue(String.class));
								String user_id = snapshotRoot.child("user_id").getValue(String.class);
								dashboardMessage.setUser_id_destination(user_id);

								Map msgDashboardMessage = dashboardMessage.totHashMap();
								
								Map mapReturnData = new HashMap<>();
							 
								// RecyclerViewGridUI recyclerViewGridUI=new RecyclerViewGridUI();
								// Script script = recyclerViewGridUI.getUIBuilder().getScript();
								// System.out.print( script.toString());
								// script.

								mapReturnData.put("script", ShowAlertDialogUI.class.getName());
							//	mapReturnData.put("script_param", script_param);

								msgDashboardMessage.put("data", mapReturnData);
							//	msgDashboardMessage.put("id", id);
								//msgDashboardMessage.put("on_top", 1);

								ResponseClient.sendResponseScriptUI(msgDashboardMessage);

								
								
								
								
								
								
								
								
								
								String bookID = null;
								String sKeyProgressDashboard = null;
								try {

									sKeyProgressDashboard = addProgressDashboard(userId);

									CreateBookOnlineByBarcode.createBookSeleniumText(textSearch, userId,
											sKeyProgressDashboard);
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId)
											.child(sKeyProgressDashboard).removeValue();

								}
								// if (bookID != null) {
								//
								// addInterestBookToDashboard(userId, textSearch, bookID);
								// return;
								//
								// }
								//
								// addInterestNotFoundBookToDashboard(userId, textSearch);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});

		// if (mBooksName.containsKey(textSearchEncode)) {
		// String sArrBookId = mBooksName.get(textSearchEncode);
		// String bookId = sArrBookId;
		// if (sArrBookId.contains(BookService.keySplit)) {
		// String[] arrBookId = sArrBookId.split(BookService.keySplit);
		//
		// for (int i = 0; i < arrBookId.length; i++) {
		// bookId = arrBookId[i];
		// if (mBooks.containsKey(bookId)) {
		// book = mBooks.get(bookId);
		// book.setBook_id(bookId);
		//
		// addInterestBookToDashboard1(userId, textSearch, book);
		// }
		// }
		// return;
		// }
		//
		// if (mBooks.containsKey(bookId)) {
		// book = mBooks.get(bookId);
		// book.setBook_id(bookId);
		// addInterestBookToDashboard1(userId, textSearch, book);
		// return;
		//
		// }
		// }

	}

	public static void addInterestNotFoundBookToDashboard(String userId, String textSearch) {
		System.out.println("addInterestNotFoundBookToDashboard " + textSearch);
		// '.', '#', '$', '[', or ']';
		HashMap<String, Object> dashboard = Dashboard.getMapsBookInterestNotFound(textSearch);
		if (userId != null) {
			textSearch = StringUtil.removeAccent(textSearch);
			FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(textSearch)
					.updateChildren(dashboard);
		}

	}

	private static String addProgressDashboard(String userId) {

		// '.', '#', '$', '[', or ']';
		HashMap<String, Object> dashboard = Dashboard.getMapsProgress();
		if (userId != null) {

			String key = FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).push().getKey();

			FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(key)
					.setValue(dashboard);
			return key;
		}
		return null;
	}

	private static void addInterestBookToDashboard1(String userId, String textSearch, Books book) {
		System.out.println("addInterestBookToDashboard " + book.getBook_id());
		HashMap<String, Object> dashboard = Dashboard.getMapsBookInterest(textSearch, book);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId)
				.child("seach" + book.getBook_id()).updateChildren(dashboard);

		getListUserByBookId(book.getBook_id(), userId);

	}

	public static void addInterestBookToDashboard(String userId, String textSearch, String book_id,
			DataSnapshot snapshotRoot) {

		FirebaseDatabase.getInstance()
				.getReference(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS_NEW + "/" + book_id))
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {

						if (snapshot.exists()) {
							
							FirebaseDatabase.getInstance()
							.getReference(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.data_objectionable_content_user_book + "/" + userId + "/" + book_id))
							.addListenerForSingleValueEvent(new ValueEventListener() {
								
								@Override
								public void onDataChange(DataSnapshot snapshotobjectionable_content) {
									if (snapshotobjectionable_content.exists()) 
									{
										zeroBook(snapshotRoot);
									}
									else
									{
										viewUI(snapshotRoot, snapshot);
										
									}
									
								}
								
								@Override
								public void onCancelled(DatabaseError error) {
									// TODO Auto-generated method stub
									
								}
							});
							
						
							
							
						} else {
							zeroBook(snapshotRoot);

						}

					}

					private void zeroBook(DataSnapshot snapshotRoot) {
						DashboardMessage dashboardMessage = new DashboardMessage();
						dashboardMessage.setApplication(snapshotRoot.child("application").getValue(String.class));
						dashboardMessage.setDelete(0);
						dashboardMessage.setEvent(snapshotRoot.child("event").getValue(String.class));
						String user_id = snapshotRoot.child("user_id").getValue(String.class);
						dashboardMessage.setUser_id_destination(user_id);

						Map msgDashboardMessage = dashboardMessage.totHashMap();
						
						Map mapReturnData = new HashMap<>();
 
						// RecyclerViewGridUI recyclerViewGridUI=new RecyclerViewGridUI();
						// Script script = recyclerViewGridUI.getUIBuilder().getScript();
						// System.out.print( script.toString());
						// script.

						mapReturnData.put("script", ShowAlertDialogUI.class.getName());
//	mapReturnData.put("script_param", script_param);

						msgDashboardMessage.put("data", mapReturnData);
//	msgDashboardMessage.put("id", id);
						//msgDashboardMessage.put("on_top", 1);

						ResponseClient.sendResponseScriptUI(msgDashboardMessage);
					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});

		HashMap<String, Object> dashboard = Dashboard.getMapsBookInterest(textSearch, book_id);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child("seach" + book_id)
				.updateChildren(dashboard);
		// .updateChildren(dashboard);

		getListUserByBookId(book_id, userId);

	}

	public static void addInterestBookToDashboard(String userId, String textSearch, String book_id) {

		HashMap<String, Object> dashboard = Dashboard.getMapsBookInterest(textSearch, book_id);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child("seach" + book_id)
				.updateChildren(dashboard);
		// .updateChildren(dashboard);

		getListUserByBookId(book_id, userId);

	}

	private static final String TABLE_REQUEST_USER_DISTANCE = "request_user_distance";

	private static void getListUserByBookId(String bookId, String userId) {

		FirebaseDatabase.getInstance().getReference().child(RefFireBaseBook.BOOK_DATA_BOOK_USERS).child(bookId)
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						if (dataSnapshot != null && dataSnapshot.getValue() != null) {

							FirebaseDatabase database = FirebaseDatabase.getInstance();

							DatabaseReference firebaseLocationTemp = database.getReference(TABLE_REQUEST_USER_DISTANCE);

							String key_id_temp = firebaseLocationTemp.push().getKey();

							RequestUsers RequestUsers = new RequestUsers();
							RequestUsers.request_userId = userId;

							firebaseLocationTemp.runTransaction(new Handler() {

								@Override
								public void onComplete(DatabaseError error, boolean committed,
										DataSnapshot currentData) {
									// TODO Auto-generated method stub

								}

								@Override
								public Result doTransaction(MutableData currentData) {
									currentData.child(key_id_temp).child("request_userId").setValue(userId);

									for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
										User user = snapshot.getValue(User.class);
										user.setUser_id(snapshot.getKey());
										UserDistance userDistance = new UserDistance();
										userDistance.setUser_id(user.getUser_id());
										userDistance.setMail_address(user.getMail_address());
										// userDistance.setDistance(0);

										currentData.child(key_id_temp).child("Z-listUser")
												.child(userDistance.getUser_id()).setValue(0);

									}

									return Transaction.success(currentData);
								}
							});

						}

					}

					@Override
					public void onCancelled(DatabaseError error) {
						// TODO Auto-generated method stub

					}
				});

		// return listUsersDistance;
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
		// System.err.println("onChildRemoved " + snapshot.getKey());

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		// System.err.println("onChildMoved " + snapshot.getKey());
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// System.err.println("onChildChanged " + snapshot.getKey());
		//
		// UserInterestChilldService userInterestChilldService = new
		// UserInterestChilldService("user_interest/"+ snapshot.getKey());
		// userInterestChilldService.onStart();
		//// checkHasNewSearching(snapshot);
		// // TODO Auto-generated method stub

	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.err.println("onChildAdded " + snapshot.getKey());
		checkHasNewSearching(snapshot);

		// FirebaseDatabase.getInstance().getReference().child(getReferenceName()).child(snapshot.getKey()).removeValue();
	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		System.err.println("onCancelled " + error.getMessage());
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.SEARCHING_BOOK);
		// return RefFireBaseBook.SEARCHING_BOOK;
	}

}
