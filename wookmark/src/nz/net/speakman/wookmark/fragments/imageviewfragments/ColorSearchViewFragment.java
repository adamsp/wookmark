package nz.net.speakman.wookmark.fragments.imageviewfragments;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import net.margaritov.preference.colorpicker.ColorPickerView;
import nz.net.speakman.wookmark.R;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 5/02/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColorSearchViewFragment extends WookmarkBaseImageViewFragment {

    // TODO Since this has to match the color of the rect, this should be stored in a resource somewhere
    private int mColor = 0xff000000;

    @Override
    public void setUri() {
        mUri = getString(R.string.wookmark_endpoint_color) + Uri.encode(getHexColor());
    }

    private String getHexColor() {
        String color = Integer.toHexString(mColor);
        if(color.length() == 8)
            color = color.substring(2);
        else
            color = "000000";
        return color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // If mView exists & images are shown, then we're displaying results. All is handled already.
        if(mView != null && imagesShown())
            return mView;
        // Otherwise we re-create the color picker and show that.
        mView = inflater.inflate(R.layout.content_frame, null);
        showImagesView(false);
        return mView;
    }

    private void setListeners() {
        ColorPickerView cpv = (ColorPickerView)mView.findViewById(R.id.color_picker);
        cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                GradientDrawable d = (GradientDrawable) getSherlockActivity().findViewById(R.id.color_picker_chosen_color_view).getBackground();
                d.setColor(color);
                d.invalidateSelf();
                mColor = color;
            }
        });
        // Will auto-restore the selected color after rotation, as mColor is saved by the OS.
        cpv.setColor(mColor);
        ((GradientDrawable)mView.findViewById(R.id.color_picker_chosen_color_view).getBackground()).setColor(mColor);

        Button b = (Button)mView.findViewById(R.id.colorSearchButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUri();
                showImagesView(true);
                getNewImages();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0
                && imagesShown()) {
            showImagesView(false);
            return true;
        }
        return false;
    }

    private boolean imagesShown() {
        View v = mView.findViewById(R.id.antipodal_wall);
        return v != null;
    }

    private void showImagesView(boolean showImages) {
        ViewGroup vg = (ViewGroup)mView;
        vg.removeAllViews();
        if(showImages) {
            getSherlockActivity().getLayoutInflater().inflate(R.layout.basic_view, vg);
            setNumberOfColumnsOnView();
            clearAllImages();
        } else {
            getSherlockActivity().getLayoutInflater().inflate(R.layout.color_search_view, vg);
            setListeners();
        }
    }

    @Override
    public String getTitle(Context ctx) {
        return ctx.getString(R.string.fragment_title_color_search);
    }
}
