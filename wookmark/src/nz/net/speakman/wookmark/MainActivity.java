package nz.net.speakman.wookmark;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import nz.net.speakman.wookmark.fragments.imageviewfragments.*;
import nz.net.speakman.wookmark.fragments.MenuFragment;
import nz.net.speakman.wookmark.fragments.WookmarkBaseFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements DownloadListener {

	private Fragment mContent;
	private boolean mProgressBarVisibility;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Request Feature must be called before adding content.
		// Note this turns it on by default, ABS thing.
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null){
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            String defaultView = prefs.getString(getString(R.string.pref_default_view_key),
                    getString(R.string.pref_default_view_default_value));
			WookmarkBaseImageViewFragment fragment;
            if(defaultView.equals(getString(R.string.fragment_title_popular))) {
                fragment = new PopularViewFragment();
            } else {
                fragment = new NewViewFragment();
            }
			fragment.setDownloadListener(this);
			mContent = fragment;
			// set the Above View
			setContentView(R.layout.content_frame);
			setAboveView(mContent);
		} else {
			int id = savedInstanceState.getInt("mContent");
			mContent = getSupportFragmentManager().findFragmentById(id);
			if(mContent instanceof Downloader) {
				((Downloader)mContent).setDownloadListener(this);
			}
			if(mContent instanceof WookmarkBaseFragment) {
				setTitle(((WookmarkBaseFragment)mContent).getTitle(this));
			}
			setContentView(R.layout.content_frame);
			boolean progressBarVisible = savedInstanceState.getBoolean("mProgressBarVisibility");
			setSupportProgressBarIndeterminateVisibility(progressBarVisible);
		}
		
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

        setSlidingActionBarEnabled(false);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        if(mContent != null && mContent instanceof ColorSearchViewFragment) {
            if(((ColorSearchViewFragment)mContent).onKeyDown(keyCode, event))
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    	menu.add(R.string.text_search_default_text)
            .setIcon(R.drawable.action_search)
            .setActionView(R.layout.collapsible_edittext)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    	final MenuItem item = menu.getItem(0);
    	EditText et = (EditText)item.getActionView();
    	et.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean consumed = false;
				if (actionId == EditorInfo.IME_NULL
						|| actionId == EditorInfo.IME_ACTION_SEARCH) {
					String userInput = v.getText().toString();
					if (inputIsValid(userInput)) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(
								v.getWindowToken(),
								0);
						item.collapseActionView();
						SearchViewFragment f = new SearchViewFragment(userInput);
						switchContent(f);
					} else {
						showBadInputWarning(userInput);
						v.requestFocus();
					}
					consumed = true;
				}
				return consumed;
			}
		});

        return true;
    }
    	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSlidingMenu().toggle();
                return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
		mProgressBarVisibility = visible;
		super.setSupportProgressBarIndeterminateVisibility(visible);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int id = mContent.getId();
		outState.putInt("mContent", id);
		outState.putBoolean("mProgressBarVisibility", mProgressBarVisibility);
	}

	private void setAboveView(Fragment fragment){
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();	
	}
		
	public void switchContent(WookmarkBaseFragment fragment) {
		mContent = fragment;
		if(fragment instanceof WookmarkBaseImageViewFragment) {
			((WookmarkBaseImageViewFragment)fragment).setDownloadListener(this);
		}
		setTitle(fragment.getTitle(this));
		setAboveView(fragment);
		getSlidingMenu().showContent();
	}

	@Override
	public void onDownloadFinished(Downloader obj) {
		setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void onDownloadStarted(Downloader obj) {
		setSupportProgressBarIndeterminateVisibility(true);
	}
	
	public void showBadInputWarning(String input) {
		Toast toast = Toast.makeText(this,
				getString(R.string.text_search_bad_input_text),
				Toast.LENGTH_SHORT);
		toast.show();
	}

	public boolean inputIsValid(String input) {
		if (input == null)
			return false;
		input = input.trim();
		if (input.length() == 0)
			return false;
		return true;
	}

	
}
