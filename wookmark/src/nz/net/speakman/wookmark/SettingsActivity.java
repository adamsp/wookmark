package nz.net.speakman.wookmark;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.actionbarsherlock.app.SherlockPreferenceActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 17/02/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
