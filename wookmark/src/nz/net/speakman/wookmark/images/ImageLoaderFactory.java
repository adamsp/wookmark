package nz.net.speakman.wookmark.images;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.fedorvlasov.lazylist.ImageLoader;

public class ImageLoaderFactory {
	private static ImageLoader imageLoader;
	public static ImageLoader getImageLoader(Context context) {
		if(imageLoader == null) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    	Display display = wm.getDefaultDisplay();
	    	int scaleSize = Math.max(display.getWidth(), display.getHeight()) / 4;
			imageLoader = new ImageLoader(context, scaleSize);
		}
		return imageLoader;
	}
}
