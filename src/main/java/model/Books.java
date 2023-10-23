package model;

import com.google.firebase.database.Exclude;

public class Books {
//	private Long rating_number;
//	private Double rating ;
//
//
//	public void setRating_number(Long rating_number) {
//		this.rating_number = rating_number;
//	}
//	public Long getRating_number() {
//		return rating_number;
//	}
//
//	public void setRating(Double rating) {
//		this.rating = rating;
//	}
//	public Double getRating() {
//		return rating;
//	} 
//	private Double rating_tottal ;
//	public void setRatingTottal(Double rating_tottal) {
//		this.rating_tottal = rating_tottal;
//	}
//	public Double getRatingTottal() {
//		return rating_tottal;
//	} 
	@Exclude
	private String book_id;

	private String ISBN;
	private String author_id;
	private String author_name;

	private String category_id;
	private String category_name;
	private Long cover_price;
	private float create_time;
	private String creator;
	private String image;
	private String link;
	private String name;
	private float number_owners;
	private Long page_number;
	private String plot_overview;
	private String public_date;
	private String publisher_name;
	private String size;
	private float status;
	private String translator_name;
	private String weight;

	// Getter Methods

	public String getISBN() {
		return ISBN;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public String getAuthor_name() {
		return author_name;
	}

	@Exclude
	public String getBook_id() {
		return book_id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public Long getCover_price() {
		return cover_price;
	}

	public float getCreate_time() {
		return create_time;
	}

	public String getCreator() {
		return creator;
	}

	public String getImage() {
		return image;
	}

	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public float getNumber_owners() {
		return number_owners;
	}

	public Long getPage_number() {
		return page_number;
	}

	public String getPlot_overview() {
		return plot_overview;
	}

	public String getPublic_date() {
		return public_date;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public String getSize() {
		return size;
	}

	public float getStatus() {
		return status;
	}

	public String getTranslator_name() {
		return translator_name;
	}

	public String getWeight() {
		return weight;
	}

	// Setter Methods

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	@Exclude
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	
	

	
	
	
	
	
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public void setCover_price(Long cover_price) {
		this.cover_price = cover_price;
	}

	public void setCreate_time(float create_time) {
		this.create_time = create_time;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber_owners(float number_owners) {
		this.number_owners = number_owners;
	}

	public void setPage_number(Long page_number) {
		this.page_number = page_number;
	}

	public void setPlot_overview(String plot_overview) {
		this.plot_overview = plot_overview;
	}

	public void setPublic_date(String public_date) {
		this.public_date = public_date;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setStatus(float status) {
		this.status = status;
	}

	public void setTranslator_name(String translator_name) {
		this.translator_name = translator_name;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	private int views_count;
	public void setViews_count(int views_count ) {
		this.views_count= views_count;
	}
	public int getViews_count() {
		return views_count;
	}
}