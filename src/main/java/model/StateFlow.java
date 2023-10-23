package model;

import java.io.Serializable;

public class StateFlow  implements Serializable{

	public StateFlow() {
		// TODO Auto-generated constructor stub
	}
	private String borrow_next_state;
	 private String button;
	 private String loan_next_state;
	 private String next_state;
	 private String title;
	private String purchase_next_state;
	private String sale_next_state;


	 // Getter Methods 

	 public String getBorrow_next_state() {
	  return borrow_next_state;
	 }

	 public String getButton() {
	  return button;
	 }

	 public String getLoan_next_state() {
	  return loan_next_state;
	 }

	 public String getNext_state() {
	  return next_state;
	 }

	 public String getTitle() {
	  return title;
	 }

	 // Setter Methods 

	 public void setBorrow_next_state(String borrow_next_state) {
	  this.borrow_next_state = borrow_next_state;
	 }

	 public void setButton(String button) {
	  this.button = button;
	 }

	 public void setLoan_next_state(String loan_next_state) {
	  this.loan_next_state = loan_next_state;
	 }

	 public void setNext_state(String next_state) {
	  this.next_state = next_state;
	 }

	 public void setTitle(String title) {
	  this.title = title;
	 }
	 public void setPurchase_next_state(String purchase_next_state) {
		  this.purchase_next_state = purchase_next_state;
		 }
	 public void setSale_next_state(String sale_next_state) {
		  this.sale_next_state = sale_next_state;
		 }
	public String getPurchase_next_state() {
		// TODO Auto-generated method stub
		return purchase_next_state;
	}

	public String getSale_next_state() {
		// TODO Auto-generated method stub
		return sale_next_state;
	}
	}