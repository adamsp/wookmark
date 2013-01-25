package nz.net.speakman.wookmark.fragments.search;

import nz.net.speakman.wookmark.R;
import android.content.Context;
import android.widget.TextView;

public class TextSearchViewFragment extends SearchBaseFragment  {

	// TODO Need to make the input field have a 'search' button at the end.
	@Override
	protected void setUserInputDefaultText(TextView tv) {
		tv.setText(R.string.text_search_default_text);
	}

	@Override
	protected void showBadInputWarning(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean inputIsValid(String input) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void updateUri(String input) {
		mUri = getString(R.string.wookmark_endpoint_search) + input;
	}

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_search);
	}

}
