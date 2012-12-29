package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.content.Context;
import android.widget.TextView;

public class UserViewFragment extends CategoryFragment  {

	@Override
	protected void setUserInputDefaultText(TextView tv) {
		tv.setText(mCtx.getString(R.string.user_id_default_text));
	}
	
	@Override
	protected void updateUri(String input) {	 
		mUri = getString(R.string.wookmark_endpoint_user) + input;
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
		return ctx.getString(R.string.fragment_title_user);
	}
}
