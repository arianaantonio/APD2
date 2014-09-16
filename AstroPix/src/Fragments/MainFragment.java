package Fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;
import com.loopj.android.image.SmartImageView;

public class MainFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "MainFragment.TAG";
	public SmartImageView imageView;
	public SmartImageView imageView2;
	public String test;
	static ArrayList<HashMap<String,String>> myData = new ArrayList<HashMap<String,String>>();
	
	public static MainFragment newInstance(int sectionNumber) {
		MainFragment frag = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        frag.setArguments(args);
		return frag;
	}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		imageView = (SmartImageView) view.findViewById(R.id.image_of_the_day);
		imageView2 = (SmartImageView) view.findViewById(R.id.runner_up); 
		//imageView.setImageUrl("http://www.astrobin.com/120122/0/rawthumb/duckduckgo/");
        //imageView2 = (SmartImageView) getView().findViewById(R.id.runner_up); 
	
		//Bundle data2 = getActivity().getIntent().getExtras();
		if (test !=null) {
			Log.i("Test", "Test: " +test);
		//Bundle data2 = getArguments();
		//getData(data2);
		}
		
		//if (data2 != null) {
			//Log.i("Main Fragment", "Bundle: " +data2); 
			//Log.i("Detail Activity", "working5");
			//getData(data2);
		//}
		test = new String("");
		
		return view;
	} //public void getData(ArrayList<HashMap<String, ?>> data)
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Bundle data2 = getArguments();
		
		if (test !=null) {
			Log.i("Test", "Test: " +test);
			//Bundle data2 = getArguments();
			//getData(data2);
			} 
			Log.i("Main Activity", "working in first saved");
			Log.i("Main Activity", "working after my data not null");
			//Bundle data2 = getActivity().getIntent().getExtras();
			//Log.i("Detail Activity", "Create bundle: " +data2);
			Log.i("Detail Activity", "working5");
			//displayDetails(userString, titleString, urlString, cameraString,hdString);
			//getData(data2);
		
		
	}


	public void getData(Bundle data) {
		//SmartImageView imageView = (SmartImageView) getView().findViewById(R.id.image_of_the_day);
       // SmartImageView imageView2 = (SmartImageView) getView().findViewById(R.id.runner_up);
		//imageView = (SmartImageView) getView().findViewById(R.id.image_of_the_day);
		if (data !=null) {
			Log.i("Main Fragment", "Before imageView");
				test =  new String("string");  
				Log.i("Test", "test: " +test);
				//test = "string";
				//ArrayList<HashMap<String, ?>> data = new ArrayList<HashMap<String, ?>>(); 
				//data = bundle.getStringArrayList("passed data");
				//ArrayList<HashMap<String, String>> = (ArrayList<HashMap<String, String>>) bundle.g
			ArrayList<HashMap<String, String>> myData = (ArrayList<HashMap<String, String>>) data.getSerializable("passed data");
				Log.i("MainFragment", "Passed Data: " +myData);
				//String url = data.get("url");
				String url = myData.get(0).get("url").toString();
				//String runnerUp = data.get(0).get("runnerUp").toString();
				Log.i("MainFragment", "Passed URL: " +url);
				if (imageView !=null) {
				imageView.setImageUrl(url);
				//imageView2.setImageUrl(runnerUp);
				onCreate(data);
			} else {
				Log.i("Main Fragment", "Null imageView");
			}
		}
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Bundle data2 = getArguments();
		//Log.i("Main Frag", "Bundle2: " +data2);
		//getData(data2);
	}

}
