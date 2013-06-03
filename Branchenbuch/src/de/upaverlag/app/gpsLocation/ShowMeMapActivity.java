package de.upaverlag.app.gpsLocation;

import de.upaverlag.app.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

public class ShowMeMapActivity extends Activity {

	GeoPoint geoPnt;
	/*
	private int mapTypeNormal = GoogleMap.MAP_TYPE_NORMAL;
	GoogleMap map;
	
	private GeoPoint geoPnt;
	LocationChecker gpsChecker = new LocationChecker(ShowMeMapActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		showMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	private void showMap(){
		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment smf
		= (SupportMapFragment)myFragmentManager.findFragmentById(R.id.map);
	GoogleMapOptions mapOptions = new GoogleMapOptions(); 
	mapOptions.mapType(GoogleMap.MAP_TYPE_SATELLITE)
    .compassEnabled(false)
    .rotateGesturesEnabled(false)
    .tiltGesturesEnabled(false);
	
//	apply options	
	smf.newInstance(mapOptions);
	
	map =  smf.getMap();
	
// map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	
	showMyLocation (map);
	double lat = 51.43f;
//	showTarget(51.43f, 71.15f, map);
	
	}
	
	private void showMyLocation (GoogleMap map ){
		double lat = gpsChecker.getLatitude();
		double lg =gpsChecker.getLongitude();
		LatLng myPos= new LatLng(lat, lg);
	
		map.addMarker(new MarkerOptions()
		.position(myPos)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_marker))
		);
		
		int coordinateLat = (int) (lat * 1E6);
		int coordinateLong = (int) (lg * 1E6);
		geoPnt = new GeoPoint(coordinateLat, coordinateLong);
		
		CameraPosition camPosBld = new CameraPosition.Builder()
		.target(myPos)
		.zoom(30)
		.build();
		
		map.animateCamera(CameraUpdateFactory.newCameraPosition(camPosBld));
		map.setMyLocationEnabled(true);
	}*/
	
	
	private void showTarget (double lat, double lg, GoogleMap m){
		
	LatLng position = new LatLng(lat, lg);
	m.addMarker(new MarkerOptions()
	.position(position)
	.icon(BitmapDescriptorFactory.defaultMarker()));
	
	int coordinateLat = (int) (lat * 1E6);
	int coordinateLong = (int) (lg * 1E6);
	geoPnt = new GeoPoint(coordinateLat, coordinateLong);
	
	CameraPosition camPosBld = new CameraPosition.Builder()
	.target(position)
	.zoom(17)
	.build();
	
	m.animateCamera(CameraUpdateFactory.newCameraPosition(camPosBld));
	}
	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;
	
	Location myLocation;
	TextView tvLocInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		
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
		
		myMap.setMyLocationEnabled(true);
		
		//myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	//	myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		LocationChecker gpsChecker = new LocationChecker(ShowMeMapActivity.this);
		
		double lat = gpsChecker.getLatitude();
		double lg =gpsChecker.getLongitude();
		LatLng myPos= new LatLng(lat, lg);
		
		int coordinateLat = (int) (lat * 1E6);
		int coordinateLong = (int) (lg * 1E6);
		geoPnt = new GeoPoint(coordinateLat, coordinateLong);
/*		
		CameraPosition newCamPos = new CameraPosition.Builder()
		.target(myPos)
		.zoom(30)
		.build();
		myMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos));

		myMap.setMyLocationEnabled(true);*/
		
		showTarget(51.43f, 71.15f, myMap);
//	myMap.animateCamera() ;	
//	myMap.setOnMapClickListener(this);
		

	}
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case R.id.menu_legalnotices:
	    	String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(
	    			getApplicationContext());
	    	AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
	    	LicenseDialog.setTitle("Legal Notices");
	    	LicenseDialog.setMessage(LicenseInfo);
	    	LicenseDialog.show();
	        return true;
	    }
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
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
