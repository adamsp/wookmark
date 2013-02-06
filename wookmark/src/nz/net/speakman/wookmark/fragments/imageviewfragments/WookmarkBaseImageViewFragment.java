package nz.net.speakman.wookmark.fragments.imageviewfragments;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.widget.Toast;
import nz.net.speakman.wookmark.DownloadListener;
import nz.net.speakman.wookmark.Downloader;
import nz.net.speakman.wookmark.ImageViewActivity;
import nz.net.speakman.wookmark.R;
import nz.net.speakman.wookmark.api.WookmarkDownloader;
import nz.net.speakman.wookmark.fragments.WookmarkBaseFragment;
import nz.net.speakman.wookmark.images.ImageLoaderFactory;
import nz.net.speakman.wookmark.images.WookmarkImage;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.antipodalwall.AntipodalWallLayout;
import com.fedorvlasov.lazylist.ImageLoader;

/**
 * Contains basic functionality common across all fragments that
 * display multiple images in an AntipodalWall layout.
 * @author Adam Speakman
 *
 */
public abstract class WookmarkBaseImageViewFragment extends WookmarkBaseFragment implements Adapter, Downloader {
	/**
	 * The number of views before the end of available ones before
	 * we start to download new ones.
	 */
	private static final int END_OF_LIST_BUFFER_VALUE = 10;
	protected View mView;
	AsyncTask mDownloadTask;
	protected String mUri;
	SparseArray<WookmarkImage> mImageMapping;
	ArrayList<WookmarkImage> mImages;
	static ImageLoader mImageLoader;
	ArrayList<DataSetObserver> mDataSetObservers;
	ArrayList<DownloadListener> mRefreshListeners;
    /**
     * The next page to request from Wookmark when fetching new images.
     */
	int mPage;
    /**
     * Used to track if there are no more images available
     * from Wookmark, so we don't keep requesting empty result sets.
     */
    private boolean mNoMoreImages;

    public WookmarkBaseImageViewFragment() {
		if (mRefreshListeners == null)
			mRefreshListeners = new ArrayList<DownloadListener>();
		setRetainInstance(true);
	}

    public abstract void setUri();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(mImageMapping == null)
			mImageMapping = new SparseArray<WookmarkImage>();
		if (mImageLoader == null)
			mImageLoader = ImageLoaderFactory.getImageLoader(getSherlockActivity());
        setUri();
	}

    @Override
    public void onDestroy() {
        cancelDownload();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // http://stackoverflow.com/questions/6526874/call-removeview-on-the-childs-parent-first
        if(mView != null)
            ((ViewGroup) mView.getParent()).removeView(mView);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null)
            mView = inflater.inflate(R.layout.basic_view, null, false);
        return mView;
    }
	
	protected void getNewImages() {
		if(downloadInProgress()) {
			Log.d("Wookmark", "Download already in progress, not updating images.");
			return;
		}
		Log.d("Wookmark", "Fetching new images.");
        for(DownloadListener listener : mRefreshListeners) {
            listener.onDownloadStarted(this);
        }
		mDownloadTask = new DownloadImagesTask().execute(mPage);
		mPage++;
	}
	
	@Override
	public void cancelDownload() {
		if(mDownloadTask != null) {
			mDownloadTask.cancel(true);
		}
        notifyListenersDownloadIsFinished();
	}
	
	@Override
	public boolean downloadInProgress() {
		if(mDownloadTask == null) return false;
		Status status = mDownloadTask.getStatus();
		if(status == Status.PENDING || status == Status.RUNNING)
			return true;
		return false;
	}

	@Override
	public void setDownloadListener(DownloadListener listener) {
		mRefreshListeners.add(listener);
	}
	
	protected void onDownloadFinished(ArrayList<WookmarkImage> images) {
        if(images != null) {
		    addNewImages(images);
        }
        notifyListenersDownloadIsFinished();
	}

    private void notifyListenersDownloadIsFinished() {
        for(DownloadListener listener : mRefreshListeners) {
            listener.onDownloadFinished(this);
        }
    }

    protected void addNewImages(ArrayList<WookmarkImage> images) {
		if(mImages == null) mImages = new ArrayList<WookmarkImage>();
        if(images.size() == 0) {
            mNoMoreImages = true;
        }
		mImages.addAll(images);
		((AntipodalWallLayout)mView.findViewById(R.id.antipodal_wall)).setAdapter(this);
//		for(DataSetObserver observer : mDataSetObservers) {
//			// TODO Notify the observer that the data has changed?
//		}
	}

	/**
	 * Adapter methods.
	 */
	@Override
	public int getCount() {
		if (mImages == null) return 0;
		return mImages.size();
	}

	@Override
	public Object getItem(int position) {
		if (mImages == null || mImages.size() < position || position < 0) return null;
		return mImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO This defaults to 0 - is this right?...
		if(mImages == null || mImages.size() < position) return 0;
		return mImages.get(position).getId();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(mImages == null || mImages.size() < position || position < 0) return null;
		if(nearingEndOfAvailableViews(position) && !mNoMoreImages) {
			getNewImages();
		}
		
		ImageView iv = null;
		if(convertView instanceof ImageView)
			iv = (ImageView)convertView;
		if(iv == null) {
			iv = new ImageView(getSherlockActivity());
		}
		WookmarkImage image = mImages.get(position);
		iv.setId(image.getId());
		mImageMapping.put(image.getId(), image);
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				WookmarkImage image;
				if((image = mImageMapping.get(id)) != null) {
					Intent intent = new Intent(getSherlockActivity(), ImageViewActivity.class);
					intent.putExtra(ImageViewActivity.IMAGE_KEY, image);
					startActivity(intent);
				}
			}
		});
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(image.getWidth(), MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(image.getHeight(),
				MeasureSpec.EXACTLY);
		iv.measure(widthMeasureSpec, heightMeasureSpec);
		mImageLoader.DisplayImage(image.getImagePreviewUri().toString(), iv, true, null);
		return iv;
	}

	private boolean nearingEndOfAvailableViews(int position) {
		if (mImages == null || mImages.size() == 0)
			return false;
		if (position > mImages.size() - END_OF_LIST_BUFFER_VALUE)
			return true;
		return false;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return (mImages == null || mImages.size() == 0);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		if(mDataSetObservers == null) mDataSetObservers = new ArrayList<DataSetObserver>();
		mDataSetObservers.add(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if(mDataSetObservers != null)
			mDataSetObservers.remove(observer);
	}
	
	/**
	 * Task for downloading images off the UI thread.
	 * @author adam
	 *
	 */
	private class DownloadImagesTask extends
	AsyncTask<Integer, Void, ArrayList<WookmarkImage>> {
		
		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		@Override
		protected ArrayList<WookmarkImage> doInBackground(Integer... params) {
			if(mUri == null) return new ArrayList<WookmarkImage>();
			WookmarkDownloader wd = new WookmarkDownloader(
					new DefaultHttpClient());
			String endpoint = mUri;
			if(params.length > 0) {
				if(mUri.equals(getString(R.string.wookmark_endpoint_new))
						|| mUri.equals(getString(R.string.wookmark_endpoint_popular))) {
					endpoint += "?page=" + params[0];
				} else {
					endpoint += "&page=" + params[0];
				}
			}
			return wd.getImages(endpoint);
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
			if (null == results) {
                Toast.makeText(getSherlockActivity(),
                        R.string.wall_view_no_connection_message,
                        Toast.LENGTH_LONG)
                        .show();
			}
			onDownloadFinished(results);
		}
	}
}
