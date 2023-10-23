package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.bing.BingCrawler;
import com.resshare.bing.Constants;
import com.resshare.book.RefFireBaseBook;
import com.resshare.book.search.SearchBookService;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.sshare.core.StringUtil;

import htmldriver.Phantom;
import service.cache.Cache;

public class CreateBookOnlineByBarcode {

	public CreateBookOnlineByBarcode() {
		// TODO Auto-generated constructor stub
	}

	static String htmlText = "<!DOCTYPE html>" + "    <html>" + "    <head>" + "        <title>Java Magazine</title>"
			+ "    </head>" + "    <body>" + "        <h1>Hello World!</h1>" + "    </body>" + "</html>";

	public static void main(String... args) {
		// "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=8935251406214";
		// a = "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=9786047221585";
		// 9786049631696

		getFieldValue("9786047221585", "kkk");
	}

	public static void getFieldValue(String sISBN, String sUserId) {
		sISBN = sISBN.trim();

		String bBook = null;
		bBook = minhkhaiStore(sISBN, sUserId);
		if (bBook == null)
			getSourceFromObook(sISBN, sUserId);
		if (bBook == null)
			bBook = createBookSeleniumISBN(sISBN, sUserId, "");

	}

	private static void getSourceFromObook(String sISBN, String sUserId) {

		Document doc = null;
		String url = null;
		try {
			doc = Jsoup.connect("https://www.google.com/search?client=opera&q= obook.co " + sISBN
					+ "&sourceid=opera&ie=UTF-8&oe=UTF-8").timeout(0).get();

			Elements gelement = doc.select("div.r");

			for (Element elements : gelement) {
				Element element_1 = elements.getElementsByTag("a").first();

				url = element_1.attr("href");

				if (url.startsWith("https://obook.co")) {

					try {

						Document document = Jsoup.connect(url).get();

						HashMap<String, Object> hashtable = new HashMap<>();

						// Nôi dung cần lấy
						hashtable.put("name", document.select("h1[class=book-name]").text());
						hashtable.put("image", document.select("div[class=item]").attr("data-src"));
						hashtable.put("plot_overview",
								document.select(
										"div[class=book-detail-description read-more-text pre-styled review-content]")
										.text());

						Elements element = document.select("table[class=table-book-detail ui table section-wrap]")
								.first().select("tr");
						for (Element e : element) {
							switch (e.select("td").get(0).text()) {
							case "Tác giả":
								hashtable.put("author_name", e.select("td").get(1).text());
								break;
							case "Tên danh mục":
								hashtable.put("category_name", e.select("td").get(1).text());
								break;
							case "ISBN":
								hashtable.put("isbn", e.select("td").get(1).text());
								break;
							case "Giá bìa":
								hashtable.put("cover_price", e.select("td").get(1).text());
								break;
							}
						}

						hashtable.put("status", 0);
						hashtable.put("count", 1);
						hashtable.put("create_time", System.currentTimeMillis());
						hashtable.put("creator_id", sUserId);
						hashtable.put("source", url);
						createCateg(hashtable);

						createAuthor(hashtable);

						DatabaseReference childBooks = FirebaseDatabase.getInstance()
								.getReference(RefFireBaseBook.BOOK_DATA_BOOKS);
						String bookId = "BK" + sISBN;
						childBooks.child(bookId).updateChildren(hashtable);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static String createBookSeleniumISBN(String sISBN, String sUserId, String sKeyProgressDashboard) {
		{
			String urlGoogle = "https://www.google.com/search?client=opera&q=" + sISBN
					+ "&num=75&sourceid=opera&ie=UTF-8&oe=UTF-8";

			// Bước 1: Điền ID sách vào.
			// String ID_BOOK = "8935244821604";
			// String ID_BOOK = "8935235220973";

			try {
				createBookGoogle(sISBN, sUserId, true, "", sKeyProgressDashboard);
				// if(sBook==null)
				// {
				//
				// String linkSite="fahasa";
				//
				// sBook = createBookGoogle(sISBN, sUserId, true, linkSite);
				// return sBook;
				//
				// }
				// else
				// return sBook;

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return null;
		// minhkhaiStore(sISBN, sUserId);
	}

	public static void createBookSeleniumText(String sText, String sUserId, String sKeyProgressDashboard) {
		{

			// Bước 1: Điền ID sách vào.
			// String ID_BOOK = "8935244821604";
			// String ID_BOOK = "8935235220973";

			try {

				createBookGoogle(sText, sUserId, false, "", sKeyProgressDashboard);
				// if(sBook==null)
				// {
				//
				// String linkSite="fahasa";
				//
				// sBook = createBookGoogle(sText, sUserId, false, linkSite);
				// return sBook;
				//
				// }
				// else
				// return sBook;

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		// return null;
		// minhkhaiStore(sISBN, sUserId);
	}

	private static void createBookGoogle(String stringTextSeah, String sUserId, boolean checksbn, String sLinkSite,
			String sKeyProgressDashboard) throws IOException {
		// Bước 2: Tự động seach sách trên google và lấy địa chỉ web

		Document doc = null;
		List<String> arrLink = new ArrayList<>();

		try {

			arrLink = googleGetLink(stringTextSeah, sLinkSite);

			// doc = Jsoup
			// .connect(urlGoogle)
			// .userAgent(
			// "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
			// .timeout(2000).get();

		} catch (Exception e) {

			// String urlGoogle = "https://www.bing.com/search?client=opera&q=" + sText
			// + "&num=75&sourceid=opera&ie=UTF-8&oe=UTF-8";
			// System.out.print(urlGoogle);
			// doc = Jsoup.connect(urlGoogle).timeout(0).get();

			arrLink = BingCrawler.seachBing(stringTextSeah, sLinkSite);
			// TODO: handle exception
		}

		// arrLink=BingCrawler.seachBing(stringTextSeah,sLinkSite);

		if ((arrLink == null) || (arrLink.size() == 0)) {
			FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId).child(sKeyProgressDashboard)
					.removeValue();

			SearchBookService.addInterestNotFoundBookToDashboard(sUserId, stringTextSeah);
			return;
		}
		int fFind = 0;
		for (String url : arrLink) {
			// Element element_1 = elements.getElementsByTag("a").first();

			// String url = element_1.attr("href");
			{

				Map<String, Object> ht = Cache.mBookSites;

				for (Map.Entry<String, Object> entry : ht.entrySet()) {

					String urlSite = entry.getKey();
					if (url.startsWith(urlSite)) {
						fFind = fFind + 1;
						HashMap hm = Phantom.seachBook(url, (HashMap<String, Object>) entry.getValue(),urlSite);
						if (hm == null)
							return;
						if (hm.entrySet().size() > 1) {
							if (checksbn) {
								if (!hm.containsKey("isbn")) {
									if ((sKeyProgressDashboard != null) && (!"".equals(sKeyProgressDashboard)))
										FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId)
												.child(sKeyProgressDashboard).removeValue();

									SearchBookService.addInterestNotFoundBookToDashboard(sUserId, stringTextSeah);
									return;
								}

								String isbn = ((String) hm.get("isbn")).trim();

								if (!isbn.contains(stringTextSeah)) {
									if ((sKeyProgressDashboard != null) && (!"".equals(sKeyProgressDashboard)))
										FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId)
												.child(sKeyProgressDashboard).removeValue();
									SearchBookService.addInterestNotFoundBookToDashboard(sUserId, stringTextSeah);
									return;
								}
							}
							hm.put("status", 0);
							hm.put("count", 1);
							hm.put("create_time", System.currentTimeMillis());
							hm.put("creator_id", sUserId);

							String textSearchEncode = StringUtil.removeAccent(stringTextSeah);

							Object cover_price = hm.get("cover_price");

							String textSearchEncode1 = textSearchEncode + String.valueOf(cover_price);

							if (!Cache.mBookNameLock.containsKey(textSearchEncode1)) {
								Cache.mBookNameLock.put(textSearchEncode1, textSearchEncode1);
								DatabaseReference childBooks = FirebaseDatabase.getInstance()
										.getReference(RefFireBaseBook.BOOK_DATA_BOOKS);
								String bookIdNew = null;// "BK" + stringTextSeah;
								if (!checksbn) {

									if (hm.containsKey("isbn")) {
										String isbn = ((String) hm.get("isbn")).trim();
										if ((isbn != null) && !"".equals(isbn)) {
											bookIdNew = "BK" + isbn;
										}
									}

								} else {
									bookIdNew = "BK" + stringTextSeah;// stringTextSeah=ibsn
								}
								if ((bookIdNew == null) || "".equals(bookIdNew)) {
									bookIdNew = "BK" + FirebaseDatabase.getInstance()
											.getReference( ResFirebaseReference.getInputPathReference( RefFireBaseBook.BOOK_DATA_ITEMS_NOA)).push().getKey();

								}

								// Cache.mBookName.put(textSearchEncode, bookId);

								String sName = StringUtil.removeAccent(String.valueOf(hm.get("name")));
								String sCover_price = String.valueOf(hm.get("cover_price"));
								hm.put("book_id", bookIdNew);

								FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY)
										.child(sName).child(sCover_price)
										.addListenerForSingleValueEvent(new ValueEventListener() {

											@Override
											public void onDataChange(DataSnapshot snapshot) {
												String bookIdNew1 = String.valueOf(hm.get("book_id"));
												if (snapshot.exists()) {
													bookIdNew1 = snapshot.getValue(String.class);

												} else {
													createCateg(hm);

													createAuthor(hm);
													hm.remove("book_id");
													childBooks.child(bookIdNew1).updateChildren(hm);

												}
												// TODO Auto-generated method stub
												if ((sKeyProgressDashboard != null)
														&& (!"".equals(sKeyProgressDashboard)))
													FirebaseDatabase.getInstance().getReference().child("dashboard")
															.child(sUserId).child(sKeyProgressDashboard).removeValue();
												SearchBookService.addInterestBookToDashboard(sUserId, stringTextSeah,
														bookIdNew1);

											}

											@Override
											public void onCancelled(DatabaseError error) {
												// TODO Auto-generated method stub

											}
										});

							}

							// Books book;

							// return ;

							//
							// FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOK_NAME_KEY).child(textSearchEncode).addListenerForSingleValueEvent(new
							// ValueEventListener() {
							//
							// @Override
							// public void onDataChange(DataSnapshot snapshot) {
							// if(!snapshot.exists())
							// {
							// //if(!Cache.mBookName.containsKey(textSearchEncode))
							// {
							// DatabaseReference childBooks = FirebaseDatabase.getInstance()
							// .getReference(RefFireBaseBook.BOOK_DATA_BOOKS);
							// String bookId = "BK" + stringTextSeah;
							// if (!checksbn)
							// bookId = "BK" + childBooks.push().getKey();
							// // Cache.mBookName.put(textSearchEncode, bookId);
							// createCateg(hm);
							//
							// createAuthor(hm);
							//
							// childBooks.child(bookId).updateChildren(hm);
							//
							// if( (sKeyProgressDashboard!=null)&& (!"".equals(sKeyProgressDashboard)))
							// FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId)
							// .child(sKeyProgressDashboard).removeValue();
							// SearchBookService.addInterestBookToDashboard(sUserId, stringTextSeah,
							// bookId);
							//
							// return ;
							// }
							//
							//
							// }
							// else
							// {
							// String sArrBookId =snapshot.getValue(String.class);
							// String bookId = sArrBookId;
							// if (sArrBookId.contains(BookService.keySplit)) {
							// String[] arrBookId = sArrBookId.split(BookService.keySplit);
							//
							// bookId= arrBookId[0];
							//
							// }
							// if( (sKeyProgressDashboard!=null)&& (!"".equals(sKeyProgressDashboard)))
							// FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId)
							// .child(sKeyProgressDashboard).removeValue();
							// SearchBookService.addInterestBookToDashboard(sUserId, stringTextSeah,
							// bookId);
							//
							//
							// return ;// sArrBookId;
							//
							// }
							//
							// }
							//
							// @Override
							// public void onCancelled(DatabaseError error) {
							// // TODO Auto-generated method stub
							//
							// }
							// });

							// if (!Cache.mBookName.containsKey(textSearchEncode)) {
							//
							//
							// }
							// else
							// {
							//
							//
							// String sArrBookId = Cache.mBookName.get(textSearchEncode);
							//
							// if (sArrBookId.contains(BookService.keySplit)) {
							// String[] arrBookId = sArrBookId.split(BookService.keySplit);
							// return arrBookId[0];
							//
							// }
							// return sArrBookId;
							// }

							// create book;

							// }
							// }
						}

					}
				}
			}

		}
		if ((fFind == 0)) {
			FirebaseDatabase.getInstance().getReference().child("dashboard").child(sUserId).child(sKeyProgressDashboard)
					.removeValue();

			SearchBookService.addInterestNotFoundBookToDashboard(sUserId, stringTextSeah);
			return;
		}
		// return ;//''null;
	}

	private static List<String> googleGetLink(String stringTextSeah, String sLinkSite) throws IOException {
		List<String> arrLink = new ArrayList<>();

		Document doc;
		String urlGoogle = "https://www.google.com/search?client=opera&q=" + stringTextSeah + " " + sLinkSite
				+ "&num=75&sourceid=opera&ie=UTF-8&oe=UTF-8";
		System.out.print(urlGoogle);

		Connection.Response res = Jsoup.connect(urlGoogle).userAgent(Constants.CRAWLER_USER_AGENT).execute();
		doc = res.parse();

		Elements element = doc.select("div.r");

		for (Element elements : element) {
			Element element_1 = elements.getElementsByTag("a").first();

			String urllink = element_1.attr("href");
			{
				if ((urllink != null) && (!"".equals(urllink)))
					arrLink.add(urllink);

			}
		}
		return arrLink;
	}

	private static String minhkhaiStore(String sISBN, String sUserId) {
		// 786047221585) {
		// String a =
		// "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=8935251406214";
		// a = "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=9786047221585";
		// url = new URL(a);
		// URLConnection conn = url.openConnection();
		//
		// // open the stream and put it into BufferedReader
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(conn.getInputStream(), "UTF-8"));
		String bookId = "BK" + sISBN;

		try {
			// Book book = new Book();
			// book.getMapsBooks(book);

			String sUrl = "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=" + sISBN;
			System.out.println(sUrl);
			Document doc = Jsoup.connect(sUrl).get();
			int iCaption = 0;
			String[] a = new String[15];
			HashMap<String, String> mapsBooksField = new HashMap<String, String>();

			// Tác Giả
			a[iCaption] = "Tên sách: ";// maps.put("name", book.getName());
			mapsBooksField.put(a[iCaption], "name");
			iCaption = iCaption + 1;
			a[iCaption] = "Tác giả: ";// maps.put("author_id", book.getAuthor_id());maps.put("author_name",
			// book.getAuthor_id());
			mapsBooksField.put(a[iCaption], "author_name");

			iCaption = iCaption + 1;
			a[iCaption] = "Tác Giả: ";// maps.put("author_id", book.getAuthor_id());maps.put("author_name",
			// book.getAuthor_id());
			mapsBooksField.put(a[iCaption], "author_name");

			iCaption = iCaption + 1;
			a[iCaption] = "Dịch giả: ";// maps.put("author_id", book.getAuthor_id());maps.put("translator",
			// book.getAuthor_id());
			// translator
			mapsBooksField.put(a[iCaption], "translator");
			iCaption = iCaption + 1;
			a[iCaption] = "Người dịch: ";
			mapsBooksField.put(a[iCaption], "translator");
			iCaption = iCaption + 1;
			a[iCaption] = "Người Dịch: ";
			mapsBooksField.put(a[iCaption], "translator");
			// Người Dịch:
			// Người dịch:
			iCaption = iCaption + 1;
			a[iCaption] = "Thể loại: ";// maps.put("category_id", book.getCategory_id());
			mapsBooksField.put(a[iCaption], "category_name");
			iCaption = iCaption + 1;
			a[iCaption] = "ISBN: ";
			mapsBooksField.put(a[iCaption], "isbn");
			iCaption = iCaption + 1;
			a[iCaption] = "Xuất bản: ";
			mapsBooksField.put(a[iCaption], "publishing_year");
			iCaption = iCaption + 1;
			a[iCaption] = "Trọng lượng: ";
			mapsBooksField.put(a[iCaption], "weight");
			iCaption = iCaption + 1;
			a[iCaption] = "NXB: ";
			mapsBooksField.put(a[iCaption], "publisher");
			iCaption = iCaption + 1;
			a[iCaption] = "Số trang: ";
			mapsBooksField.put(a[iCaption], "page_number");
			iCaption = iCaption + 1;
			a[iCaption] = "khổ: ";
			mapsBooksField.put(a[iCaption], "size");
			iCaption = iCaption + 1;
			a[iCaption] = "Khổ: ";
			mapsBooksField.put(a[iCaption], "size");
			// Khổ:
			iCaption = iCaption + 1;
			a[iCaption] = "Giá bìa: ";// maps.put("cover_price", book.getcover_price());
			mapsBooksField.put(a[iCaption], "cover_price_1");
			// iCaption = iCaption + 1;
			// a[iCaption] = "Giá bán: ";// maps.put("price", book.getPrice());

			// mapsBooksField.put(a[iCaption], "price_1");

			// mapsBooksField.put(a[12], "cover_price");
			// maps.put("description", book.getDescription());
			// mapsBooksField.put(a[5], "ISBN");
			// maps.put("image", book.getImage());
			HashMap<String, Object> maps = new HashMap<>();

			// maps.put("create_time", book.getCreate_time());
			// maps.put("creator_id", book.getCreator_id());
			// maps.put("status", book.getStatus());
			// maps.put("count", book.getCount());
			//

			//
			// for (int i = 0; i < a.length; i++) {
			// String string = a[i];
			//
			// }
			String[] s2 = null;
			// Element body = document.body();
			Elements elements = doc.select("td");
			System.out.println(elements);
			String sText = "";
			boolean bBreak = false;
			for (Element element : elements) {
				sText = element.text();

				if (bBreak)
					break;
				if (sText.contains("ISBN: " + sISBN)) {
					bBreak = true;
					System.out.println(sText);
					// s1 = sText.split("Tác giả:");
					String sText1 = sText;
					// System.out.println("Tên sách: " + s1[0]);
					int i = 0;
					int j = 0;
					for (i = 0; i < a.length - 1; i++) {
						String stringEl = a[i + 1];
						// if(s1.length>1)
						if (sText1.contains(stringEl)) {

							s2 = sText1.split(stringEl);
							String key = mapsBooksField.get(a[j]);
							String value = s2[0];
							System.out.println(a[j] + s2[0]);
							value = value.trim();
							maps.put(key, value);
							sText1 = s2[1];
							j = i + 1;
						}

						// getEl(s1, stringEl);

					}

					String key = mapsBooksField.get(a[i]);
					String value = s2[1];
					System.out.println(a[i] + s2[1]);
					value = value.trim();
					maps.put(key, value);

				}

				// System.out.println(elements.text());

			}
			if (maps.size() == 0)
				return null;
			System.out.println("description");
			System.out.println(sText);

			maps.put("plot_overview", sText);
			// maps.put("price", 1);
			maps.put("cover_price", 15);

			maps.put("image", "http://minhkhai.vn/hinhlon/" + sISBN + ".JPG");

			maps.put("source", sUrl);
			maps.put("status", 0);
			maps.put("count", 1);
			maps.put("create_time", System.currentTimeMillis());
			maps.put("creator_id", sUserId);

			String name = String.valueOf(maps.get("name"));

			if (name.contains("(")) {
				String sNames = name.substring(0, name.indexOf("("));

				maps.remove("name");
				sNames = sNames.trim();
				maps.put("name", sNames);

				if (name.contains("(Hết hàng)"))
					name = name.replaceAll("\\(Hết hàng\\)", "");

				name = name.trim();
				if (!name.equals(sNames))
					maps.put("name_ext", name);
			}

			//
			// if (sName_1.contains("(Hết hàng)"))
			// sName_1 = sName_1.replaceAll("\\(Hết hàng\\)", "");
			//
			// if (sName_1.contains("(TÁI BẢN - BÌA CỨNG)"))
			// sName_1 = sName_1.replaceAll("\\(TÁI BẢN - BÌA CỨNG\\)", "");
			// if (sName_1.contains("(BC)"))
			// sName_1 = sName_1.replaceAll("\\(BC\\)", "");
			//
			// sName_1 = sName_1.trim();
			// maps.put("name", sName_1);

			// Object price_1 = maps.get("price_1");
			// if (price_1 != null) {
			// String price_11 = String.valueOf(price_1);
			// String price_111 = StringUtil.removeAccent(price_11);
			// String price_1111 = price_111.split("D")[0];
			// if (price_1111.contains(","))
			//
			// {
			// price_1111 = price_1111.replaceAll(",", "");
			// }
			// long price = Long.parseLong(price_1111.trim());
			// maps.put("price", price);
			// maps.remove("price_1");
			// } else
			// maps.put("price", 0);
			Object cover_price_1 = maps.get("cover_price_1");
			if (cover_price_1 != null) {
				String price_11 = String.valueOf(cover_price_1);
				String price_111 = StringUtil.removeAccent(price_11);
				String price_1111 = price_111.split("D")[0];
				if (price_1111.contains(",")) {
					price_1111 = price_1111.replaceAll(",", "");
				}
				long cover_price = Long.parseLong(price_1111.trim());
				maps.put("cover_price", cover_price);
				maps.remove("cover_price_1");
			} else
				maps.put("cover_price", 0);

			if (maps.containsKey("page_number")) {
				Object page_number = maps.get("page_number");
				maps.remove("page_number");
				String sPage_number = page_number.toString();
				String sPage_number10 = "";
				for (int i = 0; i < sPage_number.length(); i++) {
					char cha = sPage_number.charAt(i);
					if ((cha >= 48) && (cha <= 57)) {
						sPage_number10 = sPage_number10 + cha;
					}
				}
				maps.put("page_number", Long.parseLong(sPage_number10));
			}

			createCateg(maps);

			createAuthor(maps);

			// FirebaseDatabase.getInstance().getReference().child("books_items_noa")
			// .child(book.getBook_id()).removeValue();

			DatabaseReference childBooks = FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_BOOKS);
			childBooks.child(bookId).updateChildren(maps);
			return bookId;
			// Elements elements1 = doc.select("p");
			// System.out.println(elements1.toString());

			// System.out.println(body);

			// document.select("p").forEach(System.out::println);
			// doc.select("td").forEach(System.out::println);
			// document.select("b").forEach(System.out::println);
			//
			// String description = document.select("meta[name=Giá bán:
			// ]").get(0).attr("content");
			// System.out.println("Meta description : " + description);
		} catch (Exception e) {

			e.printStackTrace();
			// return false;
		}
		// System.out.println("description");
		return null;
	}

	private static void createAuthor(HashMap<String, Object> maps) {
		Object author_name = maps.get("author_name");
		if (author_name != null) {
			String textAuthor_name = StringUtil.removeAccent(String.valueOf(author_name));
			String author_nameID = textAuthor_name;// Cache.mAuthorName.get(textAuthor_name);

			if (!"".equals(author_nameID)) {

				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_AUTHORS).child(author_nameID)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								if (!snapshot.exists()) {
									HashMap<String, Object> mapsAuthor = new HashMap<>();

									mapsAuthor.put("name", author_name);
									FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_AUTHORS)
											.child(author_nameID).updateChildren(mapsAuthor);

								}

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

				maps.put("author_id", author_nameID);
				return;
			}

		}
	}

	private static void createCateg(HashMap<String, Object> maps) {
		Object category_name = maps.get("category_name");
		if (category_name != null) {

			String textCateg = StringUtil.removeAccent(String.valueOf(category_name));
			String categID = textCateg;

			if (!"".equals(categID)) {

				FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_CATEGORIES).child(categID)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								if (!snapshot.exists()) {
									HashMap<String, Object> mapsAuthor = new HashMap<>();

									HashMap<String, Object> mapsCateg = new HashMap<>();
									mapsCateg.put("name", String.valueOf(category_name));

									FirebaseDatabase.getInstance().getReference(RefFireBaseBook.BOOK_DATA_CATEGORIES)
											.child(categID).updateChildren(mapsCateg);

								}

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

				maps.put("category_id", categID);

				return;
			}

		}
	}

	// private static void getEl(String[] s1, String stringEl) {
	// String[] s2 = s1[1].split(stringEl);
	// System.out.println(stringEl + s2[0]);
	// }
}
