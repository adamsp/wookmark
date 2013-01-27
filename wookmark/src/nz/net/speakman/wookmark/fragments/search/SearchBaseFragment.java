package nz.net.speakman.wookmark.fragments.search;

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

public abstract class SearchBaseFragment extends WookmarkBaseImageViewFragment implements
		OnEditorActionListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null)
			mView = inflater.inflate(R.layout.search_view, null, false);
		TextView tv = (TextView) mView.findViewById(R.id.userTextInput);
		tv.setOnEditorActionListener(this);
		setUserInputDefaultText(tv);
		return mView;
	}
	
	@Override
	public void refresh() {
		if (mUri == null) {
			showBadInputWarning(null);
			return;
		}
		super.refresh();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		boolean consumed = false;
		if (actionId == EditorInfo.IME_NULL
				|| actionId == EditorInfo.IME_ACTION_SEARCH) {
			TextView tv = (TextView)mView.findViewById(R.id.userTextInput);
			String userInput = tv.getText().toString();
			if (inputIsValid(userInput)) {
				InputMethodManager imm = (InputMethodManager) mCtx
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						mView.findViewById(R.id.userTextInput).getWindowToken(),
						0);
				updateUri(userInput);
				refresh();
				tv.clearFocus();
			} else {
				showBadInputWarning(userInput);
				tv.requestFocus();
			}
			consumed = true;
		}
		return consumed;
	}
	
	protected abstract void setUserInputDefaultText(TextView tv);
	
	protected abstract void showBadInputWarning(String input);

	protected abstract boolean inputIsValid(String input);

	protected abstract void updateUri(String input);
}
