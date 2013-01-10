package nz.net.speakman.wookmark;

public interface Refreshable {
	public void refresh();
	public void cancel();
	public boolean refreshInProgress();
	public void setRefreshListener(RefreshListener listener);
}
