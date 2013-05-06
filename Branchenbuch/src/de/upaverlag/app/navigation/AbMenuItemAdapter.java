package de.upaverlag.app.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import de.upaverlag.app.R;

public class AbMenuItemAdapter extends ArrayAdapter<String> implements SpinnerAdapter {

		public Context contxt;
		public String[] titel;
		int[] arrImages = { R.drawable.home, R.drawable.search, R.drawable.favorite };

		public AbMenuItemAdapter(Context context, int textViewResourceId, String[] actionBarItems) {
			super(context, textViewResourceId, actionBarItems);

			this.contxt = context;
			this.titel = actionBarItems;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			LayoutInflater mInflater = LayoutInflater.from(contxt);
			View row = mInflater
					.inflate(R.layout.actionbar_item_row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.titelName);
			label.setText(titel[position]);

			ImageView icon = (ImageView) row.findViewById(R.id.icon);
			icon.setImageResource(arrImages[position]);
			return row;
		}

}
