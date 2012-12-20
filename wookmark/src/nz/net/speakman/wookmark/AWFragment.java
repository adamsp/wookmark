package nz.net.speakman.wookmark;

import java.util.ArrayList;

import nz.net.speakman.wookmark.images.WookmarkImage;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.antipodalwall.AntipodalWallLayout;
import com.fedorvlasov.lazylist.ImageLoader;

public class AWFragment extends SherlockFragment {

	AntipodalWallLayout mView;
	Context mCtx;
	static ImageLoader mImageLoader;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mCtx = getActivity().getApplicationContext();
		mImageLoader = new ImageLoader(mCtx);
		if (mView == null)
			mView = (AntipodalWallLayout) inflater.inflate(R.layout.anitpodal_wall, null, false);
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// http://stackoverflow.com/questions/6526874/call-removeview-on-the-childs-parent-first
		((ViewGroup) mView.getParent()).removeView(mView);
	}

	public void updateImages(ArrayList<WookmarkImage> images) {
		for(WookmarkImage image : images) {
			ImageView iv = new ImageView(mCtx);
			// Get the width of the column, set the width to this?
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,
//					ViewGroup.LayoutParams.WRAP_CONTENT);
//			iv.setLayoutParams(lp);
			mImageLoader.DisplayImage(image.imagePreviewUri().toString(), iv);
			mView.addView(iv);
		}
	}

}
