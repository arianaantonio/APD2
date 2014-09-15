package com.fullsail.android.fragmentdemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullsail.android.fragmentdemo.R;

public class DisplayFragment extends Fragment {
	
	public static final String TAG = "DisplayFragment.TAG";
	
	private static final String ARG_TEXT = "DisplayFragment.ARG_TEXT";
	
	public static DisplayFragment newInstance(String _text) {
		DisplayFragment frag = new DisplayFragment();
		
		Bundle args = new Bundle();
		args.putString(ARG_TEXT, _text);
		frag.setArguments(args);
		
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
			Bundle _savedInstanceState) {
		View view = _inflater.inflate(R.layout.display_fragment, _container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle _savedInstanceState) {
		super.onActivityCreated(_savedInstanceState);
		
		Bundle args = getArguments();
		if(args != null && args.containsKey(ARG_TEXT)) {
			setDisplayText(args.getString(ARG_TEXT));
		}
	}
	
	public void setDisplayText(String _text) {
		TextView tv = (TextView)getView().findViewById(R.id.name_display);
		tv.setText(_text);
	}
}