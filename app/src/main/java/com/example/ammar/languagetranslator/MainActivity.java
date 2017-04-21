package com.example.ammar.languagetranslator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	LinearLayout firstll, secondll, thirdll, fourthll, homell, sharell,
			feedbackll, head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();

		firstll.setOnClickListener(this);
		secondll.setOnClickListener(this);
		thirdll.setOnClickListener(this);
		fourthll.setOnClickListener(this);
		
		sharell.setOnClickListener(this);
		feedbackll.setOnClickListener(this);
		head.setOnClickListener(this);

	}

	private void initialize() {
		// TODO Auto-generated method stub
		firstll = (LinearLayout) findViewById(R.id.firstll);
		secondll = (LinearLayout) findViewById(R.id.secondll);
		thirdll = (LinearLayout) findViewById(R.id.thirdll);
		fourthll = (LinearLayout) findViewById(R.id.fourthll);
		homell = (LinearLayout) findViewById(R.id.homell);
		sharell = (LinearLayout) findViewById(R.id.sharell);
		feedbackll = (LinearLayout) findViewById(R.id.feedbackll);
		head = (LinearLayout) findViewById(R.id.head);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.firstll:

			startActivity(new Intent(this, LanguageText.class));
			break;
		case R.id.secondll:

			startActivity(new Intent(this, LanguageVoice.class));
			break;
		case R.id.thirdll:
			startActivity(new Intent(this, PDFViewActivity_.class));
		//	startActivity(new Intent(this, LanguageAudio.class));

			break;
		
		case R.id.sharell:

			startActivity(new Intent(this, Share.class));
			break;
		case R.id.feedbackll:

			startActivity(new Intent(this, Feedback.class));
			break;
		case R.id.head:

			startActivity(new Intent(this, About.class));
			break;
		default:
			break;

		}
	}
}
