package Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arianaantonio.astropix.MainActivity;
import com.arianaantonio.astropix.R;

public class MainFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TAG = "MainFragment.TAG";
	
	public static MainFragment newInstance() {
		MainFragment frag = new MainFragment();
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
		
		return view;
	}

}
