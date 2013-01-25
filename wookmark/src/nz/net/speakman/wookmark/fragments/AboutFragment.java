package nz.net.speakman.wookmark.fragments;

import nz.net.speakman.wookmark.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends WookmarkBaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about_view, null);
	}

	@Override
	public String getTitle(Context ctx) {
		return ctx.getString(R.string.fragment_title_about);
	}
}
