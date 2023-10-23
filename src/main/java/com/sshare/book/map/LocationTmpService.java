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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sample.city.CurrentLocation;
import sample.city.Locations;
import service.ServiceBase;

public class LocationTmpService extends ServiceBase {

	private static final String TABLE_LOCATION_TMP = "map/location_tmp";
	private static final String Current_location = "map/current_location";

 

	/**
	 * @author hung
	 *
	 */

	private static void createCurrentLocation(Locations location, String key) {
		System.out.println("createCurrentLocation create Location API----------");
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference firebaseCurrentLocation = database.getReference(Current_location);
		try {
			CurrentLocation crLocation = new CurrentLocation(false, location.latitude_degree, location.longitude_degree,
					Double.valueOf(location.latitude), Double.valueOf(location.longitude));
			if (location.user_id == null)
				return;
		 
			if (location.user_id != null) {
				firebaseCurrentLocation.child(location.user_id).child("Last_Location").setValue(crLocation);
			}
			// DatabaseReference firebaseLocationTemp =
			// database.getReference(TABLE_LOCATION_TMP);

			 FirebaseDatabase.getInstance().getReference(TABLE_LOCATION_TMP). child(key).removeValue();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Location : "+uri.toASCIIString());
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		try {
		Locations location = snapshot.getValue(Locations.class);
		createCurrentLocation(location, snapshot.getKey());
		} catch (Exception e) {
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
		return TABLE_LOCATION_TMP;
	}
}