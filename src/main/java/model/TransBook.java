package model;

public class TransBook {
 
		 private String book_id;
		 private String book_name;
		 private String owner_id;
		 private String owner_name;
		 
		 private float rent_date;
		 private String renter_id;
		 private String renter_name;
		 private String book_item_id;
		 
			public void setBook_item_id(String book_item_id) {
				this.book_item_id = book_item_id;
			}
			
			
			 
			public String getBook_item_id() {
				return book_item_id;
			}

		 private float status;

			private boolean free_loan;
			  public void setFree_loan(boolean free_loan) {
			        this.free_loan= free_loan;
			    }
				
			    public boolean getFree_loan() {
					// TODO Auto-generated method stub
				return  this.free_loan;
			}
		 

		 // Getter Methods 

		 public String getBook_id() {
		  return book_id;
		 }

		 public String getBook_name() {
		  return book_name;
		 }

		 public String getOwner_id() {
		  return owner_id;
		 }

		 public String getOwner_name() {
		  return owner_name;
		 }

		 public float getRent_date() {
		  return rent_date;
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

	
	
		 
	
		 
		 

		 public float getStatus() {
		  return status;
		 }

		 // Setter Methods 

		 public void setBook_id(String book_id) {
		  this.book_id = book_id;
		 }

		 public void setBook_name(String book_name) {
		  this.book_name = book_name;
		 }

		 public void setOwner_id(String owner_id) {
		  this.owner_id = owner_id;
		 }

		 public void setOwner_name(String owner_name) {
		  this.owner_name = owner_name;
		 }

		 public void setRent_date(float rent_date) {
		  this.rent_date = rent_date;
		 }

		 public void setRenter_id(String renter_id) {
		  this.renter_id = renter_id;
		 }
		 
		 
	
		 
		 
		 
		 
		 private String renter_avatar;
		 public void setRenter_avatar(String renter_avatar) {
		  this.renter_avatar = renter_avatar;
		  
		 }
		 
		 public String getRenter_avatar() {
			  return renter_avatar;
			 }
		 

		 public void setStatus(float status) {
		  this.status = status;
		 }
		
	public TransBook() {}

}
