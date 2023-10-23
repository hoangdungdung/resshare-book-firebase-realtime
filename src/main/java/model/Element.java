package model;

import java.io.Serializable;

public class Element implements Serializable {

	public Element() {
		// TODO Auto-generated constructor stub
	}
 
		 private String author_name;
		 private String category_name;
		 private float cover_price;
		 private String image;
		 private String isbn;
		 private String name;
		 private String page_number;
		 private String plot_overview;
		 private String publisher;
		 private String publishing_year;
		 private String size;
		 private String weight;


		 // Getter Methods 

		 public String getAuthor_name() {
		  return author_name;
		 }

		 public String getCategory_name() {
		  return category_name;
		 }

		 public float getCover_price() {
		  return cover_price;
		 }

		 public String getImage() {
		  return image;
		 }

		 public String getIsbn() {
		  return isbn;
		 }

		 public String getName() {
		  return name;
		 }

		 public String getPage_number() {
		  return page_number;
		 }

		 public String getPlot_overview() {
		  return plot_overview;
		 }

		 public String getPublisher() {
		  return publisher;
		 }

		 public String getPublishing_year() {
		  return publishing_year;
		 }

		 public String getSize() {
		  return size;
		 }

		 public String getWeight() {
		  return weight;
		 }

		 // Setter Methods 

		 public void setAuthor_name(String author_name) {
		  this.author_name = author_name;
		 }

		 public void setCategory_name(String category_name) {
		  this.category_name = category_name;
		 }

		 public void setCover_price(float cover_price) {
		  this.cover_price = cover_price;
		 }

		 public void setImage(String image) {
		  this.image = image;
		 }

		 public void setIsbn(String isbn) {
		  this.isbn = isbn;
		 }

		 public void setName(String name) {
		  this.name = name;
		 }

		 public void setPage_number(String page_number) {
		  this.page_number = page_number;
		 }

		 public void setPlot_overview(String plot_overview) {
		  this.plot_overview = plot_overview;
		 }

		 public void setPublisher(String publisher) {
		  this.publisher = publisher;
		 }

		 public void setPublishing_year(String publishing_year) {
		  this.publishing_year = publishing_year;
		 }

		 public void setSize(String size) {
		  this.size = size;
		 }

		 public void setWeight(String weight) {
		  this.weight = weight;
		 }
		}