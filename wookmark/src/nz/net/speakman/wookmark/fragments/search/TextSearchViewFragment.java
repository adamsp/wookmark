package nz.net.speakman.wookmark.fragments.search;

import nz.net.speakman.wookmark.R;
import android.content.Context;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

public class TextSearchViewFragment extends SearchBaseFragment {

	// TODO Need to make the input field have a 'search' button at the end.
	@Override
	protected void setUserInputDefaultText(TextView tv) {
		tv.setText(R.string.text_search_default_text);
	}

	@Override
	protected void showBadInputWarning(String input) {
		Toast toast = Toast.makeText(mCtx,
				getString(R.string.text_search_bad_input_text),
				Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	protected boolean inputIsValid(String input) {
		if (input == null)
			return false;
		input = input.trim();
		if (input.length() == 0)
			return false;
		return true;
	}

	@Override
	protected void updateUri(String input) {
		input = input.trim();
		input = Uri.encode(input);
		mUri = getString(R.string.wookmark_endpoint_search) + input;
	}

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_search);
	}

}
