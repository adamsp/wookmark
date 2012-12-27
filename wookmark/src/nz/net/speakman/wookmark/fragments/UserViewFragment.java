package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
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

public class UserViewFragment extends WookmarkBaseFragment implements
		OnEditorActionListener {

	@Override
	public void refresh() {
		if (mUri == null) {
			showBadInputWarning();
			return;
		}
		super.refresh();
	}

	private void showBadInputWarning() {
		 // TODO Say "Hey, supply a user!"
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null)
			mView = inflater.inflate(R.layout.user_view, null, false);
		TextView tv = (TextView) mView.findViewById(R.id.userTextInput);
		tv.setOnEditorActionListener(this);
		return mView;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		boolean consumed = false;
		if (actionId == EditorInfo.IME_ACTION_DONE
				|| actionId == EditorInfo.IME_ACTION_GO
				|| actionId == EditorInfo.IME_NULL) {
			if(inputIsValid()) {
				InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(
	            		mView.findViewById(R.id.userTextInput).getWindowToken(), 0);
	            updateUri();
				refresh();
			} else {
				showBadInputWarning();
			}
			consumed = true;
		}
		return consumed;
	}
	
	private boolean inputIsValid() {
		// TODO Validate input
		String userInput = ((TextView)mView.findViewById(R.id.userTextInput)).getText().toString();
		return true;
	}
	
	private void updateUri() {
		String userInput = ((TextView)mView.findViewById(R.id.userTextInput)).getText().toString();	 
		mUri = getString(R.string.wookmark_endpoint_user) + userInput;
	}

}
