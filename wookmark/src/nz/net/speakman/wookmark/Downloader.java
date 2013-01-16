package nz.net.speakman.wookmark;

/**
 * Any class that does some downloading should implement this interface to
 * notify any listeners of downloads starting/stopping, for example in order to
 * modify UI elements in a parent View.
 * 
 * @author Adam Speakman
 * 
 */
public interface Downloader {
	public void setDownloadListener(DownloadListener listener);
	public boolean downloadInProgress();

	// TODO Is this needed? Specific case of cancelling download when fragment
	// being removed. However if the fragment knows when, for example,
	// orientation is being changed, then the fragment can handle it itself.
	// Does changing orientation with setRetainInstance(true) not call
	// onDestroy, for example?...
	public void cancelDownload();
}
