package de.upaverlag.app.branchensuche;

import de.upaverlag.app.R;
import de.upaverlag.app.SimpleSearch;
import de.upaverlag.app.SingleListItemActivity;
import de.upaverlag.app.connection.PlacesByCategoryActivity;
import de.upaverlag.app.gpsLocation.ShowMeMapActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BranchenSuche extends Fragment implements OnSeekBarChangeListener {

	private OnClickListener btnClick;

	ImageButton autoBtn;
	ImageButton foodBtn;
	ImageButton moneyBtn;
	ImageButton allCatBtn;
	ImageButton map;
	
	Button seaker; 
	TextView radiusValue;
	EditText searchTxt;
	SeekBar radius;
	int abstand;
	AlertDialog warningMsg;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.branchensuche, container,
				false);
		searchTxt = (EditText) fragmentView.findViewById(R.id.searchTextField);
		createSeekbar(fragmentView);
		hideText(fragmentView);
		addListenerOnButton(fragmentView);
		return fragmentView;
	}

	private void createSeekbar(View v) {
		radius = (SeekBar) v.findViewById(R.id.seekBar1);
		radiusValue = (TextView) v.findViewById(R.id.umkreis);
	}	
		
	private void usingSeekbar() {	
//		set max value
		radius.setMax(100);
		radius.setOnSeekBarChangeListener(this);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usingSeekbar();
	}
	
	public void addListenerOnButton(View v) {

		autoBtn = (ImageButton) v.findViewById(R.id.button1);
		foodBtn = (ImageButton) v.findViewById(R.id.button3);
		moneyBtn = (ImageButton) v.findViewById(R.id.bank);
		allCatBtn = (ImageButton) v.findViewById(R.id.categoryList);
		map = (ImageButton) v.findViewById(R.id.imgMap);
		seaker = (Button) v.findViewById(R.id.searchButton);
		
		 onCatSearchBtn (autoBtn);
		 onCatSearchBtn (moneyBtn);
		 onCatSearchBtn(foodBtn);
		 actionForAllCatBtn(allCatBtn);
		 smpSearchBtn(seaker);
		 onMapBtnClk(map);
	}

	private void onCatSearchBtn (final ImageButton img){
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				catSearching(img);
			}
		});
	}
	
	private void actionForAllCatBtn (final ImageButton img){
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMeAllCategories();
			}
		});
	}
	
	private void smpSearchBtn (final Button btn){
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				simpleSearch();
			}
		});
	}
	
	private void onMapBtnClk(ImageButton img ) {
		
	img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMap();
			}
		});
	}	

	
	private void catSearching(ImageButton imgBtn) {
	
		String buttonTxt = imgBtn.getTag().toString();

		FragmentManager fManager = getFragmentManager();
		Fragment fgt = new PlacesByCategoryActivity();
		FragmentTransaction fTrans = fManager.beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putString("buttonName", buttonTxt);
		fgt.setArguments(bundle);
		fTrans.replace(R.id.placeholder, fgt);
		fTrans.addToBackStack(null);
		fTrans.commit();
	}
	
	private void showMeAllCategories () {
		
		Intent i = new Intent(getActivity(),AllCategories.class);
		startActivity(i);
	}
	
	private void showMap() {
		Intent i = new Intent(getActivity(),ShowMeMapActivity.class);
		startActivity(i);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int value, boolean arg2) {
		this.abstand = value;
		radiusValue.setText(Integer.toString(value)+ "km");
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void simpleSearch (){

		String suchbegriff = searchTxt.getText().toString();
		
		if (!suchbegriff.equals("")){
			FragmentManager fManager = getFragmentManager();
			Fragment fgt = new SimpleSearch();
			FragmentTransaction fTrans = fManager.beginTransaction();
			Bundle bundle = new Bundle();
			bundle.putString("searchterm", suchbegriff);
			bundle.putInt("distance", abstand);
			fgt.setArguments(bundle);
			fTrans.replace(R.id.placeholder, fgt);
			fTrans.addToBackStack(null);
			fTrans.commit();
		}
		else {
			warningMsg = new AlertDialog.Builder(getActivity()).setTitle("Achtung!").setMessage("Suchbegriff wurde nicht eingegeben!").setNeutralButton("Close", null).show();  
		}
	}
	
	private void hideText (View v ){
		ToggleButton tgButton = (ToggleButton) v.findViewById(R.id.toggleButton1);
		final TextView tv = (TextView)v.findViewById(R.id.textView3);
		createSeekbar(v);
	
		tgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	tv.setVisibility(View.VISIBLE);
		        	radius.setVisibility(View.VISIBLE);
		        	radiusValue.setVisibility(View.VISIBLE);
		        	isLocalSearchEnable(isChecked);
		        } 
		       else {
		        	tv.setVisibility(View.GONE);
		        	radius.setVisibility(View.GONE);
		        	radiusValue.setVisibility(View.GONE);
		        }
		    }
		});
	}
	
	private void isLocalSearchEnable (boolean checkIt){
		 Intent singleItem = new Intent (getActivity(), SingleListItemActivity.class);
		  singleItem.putExtra("isEnable", checkIt);
		  
		  startActivity(singleItem);
		  
		  Log.e("Lokale Suche", "Lokale Suche is " + String.valueOf(checkIt));
       
	}
}
