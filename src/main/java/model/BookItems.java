package model;

import java.util.Date;

public class BookItems {
	private static final String STATE_FLOW_CHUA_NHAN = "chau_nhan";
	private static final String STATE_FLOW_CHUA_GIAO = "chua_giao";

	public static final String CREATE_TIME = "create_time";
	public static final String FREE_lOAN = "free_loan";
	public static final String PRICE = "price";
	public static final String STATUS = "status";
	public static final String QUANTITY = "quantity";
	private String owner_id;
	private String owner_name;
	private String saving_percentage;
	private Integer numdays_borrow;

	private String return_date;

	private String borrowed_date;

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public String getBorrowed_date() {
		return borrowed_date;
	}

	public void setBorrowed_date(String borrowed_date) {
		this.borrowed_date = borrowed_date;
	}

	public Integer getNumdays_borrow() {
		return numdays_borrow;
	}

	public void setNumdays_borrow(Integer numdays_borrow) {
		this.numdays_borrow = numdays_borrow;
	}

	public String getSaving_percentage() {
		return saving_percentage;
	}

	public void setSaving_percentage(String saving_percentage) {
		this.saving_percentage = saving_percentage;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public void setRenter_id(String renter_id) {
		this.renter_id = renter_id;
	}

	public String getRenter_id() {
		return renter_id;
	}

	public String getRenter_name() {
		return renter_name;
	}

	public void setRenter_name(String renter_name) {
		this.renter_name = renter_name;
	}

	private String renter_id;
	private String renter_name;

	//// @Exclude
	protected String user_id;

	protected String book_id;

	protected String book_item_id;

	protected Double  create_time;
	protected Boolean free_loan;
	protected Double  purchase_price;
	protected Integer  quantity;
	protected Double  sale_price;
	protected Integer status;
	protected String note;
	protected String state_flow;

	// Getter Methods

	public String getState_flow() {
		return state_flow;
	}

	// Setter Methods

	public void setState_flow(String state_flow) {
		this.state_flow = state_flow;
	}

	// Getter Methods

	public String getNote() {
		return note;
	}

	// Setter Methods

	public void setNote(String note) {
		this.note = note;
	}

	// Getter Methods
	//// @Exclude
	public String getBook_id() {
		return book_id;
	}

	//// @Exclude
	public String getBook_item_id() {
		return book_item_id;
	}

	public Double  getCreate_time() {
		return create_time;
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

	//// @Exclude
	public String getUser_id() {
		return user_id;
	}

	// Setter Methods
	//// @Exclude
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	//// @Exclude
	public void setBook_item_id(String book_item_id) {
		this.book_item_id = book_item_id;
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

	//// @Exclude
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	//// @Exclude
	public void setIs_selected(Boolean is_selected) {
		this.is_selected = is_selected;
	}

	//// @Exclude
	public Boolean getIs_selected() {
		return is_selected;
	}

	//// @Exclude
	private Boolean is_selected;

}