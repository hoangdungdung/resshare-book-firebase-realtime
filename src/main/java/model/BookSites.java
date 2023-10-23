package model;

import java.io.Serializable;

public class BookSites implements Serializable{

	public BookSites() {
		// TODO Auto-generated constructor stub
	}

 
	 Element ElementObject;
	 private String url;


	 // Getter Methods 

	 public Element getElement() {
	  return ElementObject;
	 }

	 public String getUrl() {
	  return url;
	 }

	 // Setter Methods 

	 public void setElement(Element elementObject) {
	  this.ElementObject = elementObject;
	 }

	 public void setUrl(String url) {
	  this.url = url;
	 }
	}
