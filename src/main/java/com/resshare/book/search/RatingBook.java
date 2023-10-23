package com.resshare.book.search;

import java.util.HashMap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resshare.book.RefFireBaseBook;
import com.resshare.framework.core.service.ResFirebaseReference;

import service.ServiceBase;

public class RatingBook extends ServiceBase {

	@Override
	public void onChildAdded(DataSnapshot snapshot1, String previousChildName) {
		// TODO Auto-generated method stub
		try {
			if (snapshot1.child("processing").getValue() == null) {

				String book_id = snapshot1.child("data/book_id").getValue(String.class);
				String user_id = snapshot1.child("user_id").getValue(String.class);
				int rating_new = snapshot1.child("data/rating").getValue(Integer.class);
				HashMap<String, Object> mapVar = new HashMap<>();
				Integer totalCount = 0;
				Integer totalRating = 0;
				mapVar.put("total_count", 0);
				mapVar.put("total_rating", 0);

				FirebaseDatabase.getInstance().getReference()
						.child(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.data_rating_book)).child(book_id)
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onDataChange(DataSnapshot snapshot2) {

								if (snapshot2.exists()) {
									int totalCount = snapshot2.child("total_count").getValue(Integer.class);
									int totalRating = snapshot2.child("total_rating").getValue(Integer.class);
									mapVar.put("total_count", totalCount);
									mapVar.put("total_rating", totalRating);
								}

								FirebaseDatabase.getInstance().getReference()
										.child(ResFirebaseReference.getAbsolutePath(RefFireBaseBook.data_rating_book_user))
										.child(book_id).child(user_id)
										.addListenerForSingleValueEvent(new ValueEventListener() {

											@Override
											public void onDataChange(DataSnapshot snapshot3) {
												try {
													
												
												int totalRatingNew =   (Integer) mapVar.get("total_rating");
												int totalCountNew =(Integer) mapVar.get("total_count");
												int totalRatingOdl = (Integer) mapVar.get("total_rating");
												if (snapshot3.exists()) {

													int rating_old = snapshot3.getValue(Integer.class);

													totalRatingNew = totalRatingOdl + rating_new - rating_old;
												} else {
													int totalCountOdl = (Integer) mapVar.get("total_count");
													totalRatingNew = totalRatingOdl + rating_new;
													totalCountNew = totalCountOdl + 1;
												}
												mapVar.put("total_count", totalCountNew);
												mapVar.put("total_rating", totalRatingNew);

												FirebaseDatabase.getInstance().getReference()
														.child(ResFirebaseReference
																.getAbsolutePath(RefFireBaseBook.data_rating_book))
														.child(book_id).setValue(mapVar);
												FirebaseDatabase.getInstance().getReference()
														.child(ResFirebaseReference
																.getAbsolutePath(RefFireBaseBook.data_rating_book_user))
														.child(book_id).child(user_id).setValue(rating_new);
										float		rating = totalRatingNew / totalCountNew;
										FirebaseDatabase.getInstance().getReference()
										.child(ResFirebaseReference
												.getAbsolutePath(RefFireBaseBook.BOOK_DATA_BOOKS))
										.child(book_id).child("rating").setValue(rating);
										
												} catch (Exception e) {
													e.printStackTrace();
												}

											}

											@Override
											public void onCancelled(DatabaseError error) {
												// TODO Auto-generated method stub

											}
										});

							}

							@Override
							public void onCancelled(DatabaseError error) {
								// TODO Auto-generated method stub

							}
						});

			}

			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("done");

		} catch (Exception e) {
			FirebaseDatabase.getInstance().getReference(getReferenceName()).child(snapshot1.getKey())
					.child("processing").setValue("error");
			e.printStackTrace();
		}
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReferenceName() {
		// TODO Auto-generated method stub
		return ResFirebaseReference.getInputPathReference(RefFireBaseBook.rating_book);
	}

}
