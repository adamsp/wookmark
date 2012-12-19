package nz.net.speakman.wookmark;

import nz.net.speakman.wookmark.images.WookmarkImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fedorvlasov.lazylist.ImageLoader;

public class StaggeredAdapter extends ArrayAdapter<WookmarkImage> {

	private ImageLoader mLoader;

	public StaggeredAdapter(Context context, int textViewResourceId,
			WookmarkImage[] objects) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position).imagePreviewUri().toString(), holder.imageView);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
}
