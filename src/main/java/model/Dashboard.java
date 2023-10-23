package model;

import java.util.HashMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.resshare.book.RefFireBaseBook;

public class Dashboard {

	public enum DashboardType {
		DEFAULT("message.default"), INTERESTING("message.interesting"), INFO("message.info"), AUTHORIZER(
				"message.authorizer"), BOOK_CREATED_STATUS("message.created.book.status"), BOOK_INTERESTING(
						"message.book.interesting"), BOOK_HOT_VIEW("message.book.hotview"), BOOK_NEW_PUBLIC(
								"message.book.newpublic"), BOOK_PROMOTION("message.book.promotion"),  SEARCH_USER("message.user"),
	    SEARCH_AUTHOR("message.author"), SEARCH_CATEGOTY("message.category"), REQUEST_RENT("message.request.rent"),AcceptorRentBook("message.acceptor.rent.book"),
	    ReciverRentBook("message.reciver.rent.book"), REQUEST_SAlE("message.request.sale"),AcceptorSaleBook("message.acceptor.sale.book"),
	    ReciverSaleBook("message.reciver.sale.book"),
	    
	    CHANGE_STATUS_PURCHASE("message.change.status.purchase.book" ),
	    CHANGE_STATUS_SAlE("message.change.status.sale.book" ),
	    CHANGE_STATUS_LOAN("message.change.status.loan.book"),  
	    CHANGE_STATUS_BORROW("message.change.status.borrow.book"),
	    
	    PROGRESS("message.progress"),
	    SEARCH_PHONE_NUMBER("message.com.phone.number"),
	     
	    ;

		private String type;

		DashboardType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	@Exclude
	private String id;

	private String book_id;
	private long create_time;
	private String type;
	private String user_id;
	private int book_created_status;
	private String search_book_key;

	public Dashboard() {
	}

	public static HashMap<String, Object> getMapsAuthor(Books book, String user_id) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book.getBook_id());
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.AUTHORIZER.getType());
		maps.put("user_id", user_id);
		maps.put("book_info", book.getBook_id());
		return maps;
	}
	
	
	public static HashMap<String, Object> getMapsBookCreatedStatus(String  book_id, int bookCreatedStatus) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("book_id", book_id);
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.BOOK_CREATED_STATUS.getType());
		maps.put("book_created_status", bookCreatedStatus);
	//	maps.put("book_info", Book.getMapsBooks(book));
		return maps;
	}

	public static HashMap<String, Object> getMapsBookInterest(String key, String book_id) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.BOOK_INTERESTING.getType());
		maps.put("book_id",book_id);
	//	maps.put("book_info", Book.getMapsBooks(book));
		maps.put("text", key);
		return maps;
	}
	public static HashMap<String, Object> getMapsBookInterest(String key, Books book) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.BOOK_INTERESTING.getType());
		maps.put("book_id", book.getBook_id());
	//	maps.put("book_info", Book.getMapsBooks(book));
		maps.put("text", key);
		return maps;
	}
	

	public static HashMap<String, Object> getMapsBookInterestNotFound(String key) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());

		maps.put("text", "Không tìm thấy quyển sách nào có tên: " + key);
		return maps;
	}
	public static HashMap<String, Object> getMapsProgress() {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.PROGRESS.getType());

		
		return maps;
	}
	
	
	
	public static HashMap<String, Object> getMapsUserNotFound(String key) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());

		maps.put("text", "Không tìm thấy người dùng nào có tên: " + key);
		return maps;
	}
	
	

	@Exclude
	public String getId() {
		return id;
	}

	public String getBook_id() {
		return book_id;
	}

	public long getCreate_time() {
		return create_time;
	}

	public String getType() {
		return type;
	}

	public String getUser_id() {
		return user_id;
	}

	public int getBook_created_status() {
		return book_created_status;
	}

	public void setBook_created_status(int book_created_status) {
		this.book_created_status = book_created_status;
	}

	public static HashMap<String, Object> getMapsUserInterest(String textSearch, User user) {
		// TODO Auto-generated method stub
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.SEARCH_USER.getType());
		maps.put("user_info", User.getMapsUsers(user));
		maps.put("text", textSearch);
		return maps;
	}

	public static HashMap<String, Object> getMapsAuthorInterest(String textSearch, Author book) {
		// TODO Auto-generated method stub
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.SEARCH_AUTHOR.getType());
		maps.put("author_info", Author.getMapsAuthor(book));
		maps.put("text", textSearch);
		return maps;
	}

	public static HashMap<String, Object> getMapsAuthorNotFound(String textSearch) {

		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());

		maps.put("text", "Không tìm thấy tác giả nào có tên: " + textSearch);
		return maps;
	
	}

	public static HashMap<String, Object> getMapsCategoryInterest(String textSearch, Category category) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.SEARCH_CATEGOTY.getType());
		maps.put("category_info", Category.getMapsAuthor(category));
		maps.put("text", textSearch);
		return maps;
	}

	public static HashMap<String, Object> getMapsCategoryNotFound(String textSearch) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());

		maps.put("text", "Không tìm thấy thể loại nào có tên: " + textSearch);
		return maps;
	}

	public static HashMap<String, Object> getMapsBomPhoneNunberNotFound(String textSearch) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.DEFAULT.getType());

		maps.put("text", "Không tìm thấy post nào có sdt: " + textSearch);
		return maps;
	}
	public static HashMap<String, Object> getMapsBomPhoneNunberMessage(String textSearch) {
		HashMap<String, Object> maps = new HashMap<>();
		maps.put("create_time", ServerValue.TIMESTAMP);
		maps.put("type", DashboardType.SEARCH_PHONE_NUMBER.getType());
		maps.put("reference", RefFireBaseBook.SEARCHING_COM_Phone_number);

		maps.put("text",  textSearch);
		return maps;
	}

	public static void addPhonNumberToDashboard(String userId, String text) {
	 
		HashMap<String, Object> dashboard = getMapsBomPhoneNunberMessage(text);
		FirebaseDatabase.getInstance().getReference().child("dashboard").child(userId).child(text)
				.updateChildren(dashboard);
	}

}
