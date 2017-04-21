package com.example.ammar.languagetranslator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class LanguageAudio extends Activity implements OnClickListener{

	Button audioButton;
	ImageView imageAudio;
	ProgressBar horizontal, circle;
	RelativeLayout audioRlFirst;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.language_audio);
		
		initialize();
		
		audioButton.setOnClickListener(this);
	}

	private void initialize() {
		// TODO Auto-generated method stub
		audioButton = (Button) findViewById(R.id.audioButton);
		imageAudio = (ImageView) findViewById(R.id.imageAudio);
		horizontal = (ProgressBar) findViewById(R.id.audioProgresshorizontal);
		circle = (ProgressBar) findViewById(R.id.audioProgresscircle);
		audioRlFirst = (RelativeLayout) findViewById(R.id.audioRlFirst);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.audioButton:
			audioRlFirst.setVisibility(View.INVISIBLE);
			circle.setVisibility(View.VISIBLE);
			break;

		
		}
	}
	
}
