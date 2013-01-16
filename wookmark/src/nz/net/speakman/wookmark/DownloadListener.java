package nz.net.speakman.wookmark;

/**
 * Any class that wants to know about when a Downloader has started or finished
 * downloading should implement this interface.
 * @author adam
 *
 */
public interface DownloadListener {
	public void onDownloadFinished(Downloader obj);
	public void onDownloadStarted(Downloader obj);
}
