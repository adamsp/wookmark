package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;

public class NewViewFragment extends BasicViewFragment {

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_new);
	}

}
