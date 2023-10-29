package com.resshare.book.addbook;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resshare.book.RefFireBaseBook;
import com.resshare.common.CommandConstant;
import com.resshare.common.ShowMsgDashboardUI;
import com.resshare.core.control.model.MsgResponse;
import com.resshare.framework.core.DataUtil;
import com.resshare.framework.core.reflectproxy.ProxyDemo;
import com.resshare.framework.core.service.Cache;
import com.resshare.framework.core.service.DashboardMessage;
import com.resshare.framework.core.service.FireBaseReference;
import com.resshare.framework.core.service.IRequest;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.core.service.ResponseClient;
import com.resshare.framework.model.Input;
import com.sshare.core.StringUtil;

import bo.BooksItemsNoaBoImpl2;
import model.BookItems;
import model.BookItemsNoa;
import model.Books;
import rekognition.DetectModerationLabels;
import service.ServiceBase;

public class BooksItemsNoaService extends ServiceBase {
	BooksItemsNoaBoImpl2 booksItemsNoaBo;

	public BooksItemsNoaService() {
		super();
		booksItemsNoaBo = new BooksItemsNoaBoImpl2();
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub
		System.out.println("onChildRemoved" + snapshot.getKey());
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

		System.out.println("onChildMoved" + snapshot.getKey());
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub
		System.out.println("onChildChanged" + snapshot.getKey());
		// booksItemsNoaBo.checkIsNew(snapshot);
	}

	final FirebaseDatabase database = FirebaseDatabase.getInstance();
	// DatabaseReference refItemHis =
	// database.getReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA_HIS);

	@Override
	public void onChildAdded(DataSnapshot snapshot100, String previousChildName) {
		if (snapshot100.child("processing").getValue() == null)
			try {

				String sKey = snapshot100.getKey();
				// BookItems book_item = snapshot.child("data").getValue(BookItems.class);

				// Gson gson = new Gson();
				// JsonElement jsonElement = gson.toJsonTree(snapshot.child("data").getValue());
				//
				// String json = gson.toJson(jsonElement);
				// BookItems book_item = gson.fromJson(json, BookItems.class);

				// Class classOfT;
				Object obj = snapshot100.child("data").getValue();
				BookItems book_item = DataUtil.fromJson(obj, BookItems.class);

				Books book = DataUtil.fromJson(obj, Books.class);
				book.setCreator(snapshot100.child("user_id").getValue(String.class));

				BookItemsNoa bookItemsNoa = DataUtil.fromJson(obj, BookItemsNoa.class);
				bookItemsNoa.setBook(book);
				bookItemsNoa.setBook_item(book_item);

				String skeyResponse = bookItemsNoa.getResponse_key();

				MsgResponse msg = new MsgResponse();
				try {

					// TODO Auto-generated method stub
					System.out.println("onChildAdded" + snapshot100.getKey());
					System.out.println("checkIsNew " + snapshot100.getKey());
					if (snapshot100 != null && snapshot100.getValue() != null) {

						if (bookItemsNoa.getCreate_date() != null) {

							bookItemsNoa.getBook().setCreate_time(bookItemsNoa.getCreate_date());

						}
						if (bookItemsNoa.getUser_id() != null) {
							bookItemsNoa.getBook().setCreator(bookItemsNoa.getUser_id());
							bookItemsNoa.getBook_item().setOwner_id(bookItemsNoa.getUser_id());
							bookItemsNoa.getBook_item().setUser_id(bookItemsNoa.getUser_id());

						}

						String bookName = bookItemsNoa.getBook().getName();
						if (bookItemsNoa.getBook().getCover_price() == null)
							bookItemsNoa.getBook().setCover_price(0L);

						if ((bookItemsNoa.getBook().getCover_price() != 0)
								& (bookItemsNoa.getBook_item().getSale_price() != null)) {
							double percent = 100
									* (bookItemsNoa.getBook().getCover_price()
											- bookItemsNoa.getBook_item().getSale_price())

									/ bookItemsNoa.getBook().getCover_price();
							long percent1 = Math.round(percent);
							String saving_percentage = String.valueOf(percent1) + "%";
							bookItemsNoa.getBook_item().setSaving_percentage(saving_percentage);
						}
						String keyEncode1 = StringUtil.removeAccent(bookName);

						// String sKey = "";

						//
						boolean bISDN = false;
						String sISBN = bookItemsNoa.getBook().getISBN();
						if ((sISBN != null) && !"".equals(sISBN)) {

							String bookId = "BK" + sISBN;
							FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOKS).child(bookId)
									.addListenerForSingleValueEvent(new ValueEventListener() {

										@Override
										public void onDataChange(DataSnapshot snapshot) {
											if (!snapshot.exists()) {
											
										
												
												
												booksItemsNoaBo.addBookBookItem(bookItemsNoa, bookId);
												msg.setUpload_image(true);
												msg.setImage_path(bookItemsNoa.getBook().getImage());
												msg.setReferenceImagePath(
														RefFireBaseBook.BOOK_DATA_BOOKS + "/" + bookId + "/" + "image");
											} else {

												bookItemsNoa.getBook_item().setBook_id(bookId);
												booksItemsNoaBo.addItem(bookItemsNoa.getBook_item(),
														bookItemsNoa.getBook().getCreator(), bookId);
												msg.setUpload_image(false);

											}
											msg.setSuccess(true);
											msg.setMessage_success("Thêm sách thành công");
											thongbaothanhcong(snapshot100);
											FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
													.removeValue();

											// refItemHis.child(sKey).setValue(bookItemsNoa);
											DatabaseReference refResponse = database
													.getReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA_RES);
											//Tạm thời bỏ thông báo 

										//	refResponse.child(skeyResponse).child("result").setValue(msg);

										}

										@Override
										public void onCancelled(DatabaseError error) {
											// TODO Auto-generated method stub

										}
									});

							// sKey = bookItemsNoa.getBook().getISBN();
						}

						else {
							boolean bFind = false;
							String cove_price = String.valueOf(bookItemsNoa.getBook().getCover_price());

							FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY)
									.child(keyEncode1).child(cove_price)
									.addListenerForSingleValueEvent(new ValueEventListener() {

										@Override
										public void onDataChange(DataSnapshot snapshot) {
											try {

												if (snapshot.exists()) {

													String sBook_id = snapshot.getValue(String.class);
													// Cache.mBookName.get(keyEncode1);
													String book_id = sBook_id;

													bookItemsNoa.getBook_item().setBook_id(book_id);
													booksItemsNoaBo.addItem(bookItemsNoa.getBook_item(),
															bookItemsNoa.getBook().getCreator(), book_id);
													msg.setUpload_image(false);

												} else {
													String book_id = "BK" + sKey;
													booksItemsNoaBo.addBookBookItem(bookItemsNoa, book_id);
													msg.setUpload_image(true);
													msg.setImage_path(bookItemsNoa.getBook().getImage());
													msg.setReferenceImagePath(RefFireBaseBook.BOOK_DATA_BOOKS + "/"
															+ book_id + "/" + "image");

												}
												msg.setSuccess(true);
												msg.setMessage_success("Thêm sách thành công");

												// FirebaseDatabase.getInstance().getReference(getReferenceName()).child(sKey)
												// .removeValue();

												// refItemHis.child(sKey).setValue(bookItemsNoa);
												// DatabaseReference refResponse = database
												// .getReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA_RES);
												//
												// refResponse.child(skeyResponse).child("result").setValue(msg);

												// DataSnapshot { key = -Mnj6TZLWfUQa1dycMuj,
												// value = {application=book, data={author_name=9, numdays_borrow=9,
												// category_name=o, quantity=9, plot_overview=o, free_loan=Cho mượn,
												// name=e, purchase_price=9, cover_price=9, sale_price=9},
												// user_id=-MnOTCqi0a6WocZ5AOd3,
												// event_dashboard_sesion=event_dashboard1636099246081,
												// event_dashboard_current_day=event_dashboard_current_day_121105,
												// event=get_data_layout1636099255143, flowchart_name=} }
												
												
												
//												HashMap mapScripParam = new HashMap<>();
//												mapScripParam.put("message", s);
//
//												Map mapReturnData = new HashMap<>();
//
//										 
//												mapReturnData.put("script", "com.deflh.ShowMessageUI");
//												mapReturnData.put("application_script", "resshare_core");
//												mapReturn.put("script_type", "script_type_form");
//												mapReturn.put("data", mapReturnData);
//												mapReturn.put("script_param", mapScripParam);
//												
												
												
												
												
												

												DashboardMessage dashboardMessage = new DashboardMessage();
												dashboardMessage.setApplication(
														snapshot100.child("application").getValue(String.class));
												dashboardMessage.setDelete(0);
												dashboardMessage.setEvent(snapshot100
														.child("event_dashboard_current_day").getValue(String.class));

												dashboardMessage.setUser_id_destination(
														snapshot100.child("user_id").getValue(String.class));

												Map msgDashboardMessage = dashboardMessage.totHashMap();

												Map script_param = new HashMap<>();
												script_param.put("msg", "Thêm sách thành công");

												String path = RefFireBaseBook.dashboard_msg;

												Object cfgLayout = Cache.configuration.child(path).getValue();

												Map mapReturnData = new HashMap<>();
												mapReturnData.put("layout", cfgLayout);
												mapReturnData.put("script", ShowMsgDashboardUI.class.getName());
												mapReturnData.put("script_param", script_param);

												msgDashboardMessage.put("data", mapReturnData);
												//Tạm thời bỏ thông báo 
												//ResponseClient.sendResponseScriptUI(msgDashboardMessage);
												
												
												
												
												
												thongbaothanhcong(snapshot100);
												
												
												
												

												FirebaseDatabase.getInstance().getReference(getReferenceName())
														.child(snapshot100.getKey()).child("processing")
														.setValue("done");

												// TODO Auto-generated method stub

											} catch (Exception e) {
												e.printStackTrace();
												FirebaseDatabase.getInstance().getReference(getReferenceName())
														.child(snapshot100.getKey()).child("processing")
														.setValue("error");
											}

										}



										

								 

										@Override
										public void onCancelled(DatabaseError error) {
											// TODO Auto-generated method stub

										}
									});

							// if (Cache.mBookName.containsKey(keyEncode1)) {
							// String sBook_id = Cache.mBookName.get(keyEncode1);
							// String book_id = sBook_id;
							// String[] arBook_id = { book_id };
							//
							// if (sBook_id.contains(BookService.keySplit)) {
							// arBook_id = sBook_id.split(BookService.keySplit);
							// }
							// for (int i = 0; i < arBook_id.length; i++) {
							// book_id = arBook_id[0];
							//
							//
							//
							//
							// Books book = Cache.mBooks.get(book_id);
							// if (book.getCover_price() == bookItemsNoa.getBook().getCover_price()) {
							// bookItemsNoa.getBook_item().setBook_id(book_id);
							// booksItemsNoaBo.addItem(bookItemsNoa.getBook_item(),
							// bookItemsNoa.getBook().getCreator(), book_id);
							// msg.setUpload_image(false);
							// bFind=true;
							// break;
							//
							// }
							//
							// }
							//
							//
							//
							// }
							//
							// if(!bFind){
							//
							// booksItemsNoaBo.addBookBookItem(bookItemsNoa, snapshot.getKey());
							// msg.setUpload_image(true);
							// msg.setImage_path(bookItemsNoa.getBook().getImage());
							// msg.setReferenceImagePath(
							// RefFireBaseBook.BOOK_DATA_BOOKS + "/" + snapshot.getKey() + "/" + "image");
							// }
							// }
							// msg.setSuccess(true);
							// msg.setMessage_success("Thêm sách thành công");
							//
							// FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot.getKey())
							// .removeValue();
							//
							// refItemHis.child(snapshot.getKey()).setValue(bookItemsNoa);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.setSuccess(false);
					msg.setException_msg(e.getCause().getMessage());

					DatabaseReference refResponse = database.getReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA_RES);

					refResponse.child(skeyResponse).child("result").setValue(msg);
					FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot100.getKey())
							.child("processing").setValue("error");
					;
				}

			} catch (Exception e) {
				e.printStackTrace();
				FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot100.getKey())
						.child("processing").setValue("error");
			}
	}
	private void thongbaothanhcong(DataSnapshot snapshot100) {
		HashMap hashMapCommand = new HashMap<>();
		HashMap hashMapCommand1 = new HashMap<>();
		hashMapCommand1.put(CommandConstant.show_alert_dialog, "Thêm sách thành công");
		hashMapCommand.put("screen",hashMapCommand1 );
		
		sendCommand(snapshot100,hashMapCommand);
	}
	static Map getHashMapResponse(DataSnapshot snapshot) {
		DashboardMessage dashboardMessage = new DashboardMessage();
		dashboardMessage.setApplication(snapshot.child("application").getValue(String.class));
		dashboardMessage.setDelete(0);
		dashboardMessage.setEvent(snapshot.child("event").getValue(String.class));
		
			
		String user_id = snapshot.child("user_id_destination").getValue(String.class);
		if( snapshot.hasChild("user_id") )
			user_id = snapshot.child("user_id").getValue(String.class);
		dashboardMessage.setUser_id_destination(user_id);

		Map msgDashboardMessage = dashboardMessage.totHashMap();
		return msgDashboardMessage;
	}
	static void sendResponse(Map msgDashboardMessage) {
		ProxyDemo<IRequest> prIRequest = new ProxyDemo<IRequest>();
		IRequest iRequest = prIRequest.create(IRequest.class);
		Gson gson = new GsonBuilder().create();

		String jsonString = gson.toJson(msgDashboardMessage);
		Input input = new Input("resshare", "key1", jsonString);

		input.setDataCollection(FireBaseReference.draft_get_resshare_user_id);
		input.setModule("core");
		iRequest.input(input);
	}
	public static void sendCommand(DataSnapshot snapshot, Map commandMap) {

		try {

			Map msgDashboardMessage = getHashMapResponse(snapshot);

  
			Date dt = new Date();
			String id = String.valueOf(dt.getTime());
 

			 
	 

	 
 

			msgDashboardMessage.put("data", commandMap);

			msgDashboardMessage.put("id", id);
			 
			msgDashboardMessage.put("msg_type", "command");

	 

			sendResponse(msgDashboardMessage);

			// FirebaseDatabase.getInstance().getReference(
			// ResFirebaseReference.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS_NEW+"/"+book_id))

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		System.out.println("onCancelled" + error.getMessage());
	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.BOOK_DATA_ITEMS_NOA);
	}

}
