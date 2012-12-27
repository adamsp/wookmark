package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.MainActivity;
import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
		Fragment newContent = null;
		switch (position) {
		case 0: // Popular
//			newContent = new ListViewFragment(getString(R.string.wookmark_endpoint_popular));
			newContent = new AWFragment(getString(R.string.wookmark_endpoint_popular));
			break;
		case 1: // New
//			newContent = new ListViewFragment(getString(R.string.wookmark_endpoint_new));
			newContent = new AWFragment(getString(R.string.wookmark_endpoint_new));
			break;
		case 2: // User
			// TODO UserViewFragment
			//newContent = new UserViewFragment();
			break;
		case 3: // Group
			// TODO GroupViewFragment
			//newContent = new GroupViewFragment();
			break;
		case 4: // Color
			// TODO ColorViewFragment
			//newContent = new ColorViewFragment();
			break;
		case 5: // Source
			// TODO SourceViewFragment
			//newContent = new SourceViewFragment();
			break;
		case 6: // Search
			// TODO SearchViewFragment
			//newContent = new SearchViewFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity ma = (MainActivity) getActivity();
			ma.switchContent(fragment);
		}
	}


}
