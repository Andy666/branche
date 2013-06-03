package de.upaverlag.app;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

import de.upaverlag.app.gpsLocation.LocationChecker;
import de.upaverlag.app.gpsLocation.ShowMeMapActivity;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class SingleListItemActivity extends Activity {

	private GoogleMap myMap;
	GeoPoint geoPnt;
	boolean isLocationSrchOn;
	
	double log;
	double lat;
	
	final int RQS_GooglePlayServices = 1;
	
	Location myLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item_activity);
		getValuesFromAnotherActivity();
		createMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_list_item, menu);
		return true;
	}
	
	void getValuesFromAnotherActivity() {

		Intent i = getIntent();

		String fName = i.getStringExtra("firmenname");
		String fAdresse = i.getStringExtra("anschrift");
		
		log = Double.parseDouble(i.getStringExtra("laenge"));
		lat = Double.parseDouble(i.getStringExtra("breite"));
		isLocationSrchOn = i.getBooleanExtra("isEnable", false);

		TextView txtViewFirma = (TextView) findViewById(R.id.company);
		TextView txtViewAdresse = (TextView) findViewById(R.id.adresseCompany);

		txtViewFirma.setText(fName);
		txtViewAdresse.setText(fAdresse);

		// showMeCenter(lat, log);

	}
	

	private void createMap() {
		
		MapFragment myMapFragment 
			= (MapFragment)getFragmentManager().findFragmentById(R.id.map);
		
		GoogleMapOptions mapOptions = new GoogleMapOptions(); 
		mapOptions.mapType(GoogleMap.MAP_TYPE_SATELLITE)
	    .compassEnabled(false)
	    .rotateGesturesEnabled(false)
	    .tiltGesturesEnabled(false);
		
//		apply options	
		myMapFragment.newInstance(mapOptions);
		myMap = myMapFragment.getMap();
		
//		myMap.setMyLocationEnabled(true);
		if(isLocationSrchOn){
			showMyLocation(myMap);
		}
		showTarget(lat, log, myMap);
	}
	
	private void showTarget(double lat, double lg, GoogleMap m) {
		LatLng position = new LatLng(lat, lg);

		m.addMarker(new MarkerOptions().position(position).icon(
				BitmapDescriptorFactory.defaultMarker()));

		int coordinateLat = (int) (lat * 1E6);
		int coordinateLong = (int) (lg * 1E6);
		geoPnt = new GeoPoint(coordinateLat, coordinateLong);

		CameraPosition camPosBld = new CameraPosition.Builder()
				.target(position).zoom(17).build();

		m.animateCamera(CameraUpdateFactory.newCameraPosition(camPosBld));
	}
		
	private void showMyLocation (GoogleMap m){
	LocationChecker gpsChecker = new LocationChecker(SingleListItemActivity.this);
		
		double lat = gpsChecker.getLatitude();
		double lg =gpsChecker.getLongitude();
		LatLng myPos= new LatLng(lat, lg);
		
		int coordinateLat = (int) (lat * 1E6);
		int coordinateLong = (int) (lg * 1E6);
		
		myMap.addMarker(new MarkerOptions()
		.position(myPos)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_marker))
		);
		
		
		geoPnt = new GeoPoint(coordinateLat, coordinateLong);
		
		CameraPosition camPosBld = new CameraPosition.Builder()
		.target(myPos)
		.zoom(10)
		.build();
		
		myMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPosBld));
		myMap.setMyLocationEnabled(true);
	}
	
	@Override
	protected void onResume() {

		super.onResume();

		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		
		if (resultCode == ConnectionResult.SUCCESS){
			Toast.makeText(getApplicationContext(), 
					"isGooglePlayServicesAvailable SUCCESS", 
					Toast.LENGTH_LONG).show();
		}else{
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
		}
		
	}
}
