package de.upaverlag.app;


import de.upaverlag.app.navigation.AbMenuItemAdapter;
import de.upaverlag.app.navigation.AbNavigationsListener;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Bundle;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SeachAppMainActivity extends FragmentActivity {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	ActionBar actionBar;
	String[] actionBarItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seach_app_main);

		// Set up the action bar to show a dropdown list.
		startActionBar();

	}

	void getActionBarItems() {
		String[] arrString = { getString(R.string.Branche),
				getString(R.string.directSearche), getString(R.string.favListe) };
		actionBarItems = arrString;
	}

	public void startActionBar() {
		this.actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		getActionBarItems();
		// Specify a SpinnerAdapter to populate the dropdown list.
		AbMenuItemAdapter abAdapter = new AbMenuItemAdapter(
				getActionBarThemedContextCompat(),
				android.R.layout.simple_list_item_1, actionBarItems);

		AbNavigationsListener abNaviListener = new AbNavigationsListener(actionBarItems, getSupportFragmentManager());
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(abAdapter,abNaviListener);
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seach_app_main, menu);
		return true;
	}



}
