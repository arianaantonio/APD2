package Fragments;

import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GridViewFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "GridviewFragment.TAG";
	
	public static GridViewFragment newInstance(int sectionNumber) {
		
		
		GridViewFragment fragment = new GridViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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
		return view;
	}
}
