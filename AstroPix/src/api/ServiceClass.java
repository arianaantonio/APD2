//Ariana Antonio
//APD2
package api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ServiceClass extends IntentService {

	static String TAG = "IMAGE INTENT SERVICE";
	public static String responseStr;
	public static String _apiURL;
	public static final String MESSENGER_KEY = "messenger";
	public static final String MESSENGER_VIEW_KEY = "view";
	Context context = this;
	
	public ServiceClass() {
		
	super("API Service");
		
	}

	@Override
	//handle the service intent to get the data from the API and pass it to MainActivity
	protected void onHandleIntent(Intent intent) {
		
	String filename = "ImageFile.txt";
	
	Bundle extra = intent.getExtras();
	Messenger messenger = (Messenger) extra.get(MESSENGER_KEY);
	Message message = Message.obtain();
	message.arg1 = Activity.RESULT_OK;
	message.obj = filename;
	Log.i("Service Class", filename);
	String viewMessage = (String) extra.get(MESSENGER_VIEW_KEY);
	Log.i("Service Class", "Message: " +viewMessage);
	if (viewMessage.equals("imageOfTheDay")) {
		_apiURL = "http://www.astrobin.com/api/v1/imageoftheday/?limit=1&api_key=e5ca219df4572fd4f187f3e6c4192e24af7e78f8&api_secret=5d4bf7b7097eed09a878af19a475fb879a36b916&format=json";
	} else if (viewMessage.equals("search")) {
		_apiURL = "http://www.astrobin.com/api/v1/image/?title__icontains=spiral&api_key=e5ca219df4572fd4f187f3e6c4192e24af7e78f8&api_secret=5d4bf7b7097eed09a878af19a475fb879a36b916&format=json";
	} else {
		_apiURL = "http://www.astrobin.com/api/v1/imageoftheday/?limit=1&api_key=e5ca219df4572fd4f187f3e6c4192e24af7e78f8&api_secret=5d4bf7b7097eed09a878af19a475fb879a36b916&format=json"; 
	}
	Log.i("Service Class", "API URL: " +_apiURL);
	String content = getData(_apiURL);
	Log.i("Service Class", content);
	
	FileManager fileManager = FileManager.getInstance();
	fileManager.writeStringFile(context, filename, content);
	
	
	try {
		messenger.send(message);
	} catch (RemoteException e) {
		Log.e("onHandleIntent error", e.getMessage().toString());
		e.printStackTrace();
	}
	
}
	//grab data from API URL and pass it into getResponse to handle the connection
	public static String getData(String URL) {
		responseStr = "";
		try {
			//pass api URL into getResponse function
			URL url = new URL(_apiURL);
			responseStr = getResponse(url);
		} catch (MalformedURLException e) {
			responseStr = "There was an error getting data in getData";
			Log.e(TAG, "Error in getData", e);
		}
		Log.v(responseStr, "Response");
		return responseStr;
	}
	//connect to API as passed from getData() and grab the data, then return it back to getData()
	public static String getResponse(URL url) {
		String response = "";
		try {
			
			//open connection to URL
			URLConnection connect = url.openConnection();
			BufferedInputStream bufferStream = new BufferedInputStream(connect.getInputStream());
			byte[] byteContext = new byte[1024];
			int readByte = 0;
			StringBuffer responseBuffer = new StringBuffer();
			while ((readByte = bufferStream.read(byteContext)) != -1) {
				response = new String(byteContext, 0 ,readByte);
				responseBuffer.append(response);
			}
			response = responseBuffer.toString();
			//Log.i(TAG, response);
		} catch (IOException e) {
			response = "There was an error getting the data";
			Log.e(TAG, "Unable to get data", e);
		}
		
		return response;
	}

}
