package nz.net.speakman.wookmark;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.fedorvlasov.lazylist.ImageLoader;
import nz.net.speakman.wookmark.images.ImageLoaderFactory;
import nz.net.speakman.wookmark.images.WookmarkImage;
import nz.net.speakman.wookmark.views.TouchImageView;

public class ImageViewActivity extends SherlockActivity {

	public static final String IMAGE_KEY = "ImageKey";

    private int MAX_IMAGE_ZOOM = 4;
	
	private static ImageLoader mImageLoader;
	
	private WookmarkImage mImage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Request Feature must be called before adding content.
		// Note this turns it on by default, ABS thing.
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		if(mImageLoader == null) mImageLoader = ImageLoaderFactory.getImageLoader(getApplicationContext());
		
		setContentView(R.layout.image_view);
		
		Intent intent = getIntent();
		mImage = (WookmarkImage)intent.getParcelableExtra(IMAGE_KEY);

		TouchImageView iv = (TouchImageView)findViewById(R.id.image_fullsize);
        iv.setMaxZoomCalculator(new TouchImageView.MaxZoomCalculator() {
            // Max Zoom is MAX_IMAGE_ZOOM x width of the image when it fills width of the view.
            @Override
            public float calculateMaxZoom(float viewWidth, float viewHeight, float fitImageToViewScale) {
                // First, need the inverse of the scale so we can get the image back to "original" size.
                float fitImageToViewScaleInverse = 1 / fitImageToViewScale;
                // Now, we need the value to use to scale the "original" size to fit the view width (if image is tall)
                // or view height (if image is wide).
                float originalToViewWidthScale;
                if(mImage.getWidth() < mImage.getHeight()) {
                    originalToViewWidthScale = viewWidth / mImage.getWidth();
                } else {
                    originalToViewWidthScale = viewHeight / mImage.getHeight();
                }
                // Finally, we multiply them all together and by MAX_IMAGE_ZOOM, to make the max zoom 4x the
                // width of the image when it is scaled to fit the width of the view.
                float maxZoom = fitImageToViewScaleInverse * originalToViewWidthScale * MAX_IMAGE_ZOOM;
                return maxZoom;
            }
        });
		mImageLoader.DisplayImage(mImage.getImageUri().toString(), iv, false, new ImageLoader.OnImageLoadFinishedListener() {
			@Override
			public void onFinished() {
				setSupportProgressBarIndeterminateVisibility(false);
			}
		});
		setTitle(mImage.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.image_view_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case R.id.image_view_menu_detail:
                showAdditionalDetailDialog();
                return true;
            case R.id.image_view_menu_share:
                startShareIntent();
                return true;
            case R.id.image_view_menu_wookmark_com:
                startWookmarkWebsiteIntent();
                return true;
            case R.id.image_view_lock_orientation:
                toggleOrientationLock();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
		return super.onOptionsItemSelected(item);
	}

    private void toggleOrientationLock() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            switch(getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
            }
        }
    }

    private void startShareIntent() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.image_view_share_content), mImage.getTitle(), mImage.getUrl()));
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.image_view_share_subject));
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.image_view_share_title)));
	}
	
	private void startWookmarkWebsiteIntent() {
		Intent sendIntent = new Intent(Intent.ACTION_VIEW, mImage.getUrl());
		startActivity(sendIntent);
	}

	private void showAdditionalDetailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View v = getLayoutInflater().inflate(R.layout.image_view_detail_dialog, null);

        String title = mImage.getTitle();
        if(null == title || title.trim().length() == 0)
            title = getString(R.string.image_view_dialog_image_no_title);

        TextView tv = (TextView)v.findViewById(R.id.image_view_dialog_title);
        tv.setText(title);

        tv = (TextView)v.findViewById(R.id.image_view_dialog_height);
        tv.setText(String.valueOf(mImage.getHeight()));

        tv = (TextView)v.findViewById(R.id.image_view_dialog_width);
        tv.setText(String.valueOf(mImage.getWidth()));

        tv = (TextView)v.findViewById(R.id.image_view_dialog_referer);
        tv.setText(mImage.getRefererUri().toString());

        builder.setView(v);
        builder.setTitle(R.string.image_view_detail_dialog_title)
                .setNeutralButton(android.R.string.ok, null);

        builder.create().show();
	}
}
