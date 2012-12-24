package nz.net.speakman.wookmark;

import java.util.ArrayList;

import nz.net.speakman.wookmark.api.WookmarkDownloader;
import nz.net.speakman.wookmark.images.WookmarkImage;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.actionbarsherlock.app.SherlockListFragment;

public class ListViewFragment extends SherlockListFragment implements RefreshableView {
	String mUri;
	Context mCtx;
	AsyncTask mDownloadTask;

	public ListViewFragment(String uri) {
		mUri = uri;
		setRetainInstance(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity().getApplicationContext();
	}

	public void updateImages(ArrayList<WookmarkImage> images) {
		WookmarkArrayAdapter adapter = new WookmarkArrayAdapter(this
				.getActivity().getApplicationContext(), R.layout.text_row,
				images);
		setListAdapter(adapter);
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
