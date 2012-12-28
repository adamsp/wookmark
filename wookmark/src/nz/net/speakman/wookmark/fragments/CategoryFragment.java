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

public abstract class CategoryFragment extends WookmarkBaseFragment implements
		OnEditorActionListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null)
			mView = inflater.inflate(R.layout.category_view, null, false);
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
		if (actionId == EditorInfo.IME_ACTION_DONE
				|| actionId == EditorInfo.IME_ACTION_GO
				|| actionId == EditorInfo.IME_NULL) {
			String userInput = ((TextView)mView.findViewById(R.id.userTextInput)).getText().toString();
			if (inputIsValid(userInput)) {
				InputMethodManager imm = (InputMethodManager) mCtx
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						mView.findViewById(R.id.userTextInput).getWindowToken(),
						0);
				updateUri(userInput);
				refresh();
			} else {
				showBadInputWarning(userInput);
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
