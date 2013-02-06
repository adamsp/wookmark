package nz.net.speakman.wookmark.fragments;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;
import nz.net.speakman.wookmark.MainActivity;
import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import nz.net.speakman.wookmark.fragments.imageviewfragments.NewViewFragment;
import nz.net.speakman.wookmark.fragments.imageviewfragments.PopularViewFragment;

public class MenuFragment extends Fragment implements View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.menu, null);
        for(int i = 0; i < layout.getChildCount(); i++) {
            layout.getChildAt(i).setOnClickListener(this);
        }
        return layout;
	}

    @Override
    public void onClick(View v) {
        WookmarkBaseFragment newContent = null;
        switch(v.getId()) {
            case R.id.menu_entry_about:
                newContent = new AboutFragment();
                break;
            case R.id.menu_entry_color_search:
                newContent = new ColorSearchViewFragment();
                break;
            case R.id.menu_entry_new:
                newContent = new NewViewFragment();
                break;
            case R.id.menu_entry_popular:
                newContent = new PopularViewFragment();
                break;
            case R.id.menu_entry_settings:
                //newContent = new SettingsFragment(); // TODO
                break;
        }
        if(newContent != null)
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
