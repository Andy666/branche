package de.upaverlag.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class SingleListItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item_activity);
		getValuesFromAnotherActivity();
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
		double log = Double.parseDouble(i.getStringExtra("laenge"));
		double lat = Double.parseDouble(i.getStringExtra("breite"));

		TextView txtViewFirma = (TextView) findViewById(R.id.company);
		TextView txtViewAdresse = (TextView) findViewById(R.id.adresseCompany);

		txtViewFirma.setText(fName);
		txtViewAdresse.setText(fAdresse);

		// showMeCenter(lat, log);

	}

}
