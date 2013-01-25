package nz.net.speakman.wookmark.fragments;

import android.content.Context;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class WookmarkBaseFragment extends SherlockFragment {
	public abstract String getTitle(Context ctx);
}
