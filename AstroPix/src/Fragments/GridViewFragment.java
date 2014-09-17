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

import com.arianaantonio.astropix.CustomBaseAdapter;
import com.arianaantonio.astropix.ImageObject;
import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;

public class GridViewFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "GridviewFragment.TAG";
	
	
	GridView gridView;
	List<ImageObject> imageItems;
	
	
	public static GridViewFragment newInstance(int sectionNumber) {
		
		GridViewFragment fragment = new GridViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
		
		View view = inflater.inflate(R.layout.fragment_gridview, container, false);
		Bundle bundle = getArguments();
		ArrayList<String> images = new ArrayList<String>();
		
				
		ArrayList<HashMap<String, String>> data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("passed data");
		
		for (int i = 0; i < data1.size(); i++) {
			images.add(data1.get(i).get("url"));
			//Log.i("Gridview", "URL: " +text);
		}
		//Log.i("Gridview", "URL: " +text);
		//Log.i("Gridview", "Data: " +data1);
		 
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
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
	}
	public void getNavItemClicked(String string) {
		if (string != null) {
			Log.i("GridViewFragment", "Item clicked: " +string);
			if (string.equals("Recent")) {
				pullMostRecent();
			} else if (string.equals("Favorites")) {
				pullFavorites();
			} else {
				pullSearch();
			}
		}
		
	}
	public void pullFavorites() {
		Log.i("GridViewFragment", "Pulling favorites...");
	}
	public void pullSearch() {
		Log.i("GridViewFragment", "Pulling Search...");
	}
	public void pullMostRecent() {
		Log.i("GridViewFragment", "Pulling Most Recent...");
	}
	public void getData(Bundle bundle) {
		Log.i("GridView", "Second bundle: " +bundle);
	}
}
