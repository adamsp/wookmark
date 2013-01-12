package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.fragments.MenuFragment;
import nz.net.speakman.wookmark.fragments.PopularViewFragment;
import nz.net.speakman.wookmark.fragments.WookmarkBaseFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements RefreshListener {

	private Fragment mContent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Request Feature must be called before adding content.
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		
		WookmarkBaseFragment fragment = new PopularViewFragment();
		fragment.setRefreshListener(this);
		mContent = fragment;
		
		// set the Above View
		setContentView(R.layout.content_frame);
		setAboveView(mContent);
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();
		
        // configure the SlidingMenu
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
	}
	
	@Override
	protected void onDestroy() {
		if(mContent != null) {
			Log.d("Wookmark", "Killing background download task as onDestroy() was called before it returned.");
			if(mContent instanceof WookmarkBaseFragment) {
				((WookmarkBaseFragment)mContent).cancel();
			}
		}
		super.onDestroy();
	}

	private void setAboveView(Fragment fragment){
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();	
	}
		
	public void switchContent(WookmarkBaseFragment fragment) {
		mContent = fragment;
		fragment.setRefreshListener(this);
		setTitle(fragment.getTitle(this));
		setAboveView(fragment);
		getSlidingMenu().showContent();
	}

	@Override
	public void onRefreshFinished(Refreshable obj) {
		setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void onRefreshStarted(Refreshable obj) {
		setSupportProgressBarIndeterminateVisibility(true);
	}

	
}
