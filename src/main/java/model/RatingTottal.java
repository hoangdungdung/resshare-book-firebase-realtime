package model;

import com.google.firebase.database.Exclude;

public class RatingTottal {
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
	private Double rating_tottal ;
	public void setRatingTottal(Double rating_tottal) {
		this.rating_tottal = rating_tottal;
	}
	public Double getRatingTottal() {
		return rating_tottal;
	} 
	 
}