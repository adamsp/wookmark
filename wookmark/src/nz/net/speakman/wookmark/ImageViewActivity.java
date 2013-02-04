package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.images.ImageLoaderFactory;
import nz.net.speakman.wookmark.images.WookmarkImage;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.fedorvlasov.lazylist.ImageLoader;
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
			break;
		case R.id.image_view_menu_share:
			startShareIntent();
			break;
		case R.id.image_view_menu_wookmark_com:
			startWookmarkWebsiteIntent();
			break;
			// TODO Add a 'view on original site' link?
		}
		return super.onOptionsItemSelected(item);
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
		String[] details = new String[] { 
				String.format(getString(R.string.image_view_detail_image_title), mImage.getTitle()),
				String.format(getString(R.string.image_view_detail_image_width), mImage.getWidth()),
				String.format(getString(R.string.image_view_detail_image_height), mImage.getHeight()),
				String.format(getString(R.string.image_view_detail_image_referer), mImage.getRefererUri()) // TODO Auto-link this - make it clickable?
		};
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle(R.string.image_view_detail_dialog_title)
		       .setItems(details, null)
		       .setNeutralButton(android.R.string.ok, null);

		// 3. Get the AlertDialog from create()
		builder.create().show();

	}
}
