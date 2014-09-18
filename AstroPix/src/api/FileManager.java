//Ariana Antonio
//APD2

package api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileManager {

	private static FileManager managerInstance;

	private FileManager() {
		
	}

	public static FileManager getInstance() {
		if (managerInstance == null) {
			managerInstance = new FileManager();
		}
		return managerInstance;
	}

	public Boolean writeStringFile(Context context, String filename, String content) {
		Boolean result = false;
		FileOutputStream fos = null;
		
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			Log.i("Write to file", "working");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	public String readStringFile(Context context, String filename) {
		String content = "";
		
		FileInputStream fis = null;
		
		try {
			
			fis = context.openFileInput(filename);
		
			BufferedInputStream bis = new BufferedInputStream(fis);
			File file = context.getFileStreamPath(filename);
			int filesize = (int) file.length();
			byte[] contentBytes = new byte[50000];
			//byte[] contentBytes = new byte[filesize];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while ((bytesRead = bis.read(contentBytes)) != -1) {
				content = new String(contentBytes, 0, bytesRead);
				contentBuffer.append(content);
			}
		} catch (IOException e) {
			Log.e("Error in read file", e.getMessage().toString());
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Log.e("Error closing file", e.getMessage().toString());
				e.printStackTrace();
			}
			
		}
		return content; 
	}
}
