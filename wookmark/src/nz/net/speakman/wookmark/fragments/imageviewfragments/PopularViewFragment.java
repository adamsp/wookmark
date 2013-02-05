package nz.net.speakman.wookmark.fragments.imageviewfragments;

import android.os.Bundle;
import nz.net.speakman.wookmark.R;
import android.content.Context;
import nz.net.speakman.wookmark.fragments.imageviewfragments.WookmarkBaseImageViewFragment;

public class PopularViewFragment extends WookmarkBaseImageViewFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) // First-time load!
            getNewImages();
    }

	@Override
	public void setUri() {
		mUri = getString(R.string.wookmark_endpoint_popular);
	}

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_popular);
	}
}
