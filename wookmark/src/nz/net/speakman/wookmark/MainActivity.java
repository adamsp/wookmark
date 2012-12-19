package nz.net.speakman.wookmark;

import java.util.ArrayList;

import org.apache.http.impl.client.DefaultHttpClient;

import nz.net.speakman.wookmark.api.WookmarkDownloader;
import nz.net.speakman.wookmark.images.WookmarkImage;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {

//	private ListViewFragment mListViewFragment;
	private AWFragment mAWFragment;
	private AsyncTask mDownloadTask;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Request Feature must be called before adding content.
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		mAWFragment = new AWFragment();
//		mListViewFragment = new ListViewFragment();
		ft.add(R.id.mainView, mAWFragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			mDownloadTask = new DownloadImagesTask().execute();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		if(null != mDownloadTask) {
			Log.d("WSNZ", "Killing background download task as onDestroy() was called before it returned.");
			mDownloadTask.cancel(true);
		}
		super.onDestroy();
	}
	
	private void showResults(ArrayList<WookmarkImage> results) {
		mAWFragment.updateImages(results);
//		mListViewFragment.updateImages(results);
	}

	private class DownloadImagesTask extends
			AsyncTask<Integer, Void, ArrayList<WookmarkImage>> {
		/**
		 * Called before the worker thread is executed. Runs on the UI thread.
		 */
		@Override
		protected void onPreExecute() {
			// mDownloading = true;
			// updateRefreshButtonVisibility();
			setSupportProgress(Window.PROGRESS_END);
			setSupportProgressBarIndeterminateVisibility(true);
		}

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		@Override
		protected ArrayList<WookmarkImage> doInBackground(Integer... params) {
			WookmarkDownloader wd = new WookmarkDownloader(
					new DefaultHttpClient());
			return wd.getImages("http://www.wookmark.com/api/json/popular");
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
			// mDownloading = false;
			setSupportProgressBarIndeterminateVisibility(false);
			// updateRefreshButtonVisibility();
			if (null == results) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setTitle("No Connection")
						.setMessage(
								"There appears to be a problem "
										+ "with the connection. Please make sure "
										+ "you have internet connectivity.")
						.setNeutralButton("Close", null);
				dialog.show();
				results = new ArrayList<WookmarkImage>();
			}
			showResults(results);
			// mQuakes = results;
			// Update our "last checked" key in the prefs.
			// if (results.size() > 0) {
			// SharedPreferences prefs =
			// PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			// Editor editor = prefs.edit();
			// editor.putString(GeonetService.KEY_PREFS_LAST_CHECKED_ID, results
			// .get(0).getReference());
			// editor.commit();
			// }
			// updateQuakesDisplay();
		}
	}
}
