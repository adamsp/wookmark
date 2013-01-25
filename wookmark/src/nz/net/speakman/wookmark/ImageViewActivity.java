package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.images.ImageLoaderFactory;
import nz.net.speakman.wookmark.images.WookmarkImage;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.fedorvlasov.lazylist.ImageLoader;

public class ImageViewActivity extends SherlockActivity {

	public static final String IMAGE_KEY = "ImageKey";
	
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

		ImageView iv = (ImageView)findViewById(R.id.image_fullsize);
		mImageLoader.DisplayImage(mImage.imageUri().toString(), iv, false, new ImageLoader.OnImageLoadFinishedListener() {
			@Override
			public void onFinished() {
				setSupportProgressBarIndeterminateVisibility(false);
			}
		});
		setTitle(mImage.title());
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
			// TODO
			break;
		case R.id.image_view_menu_wookmark_com:
			// TODO
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showAdditionalDetailDialog() {
		String[] details = new String[] { 
				String.format(getString(R.string.image_view_detail_image_title), mImage.title()),
				String.format(getString(R.string.image_view_detail_image_width), mImage.width()),
				String.format(getString(R.string.image_view_detail_image_height), mImage.height())
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
