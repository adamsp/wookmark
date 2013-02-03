package nz.net.speakman.wookmark.fragments.imageviewfragments;

import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.fragments.imageviewfragments.WookmarkBaseImageViewFragment;
import android.content.Context;

public class SearchViewFragment extends WookmarkBaseImageViewFragment {

	private String mSearchTerm;
	public SearchViewFragment(String searchTerm) {
		super();
		mSearchTerm = searchTerm;
	}
	
	@Override
	public String getTitle(Context ctx) {
		return String.format(ctx.getString(R.string.fragment_title_search), mSearchTerm);
	}

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_search) + mSearchTerm;
	}
}
