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
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.arianaantonio.astropix.CustomBaseAdapter;
import com.arianaantonio.astropix.ImageObject;
import com.arianaantonio.astropix.R;

public class SearchFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "SearchFragment.TAG";
	
	GridView gridView;
	List<ImageObject> imageItems;
	
	public static SearchFragment newInstance(int sectionNumber) {
		
		SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
   
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		//((MainActivity) activity).onSectionAttached(getArguments().getInt(
               // ARG_SECTION_NUMBER));
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		Bundle bundle = getArguments();
		ArrayList<String> images = new ArrayList<String>();
		
		if (bundle != null) {
			ArrayList<HashMap<String, String>> data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("passed data");
			Log.i("Searchview", "Data: " +data1);
			
			for (int i = 0; i < data1.size(); i++) {
				String url = data1.get(i).get("url");
				if (url.equals("null")) {
					Toast toast = Toast.makeText(getActivity(), "No images found. Please try your search again.", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					images.add(data1.get(i).get("url"));
				}
				//images.add(data1.get(i).get("url"));
				//Log.i("Gridview", "URL: " +text);
			}
			 
			gridView = (GridView) view.findViewById(R.id.grid);
			
			imageItems = new ArrayList<ImageObject>();
			imageItems.clear();
			
			
			for (int i = 0;i <images.size(); i++) {
				ImageObject item = new ImageObject(images.get(i), "user", "camera", "description", "telescope", "website", "title");
				imageItems.add(item);
				
			}
			
			 
			CustomBaseAdapter adapter = new CustomBaseAdapter(getActivity(), imageItems);
			adapter.notifyDataSetChanged();
			gridView.setAdapter(adapter);
		}
		return view;
	}

}
