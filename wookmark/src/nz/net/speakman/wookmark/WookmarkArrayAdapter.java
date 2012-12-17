package nz.net.speakman.wookmark;

import java.util.List;

import nz.net.speakman.wookmark.images.WookmarkImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;

public class WookmarkArrayAdapter extends ArrayAdapter<WookmarkImage> {

	private List<WookmarkImage> mImages;
	private ImageLoader mImageLoader;
	private Context mContext;

	public WookmarkArrayAdapter(Context context, int textViewResourceId,
			List<WookmarkImage> objects) {
		super(context, textViewResourceId, objects);
		mImageLoader = new ImageLoader(context);
		mContext = context;
		mImages = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Change this to swap views
		int layout = R.layout.image_row;
		WookmarkImage image = mImages.get(position);
		if (convertView == null) {
			convertView = ((LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(layout, parent, false);
		}
		if (image != null) {
			switch (layout) {
			case R.layout.text_row:
				displayTextResults(image, convertView);
				break;
			case R.layout.image_row:
				displayImageResults(image, convertView);
				break;
			}
		}
		return convertView;
	}

	private void displayImageResults(WookmarkImage image, View convertView) {
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.image_preview);
		mImageLoader
				.DisplayImage(image.imagePreviewUri().toString(), imageView);
//		TextView titleView = (TextView) convertView.findViewById(R.id.image_title);
//		titleView.setText(image.title());
	}

	private void displayTextResults(WookmarkImage image, View convertView) {
		TextView itemView = (TextView) convertView
				.findViewById(R.id.image_height);
		itemView.setText(String.valueOf(image.height()));

		itemView = (TextView) convertView.findViewById(R.id.image_width);
		itemView.setText(String.valueOf(image.width()));

		itemView = (TextView) convertView.findViewById(R.id.image_id);
		itemView.setText(String.valueOf(image.id()));

		itemView = (TextView) convertView
				.findViewById(R.id.image_imagePreviewUri);
		itemView.setText(image.imagePreviewUri().toString());

		itemView = (TextView) convertView.findViewById(R.id.image_imageUri);
		itemView.setText(image.imageUri().toString());

		itemView = (TextView) convertView.findViewById(R.id.image_refererUri);
		itemView.setText(image.refererUri().toString());

		itemView = (TextView) convertView.findViewById(R.id.image_title);
		itemView.setText(image.title());

		itemView = (TextView) convertView.findViewById(R.id.image_url);
		itemView.setText(image.url().toString());
	}

}
