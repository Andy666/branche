package de.upaverlag.app.branchensuche;

import java.util.ArrayList;

import de.upaverlag.app.R;
import android.app.ExpandableListActivity;
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
	private ArrayList<Parent> arrList;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.big_list_layout);
		createBigList();
	}
	
	void fiilBigList() {
		
		ArrayList<Parent> abcList = new ArrayList<Parent>();
//		ArrayList<String> arrItems = new ArrayList<String>();
		
		ArrayList<String> arrItems;
		char firstLetter = 65; // Buchstabe "A"
		char lastLetter = 90; // Buchstabe "Z"

		for (int i = firstLetter; i <= lastLetter; i++) {
			char c = (char) i;
			Parent p = new Parent();
			p.setTitle("" + c);

			arrItems = new ArrayList<String>();
			for (int j = 0; i <= 10; j++) {
				arrItems.add("Item " + j);
			}
			
			p.setArrayChildren(arrItems);
			abcList.add(p);
		}
		
		this.arrList=abcList;

	}

	void createBigList() {

		myBigList = getExpandableListView();

		
		fiilBigList();
		myBigList.setAdapter(new MyBigListAdapter(AllCategories.this, arrList));
		
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
