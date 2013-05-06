package de.upaverlag.app.branchensuche;

import de.upaverlag.app.R;
import de.upaverlag.app.connection.PlacesByCategoryActivity;
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
import android.widget.ImageButton;

public class BranchenSuche extends Fragment {

	private OnClickListener btnClick;

	ImageButton autoBtn;
	ImageButton foodBtn;
	ImageButton moneyBtn;
	ImageButton allCatBtn ;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.branchensuche, container,
				false);
		addListenerOnButton(fragmentView);
		return fragmentView;
	}

	
/*	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}*/

	
	public void addListenerOnButton(View v) {

		autoBtn = (ImageButton) v.findViewById(R.id.button1);
		foodBtn = (ImageButton) v.findViewById(R.id.button3);
		moneyBtn = (ImageButton) v.findViewById(R.id.bank);
		allCatBtn = (ImageButton) v.findViewById(R.id.categoryList);
		
		btnClick = new OnClickListener() {
			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.button3:
					catSearching(foodBtn);
					break;

				case R.id.button1:
					catSearching(autoBtn);
					break;
				case R.id.bank:
					catSearching(moneyBtn);
					break;
					
				case R.id.categoryList:	
					showMeAllCategories();
					break;
				}
			}

		};

		autoBtn.setOnClickListener(btnClick);
		foodBtn.setOnClickListener(btnClick);
		moneyBtn.setOnClickListener(btnClick);
		allCatBtn.setOnClickListener(btnClick);
	}

	private void catSearching(ImageButton imgBtn) {
		// b = (Button)v;
		String buttonTxt = imgBtn.getTag().toString();
		Intent branchensuche = new Intent(getActivity(),
				PlacesByCategoryActivity.class);
		branchensuche.putExtra("category", buttonTxt);
		Log.e("n", buttonTxt);
		startActivity(branchensuche);
	}
	
	private void showMeAllCategories () {
		
		Intent i = new Intent(getActivity(),AllCategories.class);
		startActivity(i);
	}
}
