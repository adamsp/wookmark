package nz.net.speakman.wookmark.images;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

public class WookmarkImage {
	
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
			i.height = json.getInt("height");
			i.width = json.getInt("width");
			i.id = json.getInt("id");
			i.imageUriString = json.getString("image");
			i.imagePreviewUriString = json.getString("preview");
			i.refererUriString = json.getString("referer");
			i.title = json.getString("title");
			i.urlString = json.getString("url");
		} catch (JSONException e) {
			Log.e("Wookmark", "Ohshit, something went wrong with the Json!", e);
		}
		return i;
	}
	
	/***
	 * Height of the image.
	 * @return The height of the image, in pixels.
	 */
	public int height() { return height ; }
	
	/***
	 * Width of the image.
	 * @return The width of the image, in pixels.
	 */
	public int width() { return width ; }
	
	/***
	 * Image ID on Wookmark.com.
	 * @return The unique image ID.
	 */
	public int id() { return id; }
	
	/***
	 * The image title on Wookmark.com.
	 * @return The image title.
	 */
	public String title() { return title; }
	
	private Uri _imageUri;
	/***
	 * The Uri of the image on Wookmark.com.
	 * @return The image Uri.
	 */
	public Uri imageUri() {
		if(_imageUri == null)
			_imageUri = Uri.parse(imageUriString);
		return _imageUri;
	}
	
	private Uri _imagePreviewUri;
	/***
	 * The Uri of a preview (thumbnail) of the image on Wookmark.com.
	 * @return The image preview Uri.
	 */
	public Uri imagePreviewUri() {
		if(_imagePreviewUri == null)
			_imagePreviewUri = Uri.parse(imagePreviewUriString);
		return _imagePreviewUri;
	}
	
	private Uri _refererUri;
	/***
	 * The Uri of the image referer.
	 * @return The referer Uri.
	 */
	public Uri refererUri() {
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
	public Uri url() {
		if(_url == null)
			_url = Uri.parse(urlString);
		return _url;
	}

}
