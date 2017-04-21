package com.example.ammar.languagetranslator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LanguageText extends Activity implements OnClickListener {

	EditText wordTo, wordFrom;
	Button convert, fromCountry, toCountry;
	String fromLang = "en", toLan = "ur-PK";
	ProgressBar progressText;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.language_text);

		initialize();

		fromCountry.setOnClickListener(this);
		toCountry.setOnClickListener(this);
		convert.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	private void initialize() {
		// TODO Auto-generated method stub
		wordTo = (EditText) findViewById(R.id.wordTo);
		wordFrom = (EditText) findViewById(R.id.wordFrom);
		fromCountry = (Button) findViewById(R.id.fromCountry);
		toCountry = (Button) findViewById(R.id.toCountry);
		convert = (Button) findViewById(R.id.convertButton);
		back = (ImageView) findViewById(R.id.backText);
		progressText = (ProgressBar) findViewById(R.id.progressLanguageText);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fromCountry:
			startActivityForResult(new Intent(this, CountryListView.class), 100);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			break;
		case R.id.toCountry:
			startActivityForResult(new Intent(this, CountryListView.class), 200);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			break;
		case R.id.backText:
			Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
			finish();
			break;

		case R.id.convertButton:
			if (fromCountry.getText().toString().compareTo("") != 0
					&& toCountry.getText().toString().compareTo("") != 0
					&& wordFrom.getText().toString().compareTo("") != 0) {

				String langpair, query, url = null;
				try {

					query = URLEncoder.encode(wordFrom.getText().toString(),
							"UTF-8");
					langpair = URLEncoder.encode(fromLang + "|" + toLan,
							"UTF-8");
					url = "http://api.mymemory.translated.net/get?q=" + query
							+ "&langpair=" + langpair;

					Log.e("URLLanguage", url.toString() + "");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "Error in URL", Toast.LENGTH_SHORT)
							.show();
				}

				if (isOnline() == true) {

					convert.setVisibility(View.INVISIBLE);
					progressText.setVisibility(View.VISIBLE);

					BackgroundWork.TaskListener listener = new BackgroundWork.TaskListener() {

						@Override
						public void onFinished(String result) {

							convert.setVisibility(View.VISIBLE);
							progressText.setVisibility(View.INVISIBLE);

							String translated = result;

							/* String translated = translate(text); */
							Log.e("agia 3", "yes");
							if (translated != null) {
								Log.e("agia 4", "yes");
								Log.e("Language", translated+ "");

								wordTo.setText(translated);
								/*Toast.makeText(getApplicationContext(),
										translated, Toast.LENGTH_LONG).show();*/
							}
						}
					};
					BackgroundWork back = new BackgroundWork(listener);

					back.execute(url);
				} else {
					Toast.makeText(this, "Please Connect to the Inetrnet",
							Toast.LENGTH_SHORT).show();
				}

			}
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 100) {
				fromCountry.setText(data.getStringExtra("Country").toString());
				fromLang = data.getStringExtra("cIdentifier");

			}
			if (requestCode == 200) {
				toCountry.setText(data.getStringExtra("Country").toString());
				toLan = data.getStringExtra("cIdentifier");

			}
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

}
