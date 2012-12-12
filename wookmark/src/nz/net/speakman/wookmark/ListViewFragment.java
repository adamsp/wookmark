package nz.net.speakman.wookmark;

import java.util.ArrayList;

import nz.net.speakman.wookmark.images.WookmarkImage;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;

public class ListViewFragment extends SherlockListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void updateImages(ArrayList<WookmarkImage> images) {
		WookmarkArrayAdapter adapter = new WookmarkArrayAdapter(this.getActivity().getApplicationContext(), R.layout.text_row, images);
		setListAdapter(adapter);
	}
}
