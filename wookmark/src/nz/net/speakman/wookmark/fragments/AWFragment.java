package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AWFragment extends WookmarkBaseFragment {
	
	public AWFragment(String uri) {
		mUri = uri;
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null)
			mView = inflater.inflate(R.layout.antipodal_wall, null, false);
		return mView;
	}
}
