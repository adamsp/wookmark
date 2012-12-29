package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.content.Context;
import android.widget.TextView;

public class ColorViewFragment extends CategoryFragment {

	/**
	 * Color is RGB, ie FF0000 is Red colors.
	 */
	
	@Override
	protected void setUserInputDefaultText(TextView tv) {
		tv.setText(mCtx.getString(R.string.color_id_default_text));
	}

	@Override
	protected void updateUri(String input) {
		mUri = getString(R.string.wookmark_endpoint_color) + input;
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

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_color);
	}
}
