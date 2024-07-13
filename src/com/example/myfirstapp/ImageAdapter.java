package com.example.myfirstapp;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
	
	private Context CTX;
	
	public ImageAdapter(Context CTX) {
		
		this.CTX = CTX;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return image_id.length;
		return 81;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView img;
		int mineSize = (int)(MainActivity.scrWidth)/9;
		if(convertView == null)
		{
			img = new ImageView(CTX);
			img.setLayoutParams(new GridView.LayoutParams(mineSize,mineSize));
			//img.setScaleType(ImageView.ScaleType.FIT_XY);
			//img.setPadding(1, 1, 1, 1);
		}
		else
		{
			img = (ImageView) convertView;
		}
		
		img.setImageResource(R.drawable.mine_button);		
		return img;
	}

}
