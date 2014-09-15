package com.fullsail.android.fragmentdemo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.fullsail.android.fragmentdemo.R;

public class ButtonsFragment extends Fragment implements OnClickListener {
	
	public static final String TAG = "ButtonsFragment.TAG";
	
	public static ButtonsFragment newInstance() {
		ButtonsFragment frag = new ButtonsFragment();
		return frag;
	}
	
	public interface OnButtonClickListener {
		public void displayText(String _text);
	}
	
	private OnButtonClickListener mListener;
	
	@Override
	public void onAttach(Activity _activity) {
		super.onAttach(_activity);
		
		if(_activity instanceof OnButtonClickListener) {
			mListener = (OnButtonClickListener)_activity;
		} else {
			throw new IllegalArgumentException("Containing activity must implement OnButtonClickListener interface");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater _inflater, ViewGroup _container, 
			Bundle _savedInstanceState) {
		View view = _inflater.inflate(R.layout.buttons_fragment, _container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle _savedInstanceState) {
		super.onActivityCreated(_savedInstanceState);
		
		View view = getView();

		Button button = (Button)view.findViewById(R.id.android_3_button);
		button.setOnClickListener(this);

		button = (Button)view.findViewById(R.id.android_8_button);
		button.setOnClickListener(this);

		button = (Button)view.findViewById(R.id.android_11_button);
		button.setOnClickListener(this);

		button = (Button)view.findViewById(R.id.android_14_button);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View _v) {
		switch(_v.getId()) {
		case R.id.android_3_button:
			mListener.displayText(getString(R.string.android_3_name));
			break;
		case R.id.android_8_button:
			mListener.displayText(getString(R.string.android_8_name));
			break;
		case R.id.android_11_button:
			mListener.displayText(getString(R.string.android_11_name));
			break;
		case R.id.android_14_button:
			mListener.displayText(getString(R.string.android_14_name));
			break;
		}
	}
}