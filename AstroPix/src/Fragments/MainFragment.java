package Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.arianaantonio.astropix.ImageObject;
import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;
import com.loopj.android.image.SmartImageView;

public class MainFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "MainFragment.TAG";
	public SmartImageView imageView;
	public SmartImageView imageView2;
	public String test;
	ArrayList<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();
	
	List<ImageObject> imageItems;
	private ParentListener listener;
	public interface ParentListener {
		void passBackClickedItemMain(String item);
		
	}
	
	public static MainFragment newInstance(int sectionNumber) {
		MainFragment frag = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        frag.setArguments(args);
		return frag;
	}
	

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
		try {
			listener = (ParentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "class does not implement fragment interface");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		imageView = (SmartImageView) view.findViewById(R.id.image_of_the_day);
		imageView2 = (SmartImageView) view.findViewById(R.id.runner_up); 
		
		Bundle bundle = getArguments();
		ArrayList<String> images = new ArrayList<String>();
		
		if (bundle !=null) {
		    data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("passed data");
		    Log.i("Main Fragment", "Data: " +data1);
			
			for (int i = 0; i < data1.size(); i++) {
				
				images.add(data1.get(i).get("url"));
			}
		
			imageView.setImageUrl(data1.get(0).get("url"));
			imageView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				Log.i("Main Fragment", "Image clicked" +data1.get(0).get("url"));
				listener.passBackClickedItemMain(data1.get(0).get("url"));
					
				}
				
			});
			imageView2.setImageUrl(data1.get(0).get("runnerUp"));
			imageView2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
				Log.i("Main Fragment", "Image clicked" +data1.get(0).get("runnerUp"));
				listener.passBackClickedItemMain(data1.get(0).get("runnerUp"));
					
				}
				
			});
			
		}
		
		
		return view;
	} 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	}
}
