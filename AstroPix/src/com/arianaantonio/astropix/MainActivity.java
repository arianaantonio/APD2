package com.arianaantonio.astropix;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Fragments.DetailFragment;
import Fragments.GridViewFragment;
import Fragments.NavigationDrawerFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import api.FileManager;
import api.ServiceClass;

import com.loopj.android.image.SmartImageView;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	Context mContext;
	FileManager mFile;
	String mFileName = "ImageFile.txt";
	private SmartImageView imageView;
	private SmartImageView imageView2;

	private static FileManager fileManager = FileManager.getInstance();
	final MyHandler handler = new MyHandler(this);
	static ArrayList<HashMap<String, ?>> myData = new ArrayList<HashMap<String, ?>>();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment; 

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Main activity", "Working 1");
        setContentView(R.layout.activity_main); 
        Log.i("Main activity", "Working 2");
        mContext = this;
        mFile = FileManager.getInstance();
        
        //imageView = (SmartImageView) findViewById(R.id.image_of_the_day);
        //imageView2 = (SmartImageView) findViewById(R.id.runner_up);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        
        final MyHandler handler = new MyHandler(this);
        getData(handler); 
        
        //MainFragment frag = MainFragment.newInstance();
		//getFragmentManager().beginTransaction().replace(R.id.container2, frag, MainFragment.TAG).commit();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        Log.i("Main activity", "Working 3");
    } 
    
    private static class MyHandler extends Handler {
    
    	private final WeakReference<MainActivity> myActivity;
    	public MyHandler(MainActivity activity) {
    		Log.i("Main activity", "Inside handleMessage");
    	myActivity = new WeakReference<MainActivity>(activity);
    	}

    	@Override
    	public void handleMessage(Message message) {
	    	MainActivity activity = myActivity.get();
	    	Log.i("Main activity", "Inside handleMessage");
	    	if (activity !=null) {
		    	Object objectReturned = message.obj;
		    	String filename = objectReturned.toString();
		    	Log.i("Filename", filename);
		    	if (message.arg1 == RESULT_OK && objectReturned !=null) {
			    	Log.i("Main Activity", "Message handler");
			    	String fileContent = fileManager.readStringFile(activity, filename);
			    	Log.i("Main Activity", "File content: " +fileContent);
			    	Log.i("Main Activity", "working");
			    	try {
				    	//Log.i("Main Activity", "Handler working here");
				    	JSONObject json = new JSONObject(fileContent);
				    	Log.i("Main Activity", "Handler working here");
				    	JSONArray imagesArray = json.getJSONArray("objects");
				    	activity.displayData(imagesArray);
			    	} catch (JSONException e) {
				    	Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+fileContent);
				    	e.printStackTrace();
			    	} catch (MalformedURLException e) {
				    	e.printStackTrace();
			    	}
		    	}
	    	}
    	}
    } 
    //start intent service to get the API data, passing in the hander object
    public void getData(Handler handler) {
    	Log.i("Main activity", "Inside getData");
    	Messenger messenger = new Messenger(handler);
    	Intent getIntent = new Intent(this, ServiceClass.class);
    	getIntent.putExtra("messenger", messenger);
    	Log.i("Main activity", "Inside getdata2");
    	startService(getIntent);
    }
    
    public void displayData(JSONArray jsonArray) throws MalformedURLException {
    	Log.i("Main Activity", "working1");
    	imageView = (SmartImageView) findViewById(R.id.image_of_the_day);
        imageView2 = (SmartImageView) findViewById(R.id.runner_up);  
    	for (int i = 0; i < jsonArray.length(); i++) {
    		
    		Log.i("Main Activity", "working2");
    		try {
    			String url = jsonArray.getJSONObject(i).getString("image");
    			url = url.substring(14, 20);
    			String runnerUp = jsonArray.getJSONObject(i).getString("runnerup_1");
    			runnerUp = runnerUp.substring(14, 20);
    			//http://www.astrobin.com/%@/0/rawthumb/hd/
    			url = "http://www.astrobin.com/"+url+"/0/rawthumb/hd/";
    			runnerUp = "http://www.astrobin.com/"+runnerUp+"/0/rawthumb/hd/";
    			imageView.setImageUrl(url);
    			imageView2.setImageUrl(runnerUp);
    			
    			//String title = jsonArray.getJSONObject(i).getString("title");
    			//String user = jsonArray.getJSONObject(i).getString("user");
    			//String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
    			//camera = camera.replace("[", "");
    			//camera = camera.replace("]", "");
    			//camera = camera.replace("\"", "");
    			//String hd = jsonArray.getJSONObject(i).getString("url_hd");

    			//Log.i("Returned objects", title+ " " +user+" " +camera+ " " +url);
    			//HashMap<String, Object> displayText = new HashMap<String, Object>();
    			Log.i("Main Activity", "url: " +url+ " runner up: " + runnerUp);
    			//displayText.put("title", title);
    			//displayText.put("user", user);
    			//displayText.put("imaging_cameras", camera);
    			//displayText.put("url", url);
    			//displayText.put("hdImage", hd);

    			Log.i("Main Activity", "working3");
    			//myData.add(displayText);
    		} catch (JSONException e) {
    			Log.e("Error displaying data in listview", e.getMessage().toString());
    			e.printStackTrace();
    		}
    	}
    	/*
    	FragmentManager manager = getFragmentManager();
    	MainFragment fragment = (MainFragment) manager.findFragmentById(R.id.main_fragment);
    	if (fragment !=null) {
    		//fragment.displayData(myData);
    	} else {
    		fragment = new MainFragment();
    		//fragment.displayData(myData);
    	}*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	FragmentManager fragmentManager = getFragmentManager();
        
   
    	
    	if (position == 0) {
     	   Log.i("Nav Fragment", "You selected Image of the day");
     	  fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
     	  //final MyHandler handler = new MyHandler(this);
          //getData(handler); 
     	 // fragmentManager.beginTransaction()
         // .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
         // .commit();
     	 //MainFragment frag = MainFragment.newInstance();
 		//getFragmentManager().beginTransaction().replace(R.id.container1, frag, MainFragment.TAG).commit();

        } else if (position == 1) {
     	   Log.i("Nav Fragment", "You selected Most Recent");
     	  //fragmentManager.beginTransaction().replace(R.id.container1,
     	  //DetailFragment.newInstance(position + 1)).commit();
     	 fragmentManager.beginTransaction().replace(R.id.container, DetailFragment.newInstance(position + 1)).commit();
     	
     
     	   
        } else if (position == 2) {
     	   Log.i("Nav Fragment", "You selected Search AstroBin");
     	  // fragment = new DetailFragment();
     	  fragmentManager.beginTransaction().replace(R.id.container, GridViewFragment.newInstance(position + 1)).commit();
     	  
       	
      	
        } else {
     	   Log.i("Nav Fragment", "You selected Favorites");
     	   //fragment = new DetailFragment();
     	  fragmentManager.beginTransaction().replace(R.id.container, GridViewFragment.newInstance(position + 1)).commit();
     	  
        }
        // update the main content by replacing fragments
        /*
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData(handler); 
	}

	public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
            	mTitle = getString(R.string.title_section4);
            	break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar(); 
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
