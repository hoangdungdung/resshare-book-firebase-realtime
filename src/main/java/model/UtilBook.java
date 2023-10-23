package model;

import java.util.HashMap;

import com.google.firebase.database.Exclude;

public class UtilBook {

	@Exclude
	private String book_id;
	private String author_name;
	
	private String author_id;
	private String category_id;
	private String category_name;
	private String creator_id;
	private String description;
	private String image;
	private String name;
	private long price;
	private long price_cover;
	private int status;
	private int views_count;
	private long create_time;
	private int count;
	
	
	private String ISBN;
	
	private String public_date;
	private String weight;
	private String page_number;
	private String size;
	private String translator_name;
	private boolean free_loan;
	 
    public void setFree_loan(boolean free_loan) {
        this.free_loan= free_loan;
    }
	
    public boolean getFree_loan() {
		// TODO Auto-generated method stub
		return  this.free_loan;
	}
	
//	@Exclude
//	public static HashMap<String, Object> getMapsBooks1( Books book) {
//		HashMap<String, Object> maps = new HashMap<>();
//		maps.put("author_id", book.getAuthor_id());
//		maps.put("category_id", book.getCategory_id());
//		maps.put("description", book.getDescription());
//		maps.put("image", book.getImage());
//		maps.put("name", book.getName());
//		maps.put("price", book.getPrice());
//		maps.put("price_cover", book.getPrice_cover());
//		maps.put("create_time", book.getCreate_time());
//		maps.put("creator_id", book.getCreator_id());
//		maps.put("status", book.getStatus());
//		maps.put("count", book.getCount());
//		maps.put("ISBN",book.ISBN);
//		maps.put("category_name",book.getCategory_name());
//		maps.put("public_date",book.public_date);
//		maps.put("weight",book.weight);
//		maps.put("page_number",book.page_number);
//		maps.put("size",book.size);
//		maps.put("translator_name",book.translator_name);
//		maps.put("author_name",book.getAuthor_name());
//		maps.put("free_loan",book.getFree_loan());
//		return maps;
//
//	}
	
	@Exclude
	public static HashMap<String, Object> getMapsBookItems(UtilBook book) {
		HashMap<String, Object> maps = new HashMap<>();
	 
		maps.put("price", book.getPrice());
	 
		maps.put("create_time", book.getCreate_time());
		 
		maps.put("status", book.getStatus());
		 
		maps.put("free_loan",book.getFree_loan());
		maps.put("number",book.getCount());
		return maps;

	}
	
 



	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public long getPrice_cover() {
		return price_cover;
	}

	@Exclude
	public String getBook_id() {
		return book_id;
	}

	@Exclude
	public void setBookId(String id) {
		this.book_id = id;
	}

	@Exclude
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	@Exclude
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	@Exclude
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	
	@Exclude
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	public long getCreate_time() {
		return create_time;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public String getCategory_name() {
		return category_name;
	}
	 

	public long getPrice() {
		return price;
	}
	
	public long getPriceCover() {
		return price_cover;
	}
	 

	public int getStatus() {
		return status;
	}

	public int getViews_count() {
		return views_count;
	}

	public enum BookState {
		NO_AUTHORISE(0), AUTHORISED(1), REJECTED(2);

		private int key;

		BookState(int state) {
			this.key = state;
		}

		public int getKey() {
			return key;
		}

		public static BookState getState(int key) {
			for (BookState status : BookState.values()) {
				if (status.getKey() == key)
					return status;
			}
			return NO_AUTHORISE;
		}
	}

	public static Object getMapsBooksNotFound(String key) {
		HashMap<String, Object> maps = new HashMap<>();
		 
		maps.put("name", key);
	 
		return maps;

	}

	public static HashMap<String, Object> getMapsBooksName( Books book) {
		HashMap<String, Object> maps = new HashMap<>();
		 
		maps.put("book_name", book.getName());
	 
	//	maps.put("image", book.getImage());
 
		return maps;
	}
	
	
	
	

 

}
