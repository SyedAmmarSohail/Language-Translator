/*
 * Copyright (c) 2014 Amberfog.
 */

package com.example.ammar.languagetranslator;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryAdapter extends ArrayAdapter<Country> {

	private LayoutInflater mLayoutInflater;
	Country[] data;
	Context con;
	boolean checkingcontructor;
	public CountryAdapter(Context c) 
	{
		super(c, 0);
		con = c;
		mLayoutInflater = LayoutInflater.from(con);
		checkingcontructor=true;
	}

	public CountryAdapter(Context mContext,Country[] data) 
	{
		super(mContext, R.layout.item_country, data);
		this.con = mContext;
		this.data = data;
		mLayoutInflater = LayoutInflater.from(mContext);
		checkingcontructor=false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		final ViewHolder holder;
		Country country;
		if(!checkingcontructor)
		  country = data[position];
		else
		  country = getItem(position);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_country, null);
			holder = new ViewHolder();
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.countrySelectIV);
			holder.mNameView = (TextView) convertView
					.findViewById(R.id.countryNameTV);
			holder.mCodeView = (TextView) convertView
					.findViewById(R.id.countryCodeTV);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (country != null) {
			holder.mNameView.setText(country.getlanguage());
			holder.mCodeView.setText(country.getCountry());
		}
		return convertView;
	}

	static class ViewHolder {
		public ImageView mImageView;
		public TextView mNameView;
		public TextView mCodeView;
	}
}
