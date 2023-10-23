package model;

import com.google.firebase.database.Exclude;

public class Rating {
	
    public void setKey(String key) {
		this.key=key;
    }
	public String getKey() {
		return key;
	}


	public void setRating_database(String ratingDatabase) {
		this.rating_database=ratingDatabase;
	}


	public String getRating_database() {
		return  rating_database;
	}

	public void setObject_database(String object_database) {
		this. object_database = object_database;
	}
	public String getObject_database() {
		return  this.object_database;
	}
	private String key;
	private String rating_database;
	private String object_database;
	private Long rating_number;
	private Double rating ;


	public void setRating_number(Long rating_number) {
		this.rating_number = rating_number;
	}
	public Long getRating_number() {
		return rating_number;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Double getRating() {
		return rating;
	} 
	
	


	private String user_id;
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}

}