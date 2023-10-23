package model;

import com.google.firebase.database.Exclude;

public class AcceptBook {
	 private float accep_date;
	 private String acceptor_id;
	 private String acceptor_name;
	 private String book_id;
	 private String book_name;
	 private String dashboar_id;
	 private boolean free_loan;
	 private String receiver_id;
	 private String receiver_name;
	 private float status;
	private String book_item_id;
	 
		public void setBook_item_id(String book_item_id) {
			this.book_item_id = book_item_id;
		}
		
		
		 
		public String getBook_item_id() {
			return book_item_id;
		}

	 // Getter Methods 

	 public float getAccep_date() {
	  return accep_date;
	 }

	 public String getAcceptor_id() {
	  return acceptor_id;
	 }

	 public String getAcceptor_name() {
	  return acceptor_name;
	 }

	 public String getBook_id() {
	  return book_id;
	 }

	 public String getBook_name() {
	  return book_name;
	 }

	 public String getDashboar_id() {
	  return dashboar_id;
	 }

	 public boolean getFree_loan() {
	  return free_loan;
	 }

	 public String getReceiver_id() {
	  return receiver_id;
	 }

	 public String getReceiver_name() {
	  return receiver_name;
	 }

	 public float getStatus() {
	  return status;
	 }

	 // Setter Methods 

	 public void setAccep_date(float accep_date) {
	  this.accep_date = accep_date;
	 }

	 public void setAcceptor_id(String acceptor_id) {
	  this.acceptor_id = acceptor_id;
	 }

	 public void setAcceptor_name(String acceptor_name) {
	  this.acceptor_name = acceptor_name;
	 }

	 public void setBook_id(String book_id) {
	  this.book_id = book_id;
	 }

	 public void setBook_name(String book_name) {
	  this.book_name = book_name;
	 }

	 public void setDashboar_id(String dashboar_id) {
	  this.dashboar_id = dashboar_id;
	 }

	 public void setFree_loan(boolean free_loan) {
	  this.free_loan = free_loan;
	 }

	 public void setReceiver_id(String receiver_id) {
	  this.receiver_id = receiver_id;
	 }

	 public void setReceiver_name(String receiver_name) {
	  this.receiver_name = receiver_name;
	 }

	 public void setStatus(float status) {
	  this.status = status;
	 }
	
	public AcceptBook() {
		// TODO Auto-generated constructor stub
	}
	

}
