package nz.net.speakman.wookmark.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import nz.net.speakman.wookmark.images.WookmarkImage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class WookmarkDownloader {
	private HttpClient client;
	
	public WookmarkDownloader(HttpClient client) {
		this.client = client;
	}
	
	public ArrayList<WookmarkImage> getImages(String url) {
		ArrayList<WookmarkImage> images = new ArrayList<WookmarkImage>();
		InputStream source = retrieveStream(url);
		if (null == source)
			return images;
		String json = getJSONString(source); 
		JSONObject o;
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONArray array = (JSONArray) tokener.nextValue();
			for(int i = 0; i < array.length(); i++) {
				images.add(WookmarkImage.generateFromJson(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e("WookmarkDownloader", "Error parsing JSON", e);
		}
		return images;
	}
	
	private String getJSONString(InputStream source) {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(source));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null)
				sb.append(line);

			reader.close();
			source.close();
		} catch (IOException e) {
			Log.e("WookmarkDownloader", "Error reading JSON string", e);
		}

		return sb.toString();
	}
	
	
	private InputStream retrieveStream(String url) {

		HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("WookmarkDownloader", "Error "
						+ statusCode + " for URL " + url);
				return null;
			}

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();

		} catch (IOException e) {
			getRequest.abort();
			Log.w("WookmarkDownloader", "Error for URL " + url + ". Returning null.",
					e);
		}
		return null;
	}
}
