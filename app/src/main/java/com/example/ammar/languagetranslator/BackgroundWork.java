package com.example.ammar.languagetranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

class BackgroundWork extends AsyncTask<String, Void, String >{
	
	public interface TaskListener {

		 public void onFinished(String result);
	}


private TaskListener taskListener;

public BackgroundWork(TaskListener listener){
	this.taskListener = listener;
}
	
	protected void onPostExecute(String  result) {
		
		Log.d("onpost", "Connecting to ");
		this.taskListener.onFinished(result);
		
	}


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		Log.e("background", "Connecting to ");
		
		String translated = null;
	    try {
	    	
	    	/*String query = URLEncoder.encode("hello ammar how are you. i think you are fine.", "UTF-8");
	    	String langpair = URLEncoder.encode("en|ur-PK", "UTF-8");*/
	    	/*String url = "http://api.mymemory.translated.net/get?q=hello world ammar&langpair="+langpair;*/
	    	/*String url = "http://api.mymemory.translated.net/get?q=" + query + "&langpair=" + langpair;*/
	    	
	    	HttpClient hc = new DefaultHttpClient();		         
		    HttpGet hg = new HttpGet(params[0]);
			HttpResponse hr = hc.execute(hg);
			if(hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {				
				JSONObject response = new JSONObject(EntityUtils.toString(hr.getEntity()));
				translated = response.getJSONObject("responseData").getString("translatedText");				
			}
		} catch (Exception e) {
			Log.e("Errorrrrr", e.toString());
		}	    
	    return translated;
		
	};
	
	}
