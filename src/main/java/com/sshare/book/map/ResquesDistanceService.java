/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sshare.book.map;

import java.text.DecimalFormat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.resshare.map.DistanceCalculator;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;

import sample.city.CurrentLocation;
import sample.city.RequestUserId;
import sample.city.RequestUsers;
import sample.city.UserDistance;
import service.ServiceBase;

public class ResquesDistanceService extends ServiceBase {

	private static final String TABLE_REQUEST_DISTANCE = "request_user_distance";

	private static final String TABLE_DISTANCE = "distances";

	public static final String REST_SERVICE_URI = "http://localhost:8088/";
	private static final String TABLE_CURRENT_LOCATION = "map/current_location";
 
 
 

	private static final String CHILD_REQUEST_USER_ID = "request_userId";
	private static final String CHILD_LIST_USER = "Z-listUser";
	private static final String CHILD_LIST_LOCATION = "z-ListLocation";
	private static final String CHILD_LAST_LOCATION = "Last_Location";
	public final static String UNIT_KILOMETERS = "K";

	FirebaseDatabase database = FirebaseDatabase.getInstance();

	//DatabaseReference firebaseRequestUserDistance = database.getReference(TABLE_REQUEST_USER_DISTANCE);
	DatabaseReference firebaseCurrentLocation = database.getReference(TABLE_CURRENT_LOCATION);
	DatabaseReference firebaseDistance = database.getReference(TABLE_DISTANCE);
	//DatabaseReference firebaseMapUser = database.getReference(TABLE_MAP_USER);
//	DatabaseReference firebaseReponseUserDistance = database.getReference(TABLE_REPONSE_USER_DISTANCE);
	public ResquesDistanceService() {
		super();
		FirebaseDatabase.getInstance().getReference(getReferenceName()).setValue(null);

	}

	/**
	 * @author hung
	 *
	 */

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference firebaseDistance = database.getReference(TABLE_DISTANCE);

		if (snapshot.exists()) {
			RequestUsers requestUser = new RequestUsers();
			for (DataSnapshot user : snapshot.getChildren()) {
				if (user.getKey().equals("request_userId")) {
					requestUser.request_userId = String.valueOf(user.getValue());
				}

				if (user.getKey().equals("Z-listUser")) {
					for (DataSnapshot userChild : user.getChildren()) {
						UserDistance userchildDistance = new UserDistance();
						userchildDistance.userId = userChild.getKey();

						Double distance = userChild.getValue(Double.class);
						userchildDistance.distance = distance;

						requestUser.listUserDistance.add(userchildDistance);
					}
				}
			}

			if (requestUser != null) {
				System.out.println("getLocationOfUserRequirement:" + requestUser.request_userId + " key_request_user:");
				getLocationOfUserRequirement(requestUser);
			}



		}

	}


	private void getLocationOfUserRequirement( RequestUsers itemUser) {
		firebaseCurrentLocation.child(itemUser.request_userId).child(CHILD_LAST_LOCATION)
				.addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {
						if (snapshot.exists()) {
							CurrentLocation currentLocation = snapshot.getValue(CurrentLocation.class);

							if (currentLocation != null) {
								calculateDistance( itemUser, currentLocation);
							}
						}
					}

					@Override
					public void onCancelled(DatabaseError error) {
					}
				});
	}
	private void calculateDistance( RequestUsers itemUser, CurrentLocation currentLocation) {

		for (UserDistance user : itemUser.listUserDistance) {

			firebaseCurrentLocation.child(user.userId).child(CHILD_LAST_LOCATION)
					.addListenerForSingleValueEvent(new ValueEventListener() {

						@Override
						public void onDataChange(DataSnapshot snapshot) {
							if (snapshot.exists()) {
								CurrentLocation currentLocation2 = snapshot.getValue(CurrentLocation.class);
								if (currentLocation2 != null) {

									System.out.println("calculateDistance:" + itemUser.request_userId + " user.userId:"
											+ user.userId);

									double distance = DistanceCalculator.distance(currentLocation.latitude,
											currentLocation.longitude, Double.valueOf(currentLocation2.latitude),
											Double.valueOf(currentLocation2.longitude), UNIT_KILOMETERS);
									
								String dis =	getDistance(String.valueOf(distance));
									
									
									firebaseDistance.child( itemUser.request_userId).child(user.userId).child("Distance")
									.setValue(dis);

				

									 

						

								}
							}
						}

						@Override
						public void onCancelled(DatabaseError error) {
						}
					});

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
		return TABLE_REQUEST_DISTANCE;
	}
	 public static String getDistance(String distace) {

	        DecimalFormat df2 = new DecimalFormat( "###,###" );
	        double dd=0;
	        if( (distace !=null)& !"".equals(distace)) {
	            String distace1 = distace.replaceAll(",","");
	            dd = Double.valueOf(distace1);

	            if(dd<1 ) {
	                Double    dd1=dd*1000;
	                String dd2dec = df2.format(dd1);
	                return    dd2dec + " m";
	            }else
	            {

	                String dd2dec = df2.format(dd);
	                return dd2dec + " KM";
	            }
	        }
	        return "";
	    }
}