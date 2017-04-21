package com.example.ammar.languagetranslator;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Feedback extends FragmentActivity implements OnClickListener {

	public static View view;
	final int SELECT_IMAGE = 100;
	int CAMERA_CAPTURE = 200;
	String imagePath;
	ImageView image, back;
	EditText issueTitle, issueDetail, emailFrom;
	Button send;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.feedback);

		image = (ImageView) findViewById(R.id.feedbackImage);
		issueTitle = (EditText) findViewById(R.id.issueTitle);
		issueDetail = (EditText) findViewById(R.id.issueDetail);
		/* emailFrom = (EditText) view.findViewById(R.id.feedbackEmail); */
		send = (Button) findViewById(R.id.feedbackSend);
		back = (ImageView) findViewById(R.id.backFeedback);
		send.setOnClickListener(this);
		image.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.feedbackImage:
			Intent photoPickerIntent = new Intent();
			photoPickerIntent.setType("image/*");
			photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);

			startActivityForResult(
					Intent.createChooser(photoPickerIntent, "Select Source:"),
					SELECT_IMAGE);
			break;

		case R.id.feedbackSend:

			if (issueTitle.getText().toString().compareTo("") != 0
					&& issueDetail.getText().toString().compareTo("") != 0) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "ammar_sohail13@hotmail.com" });
				i.putExtra(Intent.EXTRA_SUBJECT, issueTitle.getText()
						.toString());
				i.putExtra(Intent.EXTRA_TEXT, issueDetail.getText().toString());
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(this,
							"There are no email clients installed.",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "Please fill required fields",
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.backFeedback:
			startActivity(new Intent(this, MainActivity.class));
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		if (resultCode == this.RESULT_OK) {
			if (requestCode == SELECT_IMAGE) {
				Uri selectedImage = imageReturnedIntent.getData();
				InputStream imageStream = null;
				try {
					imageStream = this.getContentResolver().openInputStream(
							selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap yourSelectedImage = BitmapFactory
						.decodeStream(imageStream);
				image.setImageBitmap(yourSelectedImage);
			}

		}
	}

}
