package service;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.sshare.core.StringUtil;

import model.Books;
import model.Rating;
import model.RatingTottal;

public class RatingService extends ServiceBase {
	public static final String keySplit = "#@#";
	private final static int TIME_CHECK = 5000;
	// private Map<String, Books> mBooks = Cache.mBooks;

	public RatingService() {
		super();

	}

	@Override
	public void onStart() {
 
		super.onStart();

	}

	public static String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	public static String getKeyByName(Books book) {
		String keyEncode = book.getName();
		keyEncode = keyEncode.trim();
		String keyEncode7 = keyEncode.replaceAll("\\.", "");

		String keyEncode1 = StringUtil.removeAccent(keyEncode7);

		return keyEncode1;
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
 
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		 

	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
 
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {

			// TODO Auto-generated method stub
			Rating rating = snapshot.getValue(Rating.class);
			

			FirebaseDatabase.getInstance().getReference(RefFireBaseBook.rating_request).child(snapshot.getKey()).setValue(null);
			

			FirebaseDatabase.getInstance().getReference(rating.getObject_database()).child(rating.getKey()).child("rating")
					.addListenerForSingleValueEvent(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshotBook) {
							if (snapshotBook.exists()) {
								RatingTottal book = snapshotBook.getValue(RatingTottal.class);
								if ((book.getRating() != null) && book.getRating_number() != null && book.getRatingTottal() != null) {
									
									
									FirebaseDatabase.getInstance().getReference(rating.getRating_database()).child(rating.getUser_id()).
									child(rating.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
										
										@Override
										public void onDataChange(DataSnapshot snapshotRating) {
											if(snapshotRating.exists())
											{
												Double db = snapshotRating.getValue(Double.class);
												book.setRatingTottal( book.getRatingTottal()+rating.getRating()-db);
												double val = (book.getRatingTottal()/ book.getRating_number());
												 
												val = val*10;
												val = Math.round(val);
												val = val /10;
												book.setRating(val);
												
											}
											else
											{
												book.setRating_number(book.getRating_number()+1);
												book.setRatingTottal( book.getRatingTottal()+rating.getRating());
												double val = (book.getRatingTottal()/ book.getRating_number());
												 
												val = val*10;
												val = Math.round(val);
												val = val /10;
												book.setRating(val);
												
											}
											FirebaseDatabase.getInstance().getReference(rating.getRating_database()).child(rating.getUser_id()).
											child(rating.getKey()).setValue(rating.getRating());
											FirebaseDatabase.getInstance().getReference(rating.getObject_database()).child(rating.getKey()).child("rating").setValue(book);
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onCancelled(DatabaseError error) {
											// TODO Auto-generated method stub
											
										}
									});

									

									

								}
								
								
								
								
							}
							else
							{
								RatingTottal book =new RatingTottal();
								book.setRating_number(1L);
								book.setRatingTottal( rating.getRating());
								book.setRating(book.getRatingTottal()/ book.getRating_number());
								FirebaseDatabase.getInstance().getReference(rating .getRating_database()).child(rating.getUser_id()).
								child(rating.getKey()).setValue(rating.getRating());
								FirebaseDatabase.getInstance().getReference(rating.getObject_database()).child(rating.getKey()).child("rating").setValue(book);
							}

						}

						@Override
						public void onCancelled(DatabaseError error) {
							// TODO Auto-generated method stub

						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return RefFireBaseBook.rating_request;
	}

}
