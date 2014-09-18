package Fragments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arianaantonio.astropix.ImageObject;
import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;

public class DetailFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "DetailFragment.TAG";
	
	public static DetailFragment newInstance(int sectionNumber) {
		
		
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailFragment() {
 
    }
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.fragment_detail, container, false);
		Bundle bundle = getArguments();
		
		@SuppressWarnings("unchecked") 
		HashMap<String, String> data = (HashMap<String, String>)bundle.getSerializable("clicked data");
		Log.i("Detail Fragment", "Passed data: " +data);
		//ArrayList<HashMap<String, String>> data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("clicked data");
		String images = data.get("url");
		String titles = data.get("title");
		String cameras = data.get("camera");
		String telescopes = data.get("telescope");
		String websites = data.get("website");
		String descriptions = data.get("description");
		String users = data.get("username");
		HashMap<String, String> displayText = new HashMap<String, String>();
		
		HashMap<String, HashMap> newText = new HashMap<String, HashMap>();
		newText.put("image", data);
		
		displayText.put("url", images);
		displayText.put("title", titles);
		displayText.put("camera", cameras);
		displayText.put("telescope", telescopes);
		displayText.put("website", websites);
		displayText.put("description", descriptions);
		displayText.put("username", users);
		
		Log.i("Detail Fragment", "Display text: " +displayText);
		/*
		for (int i = 0; i < data1.size(); i++) {
			images.add(data1.get(i).get("url"));
			titles.add(data1.get(i).get("title"));
			cameras.add(data1.get(i).get("camera"));
			telescopes.add(data1.get(i).get("telescope"));
			websites.add(data1.get(i).get("website"));
			descriptions.add(data1.get(i).get("description"));
			users.add(data1.get(i).get("username"));
			
		}*/
		Log.i("Detail Fragment", "Working2");
		ImageObject item = null;
		HashMap<String, Object> image = new HashMap<String, Object>();
		image.put("image", item);
		/*
		for (int i = 0;i <images.size(); i++) {
			item = new ImageObject(images.get(i), users.get(i), cameras.get(i), descriptions.get(i), telescopes.get(i), websites.get(i), titles.get(i));
			//imageItems.add(item);
		}*/
		ArrayList<HashMap<String, ?>> myData = new ArrayList<HashMap<String, ?>>();
		myData.add(newText);
		String[] stringa = null;
		item = new ImageObject(images, users, cameras, descriptions, telescopes, websites, titles);
		Log.i("Detail Fragment", "Working3");
		BufferedWriter writer = null;
		try {
			String filePath = getActivity().getFilesDir().getPath().toString() + "/FavoritesFile.txt";
			//File file = new File("/FavoritesFile.ser"); 
			File file = new File(filePath);
			//System.out.println(file.getCanonicalPath());
			//file.delete(); 
			writer = new BufferedWriter(new FileWriter(file, true));
			//HashMap<String, String> saveData = (HashMap<String, String>) data+ "\n";
			/*
			//FileOutputStream fileOut = new FileOutputStream("/FavoritesFile.ser", true);
			FileOutputStream fileOut = new FileOutputStream(file);
			FileInputStream fis = new FileInputStream(filePath);
			Log.i("test", "file length: " +file.length());
			Log.i("test", "file size avail: " +file.getUsableSpace());
			Log.i("test", "file size total: " +file.getTotalSpace());
			
			ObjectOutputStream out = null;
			int b = fis.read();  
			if (b == -1) {
				Log.i("test", "empty");
			} else {
				Log.i("test", "not empty");
			}
			if (file.length() == 0) { 
				out = new ObjectOutputStream(fileOut);
				Log.i("Detail Fragment", "Inside do not append");
				
			} else {
				out = new AppendingObjectOutputStream(fileOut);
				Log.i("Detail Fragment", "Inside Append");
			}
			out.writeObject(image);
	        out.close();
	        fileOut.close();*/
			String lineTitle = newText.get("image").toString()+ "\n*#$";
			writer.write(lineTitle);
			Toast.makeText(getActivity(), "Website saved as bookmark", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return view;
	}
	public class AppendingObjectOutputStream extends ObjectOutputStream {

		  public AppendingObjectOutputStream(OutputStream out) throws IOException {
		    super(out);
		  }

		  @Override
		  protected void writeStreamHeader() throws IOException {
		    // do not write a header, but reset:
		    // this line added after another question
		    // showed a problem with the original
		    reset();
		  }

		}
}
