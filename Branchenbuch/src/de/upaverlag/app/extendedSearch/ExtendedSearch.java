package de.upaverlag.app.extendedSearch;

import de.upaverlag.app.R;
import de.upaverlag.app.R.layout;
import de.upaverlag.app.connection.PlacesBySearchTermActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ExtendedSearch extends Fragment{
	
	private Spinner selectCountry;
	private Button sbmButton;
	String plz;
	String city;
	String suchbegriff;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.extended_search, container, false);
    
    useAutoComplete(v);
    addListenerOnButton(v);
    
    getValueFromTextField(v);
    return v;
    }   
	
	public void addListenerOnButton(View v) {

		// selectCountry = (Spinner) findViewById(R.id.spinner1);

		sbmButton = (Button) v.findViewById(R.id.subbmitBtn);
		sbmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startNextActivity();

			}
		});
	}

	void useAutoComplete(View v) {

		String[] adresse = getResources().getStringArray(
				R.array.plzUndOrt_array);
		

		// Adapter + starting AutoComplet
		ArrayAdapter<String> autoCmpltAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, adresse);
		AutoCompleteTextView autoCmpltTextView = (AutoCompleteTextView) v.findViewById(R.id.ortTextField);
		autoCmpltTextView.setAdapter(autoCmpltAdapter);
		autoCmpltTextView.setThreshold(2);

		// get selected Item
		autoCmpltTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String values = ((TextView) arg1).getText().toString();
				String [] toSplit = values.split(" ");
				plz =  toSplit [0];
			}
		});
		

		// plz = autoCmpltTextView.getText().toString();

		// Landes String zerlegen in Array von String
		/*
		 * String[] toSplit = values.split("\\s"); for( int i = 0
		 * ;i<toSplit.length; i++){ System.out.println(toSplit[i]); } plz =
		 * toSplit[0];
		 */

		// TODO bei Bedarf Stadt in die Datenbank Abfragen einbeziehen
		/*
		 * String city = toSplit [1]; int wortZaehler = 2; if (toSplit.length >
		 * wortZaehler ) { for (int i=1; i<toSplit.length; i++ ){ String tmp =
		 * toSplit [i]; if (i>=wortZaehler) { city+=tmp; } } }
		 */

	}

	void getValueFromTextField(View v) {

		TextView searchTxt = (TextView) v.findViewById(R.id.suchbegriff);
		suchbegriff = searchTxt.getText().toString();

	}

	void startNextActivity() {

//		getValueFromTextField();
		Intent myIntent = new Intent(getActivity(),
				PlacesBySearchTermActivity.class);

		myIntent.putExtra("searchTerm", suchbegriff);
		myIntent.putExtra("zip", plz);
		Log.e("adresse", "ich suche: " + suchbegriff + " in " + plz);
		startActivity(myIntent);
	}
}
