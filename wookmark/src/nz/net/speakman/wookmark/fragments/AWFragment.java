package nz.net.speakman.wookmark.fragments;

import java.util.ArrayList;

import nz.net.speakman.wookmark.MainActivity;
import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.RefreshableView;
import nz.net.speakman.wookmark.api.WookmarkDownloader;
import nz.net.speakman.wookmark.images.WookmarkImage;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.antipodalwall.AntipodalWallLayout;
import com.fedorvlasov.lazylist.ImageLoader;

public class AWFragment extends SherlockFragment implements RefreshableView {

	AntipodalWallLayout mView;
	Context mCtx;
	String mUri;
	static ImageLoader mImageLoader;
	AsyncTask mDownloadTask;
	
	public AWFragment(String uri) {
		mUri = uri;
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mCtx = getActivity().getApplicationContext();
		mImageLoader = new ImageLoader(mCtx);
		if (mView == null)
			mView = (AntipodalWallLayout) inflater.inflate(R.layout.antipodal_wall, null, false);
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
			mImageLoader.DisplayImage(image.imagePreviewUri().toString(), iv);
			mView.addView(iv);
		}
	}
	
	public void refresh() {
		mDownloadTask = new DownloadImagesTask().execute();
	}
	
	public void cancel() {
		if(mDownloadTask != null) {
			mDownloadTask.cancel(true);
		}
	}

	private class DownloadImagesTask extends
			AsyncTask<Integer, Void, ArrayList<WookmarkImage>> {
		/**
		 * Called before the worker thread is executed. Runs on the UI thread.
		 */
		@Override
		protected void onPreExecute() {
			if (getActivity() instanceof MainActivity) {
				MainActivity ma = (MainActivity) getActivity();
				ma.setSupportProgress(Window.PROGRESS_END);
				ma.setSupportProgressBarIndeterminateVisibility(true);
			}
		}

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		@Override
		protected ArrayList<WookmarkImage> doInBackground(Integer... params) {
			WookmarkDownloader wd = new WookmarkDownloader(
					new DefaultHttpClient());
			return wd.getImages(mUri);
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		@Override
		protected void onPostExecute(ArrayList<WookmarkImage> results) {
			if (isCancelled()) {
				Log.d("Wookmark",
						"This download task has been killed. Not updating results.");
				return;
			}
			if (getActivity() instanceof MainActivity) {
				((MainActivity) getActivity())
						.setSupportProgressBarIndeterminateVisibility(false);
			}
			if (null == results) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(mCtx);
				dialog.setTitle("No Connection")
						.setMessage(
								"There appears to be a problem "
										+ "with the connection. Please make sure "
										+ "you have internet connectivity.")
						.setNeutralButton("Close", null);
				dialog.show();
				results = new ArrayList<WookmarkImage>();
			}
			updateImages(results);
		}
	}

}
