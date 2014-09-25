package Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.arianaantonio.astropix.CustomBaseAdapter;
import com.arianaantonio.astropix.ImageObject;
import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;

import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoritesFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "FavoritesFragment.TAG";
	ArrayList<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();
	
	GridView gridView;
	List<ImageObject> imageItems;
	private ParentListener listener;
	public interface ParentListener {
		
		void passBackClickedItem(HashMap<String, ?> item);
		
	}
	
	
	public static FavoritesFragment newInstance(int sectionNumber) {
		
		FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
		
		View view = inflater.inflate(R.layout.fragment_favorites, container, false);
		Bundle bundle = getArguments();
		ArrayList<String> images = new ArrayList<String>();
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> cameras = new ArrayList<String>();
		ArrayList<String> telescopes = new ArrayList<String>();
		ArrayList<String> websites = new ArrayList<String>();
		ArrayList<String> descriptions = new ArrayList<String>();
		ArrayList<String> users = new ArrayList<String>();
		
		if (bundle !=null) {
		    data1 = (ArrayList<HashMap<String, String>>)bundle.getSerializable("passed data");
		    Log.i("Fav Frag", "Passed data: " +data1); 
			
			for (int i = 0; i < data1.size(); i++) {
				images.add(data1.get(i).get("url"));
				titles.add(data1.get(i).get("title"));
				cameras.add(data1.get(i).get("camera"));
				telescopes.add(data1.get(i).get("telescope"));
				websites.add(data1.get(i).get("website"));
				descriptions.add(data1.get(i).get("description"));
				users.add(data1.get(i).get("username"));
				
			}
			
			 gridView = (GridView) view.findViewById(R.id.grid);
			
			imageItems = new ArrayList<ImageObject>();
			imageItems.clear();
			
			for (int i = 0;i <images.size(); i++) {
				ImageObject item = new ImageObject(images.get(i), users.get(i), cameras.get(i), descriptions.get(i), telescopes.get(i), websites.get(i), titles.get(i));
				imageItems.add(item);
			} 
			 
			CustomBaseAdapter adapter = new CustomBaseAdapter(getActivity(), imageItems);
			adapter.notifyDataSetChanged();
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					HashMap<String, ?> selectedListItem = data1.get(position);
					Log.i("Search Fragment", "Clicked: " +selectedListItem);
					//listener.passBackClickedItem(selectedListItem);
					
					Bundle bundle = new Bundle();
					bundle.putSerializable("clicked data", selectedListItem);

					FragmentManager manager = getFragmentManager();
					DetailFragment fragment = new DetailFragment();
					fragment.setArguments(bundle);
					manager.beginTransaction().replace(R.id.container, fragment).commit(); 
					
				}
				
			});
		}
		return view;
	}

}
