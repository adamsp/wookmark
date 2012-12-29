package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.fragments.MenuFragment;
import nz.net.speakman.wookmark.fragments.PopularViewFragment;
import nz.net.speakman.wookmark.fragments.WookmarkBaseFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	private Fragment mContent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Request Feature must be called before adding content.
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		
		mContent = new PopularViewFragment();
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			if(mContent != null) {
				if(mContent instanceof WookmarkBaseFragment) {
					((WookmarkBaseFragment)mContent).refresh();
				}
			}
			break;
		}
		return super.onOptionsItemSelected(item);
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
		
	public void switchContent(WookmarkBaseFragment fragment) {
		mContent = fragment;
		setTitle(fragment.getTitle(this));
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

	
}
