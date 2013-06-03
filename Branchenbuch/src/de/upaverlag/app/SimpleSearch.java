package de.upaverlag.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.upaverlag.app.connection.JSONManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SimpleSearch extends ListFragment {

	String srhterm;
	int distance;

	ListView lv;

	// Progress Dialog
	private ProgressDialog fDialog;
	private OnItemClickListener getMoreDetails;

	// Creating JSON Parser object
	JSONManager jParser = new JSONManager();

	ArrayList<HashMap<String, String>> firmenList;

	// url to get all products list
	private static String UrlPlacesByCategory = "http://10.0.2.2:80/android/app/PlacesByCategory.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PLACES = "places";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";

	private static final String TAG_PLZ = "plz";

	private static final String TAG_CATEGORY = "category";
	private static final String TAG_CITY = "stadt";
	private static final String TAG_LONG = "long";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LOGO = "icon";

	JSONArray places = null;
	Activity myActivity;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.places_by_category, container, false);
		this.myActivity = getActivity();
		firmenList = new ArrayList<HashMap<String, String>>();
		lv = (ListView) v.findViewById(android.R.id.list);

		getValues();
		new LoadAllProducts().execute();

		return v;
	}

	public void getValues() {
		Bundle bnd = this.getArguments();
		this.srhterm = bnd.getString("searchterm");
		this.distance = bnd.getInt("distance");
		Log.e("Second Screen", srhterm);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		firmenList = new ArrayList<HashMap<String, String>>();
		addActionOnItemClick();

	}

	/*
	 * public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 * Bundle savedInstanceState) { View fragmentView =
	 * inflater.inflate(R.layout.places_by_category, container, false);
	 * this.myActivity = getActivity(); firmenList = new
	 * ArrayList<HashMap<String, String>>(); lv = (ListView)
	 * fragmentView.findViewById(android.R.id.list); //
	 * addActionOnItemClick(fragmentView); getCategory(); new
	 * LoadAllProducts().execute(); return fragmentView; }
	 */

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.places_by_category, menu); return true;
	 * }
	 */

	/*
	 * void getCategory() {
	 * 
	 * Intent i = getIntent(); // Receiving the Data this.ctg =
	 * i.getStringExtra("category");
	 * 
	 * 
	 * Bundle bnd = this.getArguments(); this.ctg = bnd.getString("buttonName");
	 * Log.e("Second Screen", ctg); }
	 */

	public void addActionOnItemClick() {

		getMoreDetails = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String firma = convertTxtViewToString(view, R.id.name);
				String anschrift = convertTxtViewToString(view, R.id.plz) + " "
						+ convertTxtViewToString(view, R.id.stadt);
				String lat = convertTxtViewToString(view, R.id.lat);
				String lg = convertTxtViewToString(view, R.id.lgt);

				// TODO add logo
				Intent singleItem = new Intent(myActivity,
						SingleListItemActivity.class);
				singleItem.putExtra("firmenname", firma);
				singleItem.putExtra("anschrift", anschrift);
				singleItem.putExtra("laenge", lg);
				singleItem.putExtra("breite", lat);

				startActivity(singleItem);

				Log.e("Single Item", firma + " in " + anschrift
						+ "/n hat standort " + "laenge: " + lg
						+ " und breite: " + lat);

			}

		};

		lv.setOnItemClickListener(getMoreDetails);

	}

	private String convertTxtViewToString(View v, int idNumber) {
		TextView tmp = (TextView) v.findViewById(idNumber);
		String txtToString = tmp.getText().toString();
		// falls NULL von db erhalten
		if (txtToString.equals("null")) {
			txtToString = " ";
		}

		return txtToString;
	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// fDialog = new ProgressDialog(PlacesByCategoryActivity.this);

			fDialog = new ProgressDialog(myActivity);
			fDialog.setMessage("Loading products. Please wait...");
			fDialog.setIndeterminate(false);
			fDialog.setCancelable(false);
			fDialog.show();
		}

		/**
		 * getting All places from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("category", srhterm));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(UrlPlacesByCategory,
					"GET", params);

			System.out.println(json);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					places = json.getJSONArray(TAG_PLACES);

					// looping through All Products
					for (int i = 0; i < places.length(); i++) {
						JSONObject c = places.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);

						String postcode = c.getString(TAG_PLZ);

						String cat = c.getString(TAG_CATEGORY);
						String city = c.getString(TAG_CITY);
						String laenge = c.getString(TAG_LONG);
						String breite = c.getString(TAG_LAT);

						String logo = c.getString(TAG_LOGO);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_PLZ, postcode);
						map.put(TAG_CATEGORY, cat);
						map.put(TAG_CITY, city);
						map.put(TAG_LOGO, logo);
						map.put(TAG_LONG, laenge);
						map.put(TAG_LAT, breite);

						// adding HashList to ArrayList
						firmenList.add(map);
					}
				}
				/*else {
					
				}*/
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			fDialog.dismiss();
			// updating UI from Background Thread
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(myActivity,
							firmenList, R.layout.list_item, new String[] {
									TAG_NAME, TAG_CATEGORY, TAG_PLZ, TAG_CITY,
									TAG_LONG, TAG_LAT }, new int[] { R.id.name,
									R.id.catg, R.id.plz, R.id.stadt, R.id.lgt,
									R.id.lat });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}
