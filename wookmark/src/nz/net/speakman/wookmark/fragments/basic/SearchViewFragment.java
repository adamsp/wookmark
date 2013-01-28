package nz.net.speakman.wookmark.fragments.basic;

import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.fragments.WookmarkBaseImageViewFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchViewFragment extends BasicViewFragment {

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
