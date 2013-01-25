package nz.net.speakman.wookmark.fragments.basic;

import android.content.Context;
import nz.net.speakman.wookmark.R;

public class NewViewFragment extends BasicViewFragment {

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_new);
	}
	
	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_new);
	}

}
