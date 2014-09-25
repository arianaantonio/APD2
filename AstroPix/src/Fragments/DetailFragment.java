package Fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.arianaantonio.astropix.R;
import com.loopj.android.image.SmartImageView;

public class DetailFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "DetailFragment.TAG";
	private String astroBinUrl;
	private ArrayList<HashMap<String, ?>> myData = new ArrayList<HashMap<String, ?>>();
	
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
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.fragment_detail, container, false);
		
		
		TextView titleView = (TextView) view.findViewById(R.id.title);
		TextView usernameView = (TextView) view.findViewById(R.id.username);
		TextView telescopeView = (TextView) view.findViewById(R.id.telescope);
		TextView cameraView = (TextView) view.findViewById(R.id.camera);
		TextView descriptionView = (TextView) view.findViewById(R.id.description);
		SmartImageView imageView = (SmartImageView) view.findViewById(R.id.detailImage);
		
		Bundle bundle = getArguments();
		//Log.i("Detail Fragment", "Bundle: " +bundle);
		HashMap<String, String> data = (HashMap<String, String>)bundle.getSerializable("clicked data");
		//Log.i("Detail Fragment", "Passed data: " +data);
		
		String images = data.get("url");
		String titles = data.get("title");
		String cameras = data.get("camera");
		String telescopes = data.get("telescope");
		String websites = data.get("website");
		String descriptions = data.get("description");
		String users = data.get("username");
		astroBinUrl = "http://www.astrobin.com/" +websites;
		
		String displayCamera = cameras.replace("\"", "").replace("[", "").replace("]", "");
		String displayTelescope = telescopes.replace("\"", "").replace("[", "").replace("]", "");
		
		titleView.setText(titles);
		usernameView.setText(users);
		telescopeView.setText(displayTelescope);
		cameraView.setText(displayCamera);
		descriptionView.setText(descriptions);
		descriptionView.setMovementMethod(new ScrollingMovementMethod());  
		imageView.setImageUrl(images);
		
		HashMap<String, String> displayText = new HashMap<String, String>();

		displayText.put("url", images);
		displayText.put("title", titles);
		displayText.put("camera", cameras);
		displayText.put("telescope", telescopes);
		displayText.put("website", websites);
		displayText.put("description", descriptions);
		displayText.put("username", users);
		
		Log.i("Detail Fragment", "Display text: " +displayText);
		
		myData.add(displayText);
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.detail_action_bar, menu);

		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		String string = "Check out this image: " +astroBinUrl;
		shareIntent.putExtra(Intent.EXTRA_TEXT, string);
		MenuItem menuItem = menu.findItem(R.id.share);
		ShareActionProvider mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
		mShareActionProvider.setShareIntent(shareIntent);

		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("Detail Fragment", "menu item clicked: " +item.getItemId());
		
		switch (item.getItemId()) {
		case R.id.favorites:
			ArrayList<HashMap<String, String>> appendData = new ArrayList<HashMap<String, String>>();
			String filePath = getActivity().getFilesDir().getPath().toString() + "/FavoritesFile.ser";
			File file = new File(filePath); 

			FileInputStream fileIn;
			ObjectInputStream in;
			
			if (file.exists()) {
				try {
					fileIn = new FileInputStream(filePath);
					in = new ObjectInputStream(fileIn);
					appendData = (ArrayList<HashMap<String,String>>) in.readObject();
					in.close();
					fileIn.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			//Log.i("Detail Fragment", "Read data:" +appendData);
			
			for (int i = 0; i < appendData.size(); i++) {
				myData.add(appendData.get(i));
			}
			//Log.i("Detail Fragment", "New Data: " +myData);

			try { 
				
				FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(myData);
		        out.close();
		        fileOut.close();
				Toast.makeText(getActivity(), "Image saved as favorite", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.share:
			break;
		case R.id.info:
			Uri website = Uri.parse(astroBinUrl);
			Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
			startActivity(websiteIntent);
			break;
		
		}
		
		return true;
	}
}
