package model;

import com.google.firebase.database.Exclude;

public class BookUsers {

	@Exclude
	private String book_id;
	@Exclude
	private String user_id;
	@Exclude
	private String book_user_id;
	private String book_item_id;
	private boolean free_loan;
	private float sale_price;
	private String avatar;
	private String user_name;

	// Getter Methods
	@Exclude
	public String getBook_id() {
		return book_id;
	}

	@Exclude
	public String getUser_id() {
		return user_id;
	}

	@Exclude
	public String getBook_user_id() {
		return book_user_id;
	}

	public String getBook_item_id() {
		return book_item_id;
	}

	public boolean getFree_loan() {
		return free_loan;
	}

	public float getSale_price() {
		return sale_price;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getUser_name() {
		return user_name;
	}

	// Setter Methods
	@Exclude
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	@Exclude
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Exclude
	public void setBook_user_id(String book_user_id) {
		this.book_user_id = book_user_id;
	}

	public void setBook_item_id(String book_item_id) {
		this.book_item_id = book_item_id;
	}

	public void setFree_loan(boolean free_loan) {
		this.free_loan = free_loan;
	}

	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}