package com.example.saturnus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Using Android default icons instead
    public Integer[] mThumbIds = {
            android.R.drawable.ic_dialog_email,
            android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_dialog_map,
            android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_dialog_dialer,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_share,
            android.R.drawable.ic_menu_send,
            android.R.drawable.star_big_on,
            android.R.drawable.star_big_off,
            android.R.drawable.btn_star,
            android.R.drawable.btn_star_big_on,
            android.R.drawable.btn_star_big_off
    };

    // Constructor and other methods remain the same...
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}