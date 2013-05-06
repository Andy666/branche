package de.upaverlag.app.branchensuche;

import java.util.ArrayList;

import de.upaverlag.app.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyBigListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<Parent> mParent;

	public MyBigListAdapter(Context cont, ArrayList<Parent> mParent) {

		this.inflater = LayoutInflater.from(cont);
		this.mParent = mParent;
	}

	@Override
	public Object getChild(int index1, int index2) {

		return mParent.get(index1).getArrayChildren().get(index2);
	}

	@Override
	public long getChildId(int i1, int i2) {

		return i2;
	}

	@Override
	public View getChildView(int i1, int i2, boolean b, View view,
			ViewGroup viewGroup) {
		if (view == null) {
			view = inflater.inflate(R.layout.single_category_items, viewGroup,
					false);
		}

		TextView textView = (TextView) view.findViewById(R.id.childItem);
		// "i1" is the position of the parent/group in the list and
		// "i2" is the position of the child
		textView.setText(mParent.get(i1).getArrayChildren().get(i2));
		final String txtVal = textView.getText().toString();
		
		view.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View v) {
			 /*   Toast.makeText(getactivity();, tempChild.get(childPosition),
			      Toast.LENGTH_SHORT).show();*/
				   
				   Log.e("OnChildClickListener", txtVal);
			   }
			  });
		// return the entire view
		return view;
	}

	@Override
	public int getChildrenCount(int i) {

		return mParent.get(i).getArrayChildren().size();
	}

	@Override
	public Object getGroup(int i) {

		return mParent.get(i).getTitle();
	}

	@Override
	public int getGroupCount() {

		return mParent.size();
	}

	@Override
	public long getGroupId(int i) {

		return i;
	}

	@Override
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = inflater.inflate(R.layout.group_of_items, viewGroup, false);
		}

		TextView textView = (TextView) view.findViewById(R.id.header);
		// "i" is the position of the parent/group in the list
		textView.setText(getGroup(i).toString());

		// return the entire view
		return view;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}
}
