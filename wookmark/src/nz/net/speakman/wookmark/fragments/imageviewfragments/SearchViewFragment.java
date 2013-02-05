package nz.net.speakman.wookmark.fragments.imageviewfragments;

import android.net.Uri;
import android.os.Bundle;
import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.fragments.imageviewfragments.WookmarkBaseImageViewFragment;
import android.content.Context;

public class SearchViewFragment extends WookmarkBaseImageViewFragment {

	private String mSearchTerm;
	public SearchViewFragment(String searchTerm) {
		super();
		mSearchTerm = searchTerm.trim();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) // First-time load!
            getNewImages();
    }
	
	@Override
	public String getTitle(Context ctx) {
		return String.format(ctx.getString(R.string.fragment_title_search), mSearchTerm);
	}

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_search) + Uri.encode(mSearchTerm);
	}
}
