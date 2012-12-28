package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Displays a single image and associated details.
 * @author Adam Speakman
 *
 */
public class ImageViewFragment extends SherlockFragment {
	View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null)
			mView = inflater.inflate(R.layout.image_view, null, false);
		return mView;
	}
}
