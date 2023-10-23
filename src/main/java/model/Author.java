package model;

import java.io.Serializable;
import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.SerializedName;

import listener.UserInfoListener;

public class Author  implements Serializable {
	 
	    @Exclude
	    private String author_id;
	    private String name;
	    private String avatar;
	    private String nick_name;
	    private String country;

	    @Exclude
	    public void setAuthor_id(String author_id) {
	        this.author_id = author_id;
	    }

	    @Exclude
	    public String getAuthor_id() {
	        return author_id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getAvatar() {
	        return avatar;
	    }

	    public String getCountry() {
	        return country;
	    }

	    public String getNick_name() {
	        return nick_name;
	    }

	    public void setAvatar(String avatar) {
	        this.avatar = avatar;
	    }

	    public Author() {
	    }

	    public Author(String name, String avatar, String nick_name, String country) {
	        this.name = name;
	        this.avatar = avatar;
	        this.nick_name = nick_name;
	        this.country = country;
	    }
	 
	    @Exclude
		public static HashMap<String, Object> getMapsAuthor(Author  user) {
			HashMap<String, Object> maps = new HashMap<>();
			maps.put("name", user.getName());
			maps.put("author_id",user.getAuthor_id());
			maps.put("avatar", user.getAvatar()); 
		 	maps.put("nick_name", user.getNick_name());
		 	maps.put("country", user.getCountry());
		 
			return maps;

		}
}
