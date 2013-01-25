package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.MainActivity;
import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] menuTitles = getResources().getStringArray(R.array.menu_titles);
		ArrayAdapter<String> menuTitleAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, menuTitles);
		setListAdapter(menuTitleAdapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		WookmarkBaseFragment newContent = null;
		switch (position) {
		case 0: // Popular
			newContent = new PopularViewFragment();
			break;
		case 1: // New
			newContent = new NewViewFragment();
			break;
		case 2: // Color
			newContent = new ColorViewFragment();
			break;
		case 3: // Search
			//newContent = new SearchViewFragment(); // TODO
			break;
		case 4: // Settings
			//newContent = new SettingsViewFragment(); // TODO
			break;
		case 5: // About
			//newContent = new AboutViewFragment(); // TODO
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(WookmarkBaseFragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
	}


}
