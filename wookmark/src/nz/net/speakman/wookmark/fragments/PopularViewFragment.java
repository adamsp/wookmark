package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.content.Context;

public class PopularViewFragment extends BasicViewFragment {

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_popular);
	}

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_popular);
	}
}
