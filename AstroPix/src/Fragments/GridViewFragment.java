package Fragments;

import java.util.ArrayList;
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
import com.loopj.android.image.SmartImageView;

public class GridViewFragment extends Fragment {

	public static SmartImageView imageView;
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "GridviewFragment.TAG";
	public static final String[] images = new String[] {
		"http://cdn.astrobin.com/images/thumbs/420012d7840e16cc292fc8101b3a9aa2.250x200_q80_crop-smart.jpg",
		"http://cdn.astrobin.com/images/thumbs/206d85ef5d583561a28674d553a0ddfb.250x200_q80_crop-smart.jpg",
		"http://cdn.astrobin.com/images/thumbs/5cb72778586e8859f655b0406cbb6ab5.250x200_q80_crop-smart.jpg",
		"http://cdn.astrobin.com/images/thumbs/15a82925c23d123d617fb81c5604d260.250x200_q80_crop-smart.jpg",
		"http://cdn.astrobin.com/images/thumbs/2ff676a933daccad01d9de393ae4b221.250x200_q80_crop-smart.jpg",
		"http://cdn.astrobin.com/images/thumbs/4fa45eb494d90bd8db89e73d2133230e.250x200_q80_crop-smart.jpg"};
	
	GridView gridView;
	List<ImageObject> imageItems;
	
	public static GridViewFragment newInstance() {
		//int sectionNumber
		
		GridViewFragment fragment = new GridViewFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public GridViewFragment() {
 
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
		Log.i("GridView", "Bundle: " +bundle);
		imageView = (SmartImageView) view.findViewById(R.id.smartimage);
		
		
		
		imageItems = new ArrayList<ImageObject>();
		
		for (int i = 0;i <images.length; i++) {
			ImageObject item = new ImageObject(images[i], "user", "camera", "description", "telescope", "website", "title");
			imageItems.add(item);
			
		}
		
		gridView = (GridView) view.findViewById(R.id.grid); 
		CustomBaseAdapter adapter = new CustomBaseAdapter(getActivity(), imageItems);
		gridView.setAdapter(adapter);
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Bundle bundle = getArguments();
		//Log.i("GridView", "Bundle: " +bundle);
		/*
		Log.i("GridView", "Working1");
		imageItems = new ArrayList<ImageObject>();
		Log.i("GridView", "Working2");
		for (int i = 0;i <images.length; i++) {
			ImageObject item = new ImageObject(images[i], "user", "camera", "description", "telescope", "website");
			imageItems.add(item);
			Log.i("GridView", "Working3");
		}
		
		gridView = (GridView) getActivity().findViewById(R.id.grid);
		Log.i("GridView", "Working4");
		CustomBaseAdapter adapter = new CustomBaseAdapter(getActivity(), imageItems);
		Log.i("GridView", "Working5");
		gridView.setAdapter(adapter);
		Log.i("GridView", "Working6");*/
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Bundle bundle = new Bundle();
		bundle = getArguments();
		Log.i("GridView", "Bundle: " +bundle.getString("test"));
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
