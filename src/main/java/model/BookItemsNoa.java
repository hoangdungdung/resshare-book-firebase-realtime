package model;

public class BookItemsNoa {
	private Float create_date;
	private String token;
	private String user_id;
	private Books book;
	private BookItems book_item;
	private String  response_key;

	
	 public String getResponse_key() {
		  return response_key;
		 }

	 public void setResponse_key(String response_key) {
		  this.response_key = response_key;
		 }

	// Getter Methods

	public Books getBook() {
		return book;
	}

	public BookItems getBook_item() {
		return book_item;
	}

	// Setter Methods

	public void setBook(Books book) {
		this.book = book;
	}

	public void setBook_item(BookItems book_item) {
		this.book_item = book_item;
	}
	
	 public Float getCreate_date() {
		  return create_date;
		 }

		 public String getToken() {
		  return token;
		 }

		 public String getUser_id() {
		  return user_id;
		 }


		 public void setCreate_date(Float create_date) {
		  this.create_date = create_date;
		 }

		 public void setToken(String token) {
		  this.token = token;
		 }

		 public void setUser_id(String user_id) {
		  this.user_id = user_id;
		 }
}