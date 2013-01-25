package nz.net.speakman.wookmark.images;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Represents a Wookmark Image.
 * @author Adam Speakman
 *
 */
public class WookmarkImage implements Parcelable {
	
	private int height;
	private int width;
	private int id;
	private String imageUriString;
	private String imagePreviewUriString;
	private String refererUriString;
	private String title;
	private String urlString;
	
	// Private constructor, can only create from Json
	private WookmarkImage() { }
	
	/***
	 * Generates a WookmarkImage from a Json representation.
	 * @param json
	 * @return
	 */
	public static WookmarkImage generateFromJson(JSONObject json) {
		if(json == null) throw new NullPointerException();
		WookmarkImage i = new WookmarkImage();
		try {
			i = new WookmarkImage(
				json.getInt("height"),
				json.getInt("width"),
				json.getInt("id"),
				json.getString("image"),
				json.getString("preview"),
				json.getString("referer"),
				json.getString("title"),
				json.getString("url"));
		} catch (JSONException e) {
			Log.e("Wookmark", "Ohshit, something went wrong with the Json!", e);
		}
		return i;
	}
	
	private WookmarkImage(
			int height,
			int width,
			int id,
			String imageUriString,
			String imagePreviewUriString,
			String refererUriString,
			String title,
			String urlString) {
		this.height = height;
		this.width = width;
		this.id = id;
		this.imageUriString = imageUriString;
		this.imagePreviewUriString = imagePreviewUriString;
		this.refererUriString = refererUriString;
		this.title = title;
		this.urlString = urlString;
	}
	
	/***
	 * Height of the image.
	 * @return The height of the image, in pixels.
	 */
	public int getHeight() { return height ; }
	
	/***
	 * Width of the image.
	 * @return The width of the image, in pixels.
	 */
	public int getWidth() { return width ; }
	
	/***
	 * Image ID on Wookmark.com.
	 * @return The unique image ID.
	 */
	public int getId() { return id; }
	
	/***
	 * The image title on Wookmark.com.
	 * @return The image title.
	 */
	public String getTitle() { return title; }
	
	private Uri _imageUri;
	/***
	 * The Uri of the image on Wookmark.com.
	 * @return The image Uri.
	 */
	public Uri getImageUri() {
		if(_imageUri == null)
			_imageUri = Uri.parse(imageUriString);
		return _imageUri;
	}
	
	private Uri _imagePreviewUri;
	/***
	 * The Uri of a preview (thumbnail) of the image on Wookmark.com.
	 * @return The image preview Uri.
	 */
	public Uri getImagePreviewUri() {
		if(_imagePreviewUri == null)
			_imagePreviewUri = Uri.parse(imagePreviewUriString);
		return _imagePreviewUri;
	}
	
	private Uri _refererUri;
	/***
	 * The Uri of the image referer.
	 * @return The referer Uri.
	 */
	public Uri getRefererUri() {
		if(_refererUri == null)
			_refererUri = Uri.parse(refererUriString);
		return _refererUri;
	}
	
	private Uri _url;
	/***
	 * The Url of the image page on Wookmark.com - not the image itself,
	 * rather the page it appears on.
	 * @return The Url of the images page.
	 */
	public Uri getUrl() {
		if(_url == null)
			_url = Uri.parse(urlString);
		return _url;
	}
	
	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<WookmarkImage> CREATOR = new Parcelable.Creator<WookmarkImage>() {
		public WookmarkImage createFromParcel(Parcel in) {
			return new WookmarkImage(in);
		}

		public WookmarkImage[] newArray(int size) {
			return new WookmarkImage[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(height);
		dest.writeInt(width);
		dest.writeInt(id);
		dest.writeString(imageUriString);
		dest.writeString(imagePreviewUriString);
		dest.writeString(refererUriString);
		dest.writeString(title);
		dest.writeString(urlString);
	}
	
	private WookmarkImage(Parcel in) {
		this(in.readInt(),
				in.readInt(),
				in.readInt(),
				in.readString(),
				in.readString(),
				in.readString(),
				in.readString(),
				in.readString());
	}

}
