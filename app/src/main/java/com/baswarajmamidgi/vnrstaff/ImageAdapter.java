package com.baswarajmamidgi.vnrstaff;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by baswarajmamidgi on 28/02/17.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> mThumbIds;

    public ImageAdapter(Context c,ArrayList<String> imageslist) {
        mContext = c;
        mThumbIds=imageslist;

    }

    public int getCount() {
        return mThumbIds.size();
    }

    @Override
    public String getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(530, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position);
        Glide.with(mContext).load(url)
                .placeholder(R.drawable.navicon)
                .fitCenter()
                .crossFade()
                .into(imageView);
        return imageView;
    }
}


