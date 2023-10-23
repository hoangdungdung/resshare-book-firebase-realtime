package model;

import java.io.Serializable;
import java.util.HashMap;

import com.google.firebase.database.Exclude;

/**
 * Created by Nha Nha on 8/1/2017.
 */

public class Category implements Serializable {
    @Exclude
    private String category_id;
    private String name;
    private String image;

    @Exclude
    public String getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    @Exclude
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Category() {
    }

    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

	    @Exclude
		public static HashMap<String, Object> getMapsAuthor(Category  category) {
			HashMap<String, Object> maps = new HashMap<>();
			maps.put("name", category.getName());
			maps.put("category_id",category.getCategory_id());
			maps.put("image", category.getImage()); 
		  
		 
			return maps;

		}

		 
}
