package de.upaverlag.app.branchensuche;

import java.util.ArrayList;



import de.upaverlag.app.R;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class AllCategories extends ExpandableListActivity{

	private ExpandableListView myBigList;
	private ArrayList<String> arrList;
	private ArrayList<Object> listChild;
	
	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.big_list_layout);
		
		setGroupData();
		setChildGroupData();

		myBigList = getExpandableListView();
		MyBigListAdapter mBigAdapter = new MyBigListAdapter(groupItem, childItem);
		mBigAdapter
				.setInflater(
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						this);
		myBigList.setAdapter(mBigAdapter);
		myBigList.setOnChildClickListener(this);
//		createBigList();
	}
	
/*	void fiilBigList() {
		
		ArrayList<String> abcList = new ArrayList<String>();
		listChild = new ArrayList<Object>();
		ArrayList<String> children;
		
		
//		ArrayList<String> arrItems;
		char firstLetter = 65; // Buchstabe "A"
		char lastLetter = 90; // Buchstabe "Z"

		for (int i = firstLetter; i <= lastLetter; i++) {
			char c = (char) i;
			abcList.add("" + c);

			children = new ArrayList<String>();
			for (int j = 0; i <= 10; j++) {
				children.add("Item " + j);
			}
			
			listChild.add(children);
//			abcList.add(p);
		}
		
		this.arrList=abcList;

	}
	*/
	public void setGroupData() {
		groupItem.add("TechNology");
		groupItem.add("Mobile");
		groupItem.add("Manufacturer");
		groupItem.add("Extras");
	}

	
	public void setChildGroupData() {
		/**
		 * Add Data For TecthNology
		 */
		ArrayList<String> child = new ArrayList<String>();
		child.add("Java");
		child.add("Drupal");
		child.add(".Net Framework");
		child.add("PHP");
		childItem.add(child);

		/**
		 * Add Data For Mobile
		 */
		child = new ArrayList<String>();
		child.add("Android");
		child.add("Window Mobile");
		child.add("iPHone");
		child.add("Blackberry");
		childItem.add(child);
		/**
		 * Add Data For Manufacture
		 */
		child = new ArrayList<String>();
		child.add("HTC");
		child.add("Apple");
		child.add("Samsung");
		child.add("Nokia");
		childItem.add(child);
		/**
		 * Add Data For Extras
		 */
		child = new ArrayList<String>();
		child.add("Contact Us");
		child.add("About Us");
		child.add("Location");
		child.add("Root Cause");
		childItem.add(child);
	}

	void createBigList() {

		myBigList = getExpandableListView();
		myBigList.setDividerHeight(2);
//		myBigList.setGroupIndicator(null);
		myBigList.setClickable(true);
		
//		fiilBigList();
		MyBigListAdapter myBigAdaper = new MyBigListAdapter(arrList, listChild );
		myBigList.setAdapter(myBigAdaper);
		myBigAdaper.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);
		
		  myBigList.setOnGroupExpandListener(new OnGroupExpandListener()
		  {
		   @Override
		   public void onGroupExpand(int groupPosition) 
		   {
		    Log.e("onGroupExpand", "OK");
		   }
		  });
		   
	/*	  myBigList.setOnGroupCollapseListener(new OnGroupCollapseListener()
		  {
		   @Override
		   public void onGroupCollapse(int groupPosition) 
		   {
		    Log.e("onGroupCollapse", "OK");
		   }
		  });
		   
		  myBigList.setOnChildClickListener(new OnChildClickListener()
		  {
		   @Override
		   public boolean onChildClick(ExpandableListView parent, View v,
		     int groupPosition, int childPosition, long id) {
		    Log.e("OnChildClickListener", "OK");
		    return false;
		   }
		  });*/
	}

}
