package com.example.ammar.languagetranslator;

import java.util.List;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Share extends FragmentActivity implements OnClickListener {
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	public static View view;
	LinearLayout ratingLayout, facebookLayout, whatsappLayout, googleLayout;
	PackageManager pm;
	TextView AboutHead;
	ImageView back;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.share);
		pm = getPackageManager();
		
		ratingLayout = (LinearLayout) findViewById(R.id.ratinglinear);
		ratingLayout.setOnClickListener(this);
		facebookLayout = (LinearLayout) findViewById(R.id.facebookLinear);
		facebookLayout.setOnClickListener(this);
		whatsappLayout = (LinearLayout) findViewById(R.id.whatsappLinear);
		whatsappLayout.setOnClickListener(this);
		googleLayout = (LinearLayout) findViewById(R.id.googleLinear);
		googleLayout.setOnClickListener(this);
		back = (ImageView) findViewById(R.id.backShare);
		back.setOnClickListener(this);

		
		
	}
	
	

	private void shareAppLinkViaFacebook() {
		String urlToShare = "https://play.google.com/store/apps/details?id=demo.GPSTracker";

		try {
			Intent intent1 = new Intent();
			intent1.setClassName("com.facebook.katana",
					"com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
			intent1.setAction("android.intent.action.SEND");
			intent1.setType("text/plain");
			intent1.putExtra("android.intent.extra.TEXT", urlToShare);
			startActivity(intent1);
		} catch (Exception e) {
			// If we failed (not native FB app installed), try share through
			// SEND
			Intent intent = new Intent(Intent.ACTION_SEND);
			String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="
					+ urlToShare;
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
			startActivity(intent);
		}
	}

	private void shareAppLinkViaWhatsapp() {
		try {

			Intent waIntent = new Intent(Intent.ACTION_SEND);
			waIntent.setType("text/plain");
			String text = "Hey this is a omni locator application,you can track your loved ones using small GPS device and also using your mobile phone, A first Pakistan GPS tracker application";

			PackageInfo info = pm.getPackageInfo("com.whatsapp",
					PackageManager.GET_META_DATA);
			// Check if package exists or not. If not then code
			// in catch block will be called
			waIntent.setPackage("com.whatsapp");

			waIntent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(Intent.createChooser(waIntent, "Share with"));

		} catch (NameNotFoundException e) {
			Toast.makeText(getApplicationContext(),
					"WhatsApp not Installed", Toast.LENGTH_SHORT).show();
		}
	}

	private void shareAppLinkViaGooglePlus() {
		try {

			Intent waIntent = new Intent(Intent.ACTION_SEND);
			waIntent.setType("text/plain");
			String text = "Hey this is a omni locator application,you can track your loved ones using small GPS device and also using your mobile phone, A first Pakistan GPS tracker application";

			PackageInfo info = pm.getPackageInfo(
					"com.google.android.apps.plus",
					PackageManager.GET_META_DATA);
			// Check if package exists or not. If not then code
			// in catch block will be called
			waIntent.setPackage("com.google.android.apps.plus");

			waIntent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(Intent.createChooser(waIntent, "Share with"));

		} catch (NameNotFoundException e) {
			Toast.makeText(getApplicationContext(),
					"WhatsApp not Installed", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ratinglinear:

			break;

		case R.id.facebookLinear:
			shareAppLinkViaFacebook();

			break;
		case R.id.whatsappLinear:
			shareAppLinkViaWhatsapp();

			break;
		case R.id.googleLinear:
			shareAppLinkViaGooglePlus();
			break;
		case R.id.backShare:
			startActivity(new Intent(this, MainActivity.class));;
			break;

		}

	}
}
