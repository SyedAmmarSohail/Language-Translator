package com.example.ammar.languagetranslator;

import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class CustomAutoCompleteTextchangeListner implements TextWatcher {

	public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
	Context context;
	int index = 0;

	public CustomAutoCompleteTextchangeListner(Context context) {
		this.context = context;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence userInput, int start, int before,
			int count) {

		try {

			CountryListView mainActivity = ((CountryListView) context);

			// update the adapater
			mainActivity.mAdapter.notifyDataSetChanged();

			// get array size
			for (int i = 0; i < mainActivity.data.size(); i++) {
				if (mainActivity.data.get(i).getCountry().toString()
						.startsWith(userInput.toString())) {
					Log.e("eeeeee", mainActivity.data.get(i).getlanguage().toString());
					index++;
				}
			}
			// put array size
			Country[] country = new Country[index];
			index = 0;
			for (int i = 0; i < mainActivity.data.size(); i++) {
				if (mainActivity.data.get(i).getlanguage().toString()
						.startsWith(userInput.toString())) {
					country[index++] = mainActivity.data.get(i);
					Log.e("ttttt", mainActivity.data.get(index).getCountry().toString());
					Log.e("sssss", country[index] + "");
				}
			}
			index = 0;
			mainActivity.mAdapter = new CountryAdapter(context, country);

			mainActivity.countryLV.setAdapter(mainActivity.mAdapter);

			mainActivity.autosearch.setAdapter(mainActivity.mAdapter);

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
