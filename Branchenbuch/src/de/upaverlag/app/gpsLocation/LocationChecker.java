package de.upaverlag.app.gpsLocation;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class LocationChecker extends Service implements LocationListener {
	
	private Context mContext;

	// GPS off default
	boolean isGPSEnabled = false;

	//
	boolean isNetworkEnabled = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	boolean canGetLocation = false;
	
	public LocationChecker (Context cont){
		this.mContext = cont;
		getLocation();
	}

	private Location getLocation() {
		try {
			getLocationManager();

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				// TODO add allertDialog
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	LocationManager getLocationManager() {

		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);

		return locationManager;

	}
	
	  public double getLatitude(){
	        if(location != null){
	            latitude = location.getLatitude();
	        }
	 
	        // return latitude
	        return latitude;
	    }
	 
	    /**
	     * Function to get longitude
	     * */
	    public double getLongitude(){
	        if(location != null){
	            longitude = location.getLongitude();
	        }
	 
	        // return longitude
	        return longitude;
	    }

	@Override
	public void onLocationChanged(Location location) {
		getLatitude();
		getLongitude();

	}

	@Override
	public void onProviderDisabled(String provider) {
		AlertDialog.Builder msgBuilder = new AlertDialog.Builder(mContext);
		msgBuilder.setTitle("Ortungsdienst");
		msgBuilder.setMessage("Ortungsdienste sind nicht freigeschaltet");
		
		 // On pressing Settings button
        msgBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
  
        // on pressing cancel button
       msgBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        msgBuilder.show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
