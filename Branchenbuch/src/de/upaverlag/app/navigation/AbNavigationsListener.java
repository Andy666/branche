package de.upaverlag.app.navigation;

import de.upaverlag.app.R;
import de.upaverlag.app.branchensuche.BranchenSuche;
import de.upaverlag.app.extendedSearch.ExtendedSearch;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AbNavigationsListener implements ActionBar.OnNavigationListener{
	
 	private Fragment mFragment;
 	
 	private String [] menuItemTitel;
 	FragmentManager fManager;
 	
 	public AbNavigationsListener (String [] arrString, FragmentManager fmg){
 		this.menuItemTitel = arrString;
 		this.fManager = fmg;
 	}
 	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		
//		mFragment = fManager.findFragmentByTag(menuItemTitel[itemPosition]);

//		if(mFragment==null){
			if(menuItemTitel[itemPosition].equals("Erweiterte Suche")){
			mFragment = new ExtendedSearch();
			}
		
			if(menuItemTitel[itemPosition].equals("Branchensuche")){
			mFragment = new BranchenSuche();
			}
//			}
		FragmentTransaction fTrans = fManager.beginTransaction();
		fTrans.replace(R.id.placeholder, mFragment, menuItemTitel[itemPosition]);
		fTrans.commit();
		
		return true;
		
	}

}
