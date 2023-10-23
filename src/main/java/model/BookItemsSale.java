package model;

import com.google.firebase.database.Exclude;

public class BookItemsSale extends BookItems {

	public static final String CREATE_TIME = "create_time";
	public static final String FREE_lOAN = "free_loan";
	public static final String PRICE = "price";
	public static final String STATUS = "status";
	public static final String QUANTITY = "quantity";
	
	//@Exclude
	private String user_id;
	//@Exclude
	private String book_id;
	//@Exclude
	private String book_item_id;

	private Double  create_time;
	private Boolean free_loan;
	private Double  purchase_price;
	private Integer  quantity;
	private Double  sale_price;
	private Integer status;
	private String note;
	
 
	 public void setRenter_id(String renter_id) {
		  this.renter_id = renter_id;
		 }
	 public String getRenter_id() {
		  return renter_id;
		 }

		 public String getRenter_name() {
		  return renter_name;
		 }
		 
		 public void setRenter_name(String renter_name ) {
			  this.renter_name=renter_name;
			 }
	 
	 private String renter_id;
	 private String renter_name;
	private String  purchase_date;
		 
	
		 

	 // Getter Methods 

	 public String getNote() {
	  return note;
	 }

	 // Setter Methods 

	 public void setNote(String note) {
	  this.note = note;
	 }

	// Getter Methods
	//@Exclude
	public String getBook_id() {
		return book_id;
	}

	//@Exclude
	public String getBook_item_id() {
		return book_item_id;
	}

	public Double  getCreate_time() {
		return create_time;
	}

	public String  getPurchase_date() {
		// TODO Auto-generated method stub
		return purchase_date;
	}
	
	
	public Boolean getFree_loan() {
		return free_loan;
	}

	public Double  getPurchase_price() {
		return purchase_price;
	}

	public Integer  getQuantity() {
		return quantity;
	}

	public Double  getSale_price() {
		return sale_price;
	}

	public Integer getStatus() {
		return status;
	}

	//@Exclude
	public String getUser_id() {
		return user_id;
	}

	// Setter Methods
	//@Exclude
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	//@Exclude
	public void setBook_item_id(String book_item_id) {
		this.book_item_id = book_item_id;
	}
	
	
	public void setPurchase_date(String  purchase_date) {
		// TODO Auto-generated method stub
		this.purchase_date=purchase_date;
	}
	

	public void setCreate_time(Double  create_time) {
		this.create_time = create_time;
	}

	public void setFree_loan(Boolean free_loan) {
		this.free_loan = free_loan;
	}

	public void setPurchase_price(Double  purchase_price) {
		this.purchase_price = purchase_price;
	}

	public void setQuantity(Integer  quantity) {
		this.quantity = quantity;
	}

	public void setSale_price(Double  sale_price) {
		this.sale_price = sale_price;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	//@Exclude
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}