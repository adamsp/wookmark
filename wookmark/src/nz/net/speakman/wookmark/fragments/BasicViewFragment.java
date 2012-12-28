package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A basic view that displays images returned from the URI set by the subclass.
 * 
 * @author Adam Speakman
 * 
 */
public abstract class BasicViewFragment extends WookmarkBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setUri(); // TODO Should this be called here? Check lifecycle.
		if (mView == null)
			mView = inflater.inflate(R.layout.antipodal_wall, null, false);
		return mView;
	}

	public abstract void setUri();
	
}
