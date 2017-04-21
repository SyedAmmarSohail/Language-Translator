package com.example.ammar.languagetranslator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LanguageVoice extends Activity implements OnClickListener,
		RecognitionListener, TextToSpeech.OnInitListener {

	ImageView back, voiceImage;
	ListView returnedText;
	TextView returnedError;

	private SpeechRecognizer speech = null;
	private static TextToSpeech mTts;
	static Paint ptAlphaColor;
	private Intent recognizerIntent;
	private String LOG_TAG = "LanguageVoice";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.language_voice);

		initialize();

		mTts = new TextToSpeech(this, this);

		speech = SpeechRecognizer.createSpeechRecognizer(this);
		speech.setRecognitionListener(this);
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				this.getPackageName());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

		voiceImage.setImageBitmap(highlightImage(BitmapFactory.decodeResource(
				getResources(), R.drawable.audio_voice)));

		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		int amStreamMusicMaxVol = audioManager
				.getStreamMaxVolume(audioManager.STREAM_MUSIC);

		audioManager.setStreamVolume(audioManager.STREAM_MUSIC,
				amStreamMusicMaxVol, 0);

		audioManager.setSpeakerphoneOn(true);

		voiceImage.setOnClickListener(this);
		back.setOnClickListener(this);

	}

	private void initialize() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.backVoice);
		voiceImage = (ImageView) findViewById(R.id.voiceImage);
		returnedText = (ListView) findViewById(R.id.list);
		returnedError = (TextView) findViewById(R.id.returnedError);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.voiceImage:
			voiceImage
					.setImageBitmap(highlightImage(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.audio_voice_red)));
			speech.startListening(recognizerIntent);
			break;
		case R.id.backVoice:
			finish();
			break;

		}
	}

	public Bitmap highlightImage(Bitmap src) {
		// create new bitmap, which will be painted and becomes result image
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth() + 10,
				src.getHeight() + 10, Bitmap.Config.ARGB_8888);
		// setup canvas for painting
		Canvas canvas = new Canvas(bmOut);
		// setup default color
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		// create a blur paint for capturing alpha
		Paint ptBlur = new Paint();
		ptBlur.setMaskFilter(new BlurMaskFilter(5, Blur.NORMAL));
		int[] offsetXY = new int[2];
		// capture alpha into a bitmap
		Bitmap bmAlpha = src.extractAlpha(ptBlur, offsetXY);
		// create a color paint
		ptAlphaColor = new Paint();
		/* ptAlphaColor.setColor(0xFFFFFFFF); */
		ptAlphaColor.setShadowLayer(10.0f, 0.0f, 0.0f, 0xFFdc4e80);
		// paint color for captured alpha region (bitmap)
		canvas.drawBitmap(bmAlpha, 0.0f, 0.0f, ptAlphaColor);
		// free memory
		bmAlpha.recycle();

		// paint the image source
		canvas.drawBitmap(src, 0, 0, null);

		// return out final image
		return bmOut;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (speech != null) {
			speech.destroy();
			Log.i(LOG_TAG, "destroy");
		}
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}

	}

	@Override
	public void onBeginningOfSpeech() {
		Log.i(LOG_TAG, "onBeginningOfSpeech");

	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		Log.i(LOG_TAG, "onBufferReceived: " + buffer);
	}

	@Override
	public void onEndOfSpeech() {
		Log.i(LOG_TAG, "onEndOfSpeech");

	}

	@Override
	public void onError(int errorCode) {
		String errorMessage = getErrorText(errorCode);
		Log.d(LOG_TAG, "FAILED " + errorMessage);
		returnedError.setVisibility(View.VISIBLE);
		returnedText.setVisibility(View.INVISIBLE);
		returnedError.setText(errorMessage);

		voiceImage.setImageBitmap(highlightImage(BitmapFactory.decodeResource(
				getResources(), R.drawable.audio_voice)));
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
		Log.i(LOG_TAG, "onEvent");
	}

	@Override
	public void onPartialResults(Bundle arg0) {
		Log.i(LOG_TAG, "onPartialResults");
	}

	@Override
	public void onReadyForSpeech(Bundle arg0) {
		Log.i(LOG_TAG, "onReadyForSpeech");
	}

	@Override
	public void onResults(Bundle results) {
		Log.i(LOG_TAG, "onResults");
		voiceImage.setImageBitmap(highlightImage(BitmapFactory.decodeResource(
				getResources(), R.drawable.audio_voice)));

		returnedText.setVisibility(View.VISIBLE);
		returnedError.setVisibility(View.INVISIBLE);

		ArrayList<String> matches = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		String text = "";
		for (String result : matches)
			text += result + "\n";

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, matches);
		returnedText.setAdapter(adapter);
		returnedText.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;

				// ListView Clicked item value
				String itemValue = (String) returnedText
						.getItemAtPosition(position);

				// Show Alert
				Toast.makeText(
						getApplicationContext(),
						"Position :" + itemPosition + "  ListItem : "
								+ itemValue, Toast.LENGTH_LONG).show();
					/////////////////////////////////////////////////////
				
				String query, url = null, langpair;
				
				try{
					langpair = URLEncoder.encode("en|ur-pk",
							"UTF-8");
				query = URLEncoder.encode(itemValue,
						"UTF-8");
				url = "http://api.mymemory.translated.net/get?q=" + query
						+ "&langpair=" + langpair;

				Log.e("URLLanguage", url.toString() + "");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "Error in URL", Toast.LENGTH_SHORT)
						.show();
			}

			if (isOnline() == true) {

				

				BackgroundWork.TaskListener listener = new BackgroundWork.TaskListener() {

					@Override
					public void onFinished(String result) {

						

						String translated = result;

						/* String translated = translate(text); */
						Log.e("agia 3", "yes");
						if (translated != null) {
							Log.e("agia 4", "yes");
							Log.e("Language", translated+ "");

							
							mTts.setLanguage(new Locale("urd"));
							mTts.speak(translated, TextToSpeech.QUEUE_FLUSH, null);
							Toast.makeText(getApplicationContext(),
									translated, Toast.LENGTH_LONG).show();
						}
					}
				};
				BackgroundWork back = new BackgroundWork(listener);

				back.execute(url);
			} else {
				mTts.speak("Please Connect to the Inetrnet", TextToSpeech.QUEUE_FLUSH, null);
				Toast.makeText(getApplicationContext(), "Please Connect to the Inetrnet",
						Toast.LENGTH_SHORT).show();
			}
				
				//////////////////////////////////////////////////
				/*mTts.setLanguage(new Locale("ur"));
				mTts.speak("yes", TextToSpeech.QUEUE_FLUSH, null);*/

			}

		});

		/* returnedText.setText(text); */
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);

		ptAlphaColor.setShadowLayer((float) rmsdB, (float) rmsdB,
				(float) rmsdB, 0xFFdc4e80);
		/* imageView.setAlpha((float)rmsdB); */
		voiceImage.setImageAlpha((int) rmsdB + 1);
		Log.e("alpha value", rmsdB + "");
	}

	public static String getErrorText(int errorCode) {
		String message;
		switch (errorCode) {
		case SpeechRecognizer.ERROR_AUDIO:
			message = "Audio recording error";
			mTts.speak("Audio recording error", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_CLIENT:
			message = "Client side error";
			mTts.speak("Client side error", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
			message = "Insufficient permissions";
			mTts.speak("Insufficient permissions", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_NETWORK:
			message = "Network error";
			mTts.speak("Network error", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
			message = "Network timeout";
			mTts.speak("Network timeout", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_NO_MATCH:
			message = "No match";
			mTts.speak("Please say clearly", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
			message = "RecognitionService busy";
			mTts.speak("RecognitionService busy", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_SERVER:
			message = "error from server";
			mTts.speak("Please connect to the internet", TextToSpeech.QUEUE_FLUSH, null);
			break;
		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
			message = "No speech input";
			mTts.speak("No speech input", TextToSpeech.QUEUE_FLUSH, null);
			break;
		default:
			message = "Didn't understand, please try again.";
			mTts.speak("Didn't understand, please try again.", TextToSpeech.QUEUE_FLUSH, null);
			break;
		}
		return message;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			int result = mTts.setLanguage(Locale.ENGLISH);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TAG",
						"Language data is missing or the language is not supported.");
			} else {
				Log.e("TAG", "Ready to use Text to Speech service");
				mTts.speak("Ready to use", TextToSpeech.QUEUE_FLUSH, null);
			}
		} else {
			Log.e("TAG", "Could not initialize TextToSpeech.");
		}
	}
	
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

}
