package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.widget.TextView;

public class SourceViewFragment  extends CategoryFragment {

	@Override
	protected void setUserInputDefaultText(TextView tv) {
		tv.setText(mCtx.getString(R.string.source_id_default_text));
	}

	@Override
	protected void updateUri(String input) {
		mUri = getString(R.string.wookmark_endpoint_source) + input;
	}

	@Override
	protected void showBadInputWarning(String input) {
		// input may be null.
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean inputIsValid(String input) {
		// TODO Validate input
		return true;
	}

}
