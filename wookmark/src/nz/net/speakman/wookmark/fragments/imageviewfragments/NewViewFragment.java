package nz.net.speakman.wookmark.fragments.imageviewfragments;

import android.content.Context;
import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.fragments.imageviewfragments.WookmarkBaseImageViewFragment;

public class NewViewFragment extends WookmarkBaseImageViewFragment {

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_new);
	}
	
	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_new);
	}

}
