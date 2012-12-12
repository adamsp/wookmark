package nz.net.speakman.wookmark;

import java.util.List;

import nz.net.speakman.wookmark.images.WookmarkImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WookmarkArrayAdapter extends ArrayAdapter<WookmarkImage> {

	private List<WookmarkImage> mImages;
	private Context mContext;
	public WookmarkArrayAdapter(Context context,
			int textViewResourceId, List<WookmarkImage> objects) {
		super(context, textViewResourceId, objects);
		
		mContext = context;
		mImages = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		WookmarkImage image = mImages.get(position);
		if (convertView == null) {
			convertView = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_row, parent, false);
		}
		if (image != null) {
			TextView itemView = (TextView) convertView.findViewById(R.id.image_height);
			itemView.setText(String.valueOf(image.height()));

			itemView = (TextView) convertView.findViewById(R.id.image_width);
			itemView.setText(String.valueOf(image.width()));
			
			itemView = (TextView) convertView.findViewById(R.id.image_id);
			itemView.setText(String.valueOf(image.id()));
			
			itemView = (TextView) convertView.findViewById(R.id.image_imagePreviewUri);
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
		return convertView;
	}

}
