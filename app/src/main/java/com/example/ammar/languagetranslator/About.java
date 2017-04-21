package com.example.ammar.languagetranslator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		TextView AboutHead;
		ImageView back;

		String fontPath = "fonts/Roboto-Thin.ttf";

		AboutHead = (TextView) findViewById(R.id.textView1);
		back = (ImageView) findViewById(R.id.backAbout);

		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

		AboutHead.setTypeface(tf);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				
			}
		});
	}

}
