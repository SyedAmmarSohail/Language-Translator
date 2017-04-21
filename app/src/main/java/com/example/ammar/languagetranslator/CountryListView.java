package com.example.ammar.languagetranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CountryListView extends Activity {

	ListView countryLV;
	int itemPosition = 0;
	static List<Country> data;
	public CountryAdapter mAdapter;
	protected SparseArray<ArrayList<Country>> mCountriesMap = new SparseArray<ArrayList<Country>>();
	CustomAutoCompleteView autosearch;
	View backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country_list_view);

		countryLV = (ListView) findViewById(R.id.countryLV);
		autosearch = (CustomAutoCompleteView) findViewById(R.id.searchET);
		mAdapter = new CountryAdapter(this);

		data = new ArrayList<Country>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(this
					.getApplicationContext().getAssets().open("countries_identifiers.dat"),
					"UTF-8"));

			// do reading, usually loop until end of file reading
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				// process line
				Country c = new Country(this, line, i);
				// data.add(c);
				// ArrayList<Country> list =
				// mCountriesMap.get(c.getCountryCode());
				// if (list == null) {
				// list = new ArrayList<Country>();
				// mCountriesMap.put(c.getCountryCode(), list);
				// }
				// list.add(c);
				i++;
				data.add(c);
				mAdapter.add(c);
			}
		} catch (IOException e) {
			// log the exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// log the exception
				}
			}
		}
		// mAdapter.addAll(data);
		autosearch.setAdapter(mAdapter);
		autosearch.setThreshold(1);
		countryLV.setAdapter(mAdapter);
		autosearch
				.addTextChangedListener(new CustomAutoCompleteTextchangeListner(
						this));

		// String countryRegion = PhoneUtils.getCountryRegionFromPhone(this);
		// int code = mPhoneNumberUtil.getCountryCodeForRegion(countryRegion);

		countryLV.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState != 0) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(autosearch.getWindowToken(), 0);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		countryLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Country c = (Country) parent.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.putExtra("Country", c.getCountry());
				intent.putExtra("cIdentifier", c.getcIdentifier());
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		/*getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);*/
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, LanguageText.class));
			finish();
		}
		return true;
	}

}
