package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.images.ImageLoaderFactory;
import nz.net.speakman.wookmark.images.WookmarkImage;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.fedorvlasov.lazylist.ImageLoader;

public class ImageViewActivity extends SherlockActivity {

	public static final String IMAGE_KEY = "ImageKey";
	
	private static ImageLoader mImageLoader;
	
	private WookmarkImage image;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(mImageLoader == null) mImageLoader = ImageLoaderFactory.getImageLoader(getApplicationContext());
		
		setContentView(R.layout.image_view);
		
		Intent intent = getIntent();
		image = (WookmarkImage)intent.getParcelableExtra(IMAGE_KEY);
		
		ImageView iv = (ImageView)findViewById(R.id.image_fullsize);
		mImageLoader.DisplayImage(image.imageUri().toString(), iv, false);
		
		((TextView)findViewById(R.id.image_title)).setText(image.title());
		
		((Button)findViewById(R.id.wookmark_site_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Go to the site!
			}
		});
	}
}
