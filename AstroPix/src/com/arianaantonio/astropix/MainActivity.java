package com.arianaantonio.astropix;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Fragments.GridViewFragment;
import Fragments.MainFragment;
import Fragments.NavigationDrawerFragment;
import Fragments.SearchFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
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
import android.widget.SearchView;
import android.widget.Toast;
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
	String currentView = new String();
	String query;

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
        setContentView(R.layout.activity_main); 
        mContext = this;
        mFile = FileManager.getInstance();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        
        //handleSearchIntent(getIntent());
/*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("search_value");
            Log.i("Main Activity", "Search value: " +value);
            query = value;
            currentView = "search";*/
        //} //else {
        	getData(handler);
       // }
        
        //final MyHandler handler = new MyHandler(this);
       
        //getData(handler);
   
        
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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
	    	
	    	if (activity !=null) {
		    	Object objectReturned = message.obj;
		    	String filename = objectReturned.toString();
		    	Log.i("Filename", filename);
		    	if (message.arg1 == RESULT_OK && objectReturned !=null) {
			    	
			    	String fileContent = fileManager.readStringFile(activity, filename);
			    	Log.i("Main Activity", "File content: " +fileContent);
			    	
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
    	
    	Messenger messenger = new Messenger(handler);
    	Intent getIntent = new Intent(this, ServiceClass.class);
    	getIntent.putExtra("messenger", messenger);
    	if (currentView.equals("imageOfTheDay")) {
    		getIntent.putExtra("view", "imageOfTheDay");
    	} else if (currentView.equals("recent")) {
    		getIntent.putExtra("view", "recent");
    	} else if (currentView.equals("favorites")) {
    		getIntent.putExtra("view", "favorites");
    	} else if (currentView.equals("search")){
    		getIntent.putExtra("view", query);
    	} else {
    		getIntent.putExtra("view", "imageOfTheDay");
    	}
    	startService(getIntent);
    }
    
    public void displayData(JSONArray jsonArray) throws MalformedURLException {
    
    	imageView = (SmartImageView) findViewById(R.id.image_of_the_day);
        imageView2 = (SmartImageView) findViewById(R.id.runner_up);
  
    	FragmentManager manager = getFragmentManager();
    	myData.clear();
    	Log.i("Test", "test " +jsonArray);
    	
		if (jsonArray.length() == 0) {
			HashMap<String, Object> displayText = new HashMap<String, Object>();
			String url = "null";
			displayText.put("url", url);
			myData.add(displayText);
			Bundle bundle = new Bundle();
			bundle.putSerializable("passed data", myData);
			
	    	SearchFragment fragment = new SearchFragment();
	    	fragment.setArguments(bundle);
	    	manager.beginTransaction().replace(R.id.container, fragment).commit();
		}
    	
    	for (int i = 0; i < jsonArray.length(); i++) {
    		
    		
    		try {
   
	    			if (currentView.equals("recent")) {
	    				HashMap<String, Object> displayText = new HashMap<String, Object>();
	    				String url = jsonArray.getJSONObject(i).getString("url_duckduckgo");
	    				String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
	    				String telescope = jsonArray.getJSONObject(i).getString("imaging_telescopes");
	    				String username = jsonArray.getJSONObject(i).getString("user");
	    				String description = jsonArray.getJSONObject(i).getString("description");
	    				String title = jsonArray.getJSONObject(i).getString("title");
	        			//Log.i("Main", "url and description: " +url+description);
	        			//http://www.astrobin.com/%@/0/rawthumb/hd/
	        		
	        			//HashMap<String, Object> displayText = new HashMap<String, Object>();
	        			displayText.put("url", url);
	        			displayText.put("description", description);
	    				displayText.put("camera", camera);
	    				displayText.put("telescope", telescope);
	    				displayText.put("username", username);
	    				displayText.put("title", title);
	    				
	        			myData.add(displayText);
	        			Bundle bundle = new Bundle();
	        			bundle.putSerializable("passed data", myData);
	        			
	        	    	GridViewFragment fragment = new GridViewFragment();
	        	    	fragment.setArguments(bundle);
	        	    	manager.beginTransaction().replace(R.id.container, fragment).commit();
	        			
	    			} else if (currentView.equals("search")) {
	    				HashMap<String, Object> displayText = new HashMap<String, Object>();
	    			
		    				String url = jsonArray.getJSONObject(i).getString("url_duckduckgo");
		    				String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
		    				String telescope = jsonArray.getJSONObject(i).getString("imaging_telescopes");
		    				String username = jsonArray.getJSONObject(i).getString("user");
		    				String description = jsonArray.getJSONObject(i).getString("description");
		    				String title = jsonArray.getJSONObject(i).getString("title");
		        			//Log.i("Main", "url and description: " +url+description);
		        			//http://www.astrobin.com/%@/0/rawthumb/hd/
		        		
		        			
		        			displayText.put("url", url);
		        			displayText.put("description", description);
		    				displayText.put("camera", camera);
		    				displayText.put("telescope", telescope);
		    				displayText.put("username", username);
		    				displayText.put("title", title);
	    				
	        			myData.add(displayText);
	        			Bundle bundle = new Bundle();
	        			bundle.putSerializable("passed data", myData);
	        			
	        	    	SearchFragment fragment = new SearchFragment();
	        	    	fragment.setArguments(bundle);
	        	    	manager.beginTransaction().replace(R.id.container, fragment).commit();
	    			}
	    			else {
	    		
	    			
	    			String url = jsonArray.getJSONObject(i).getString("image");
	    			Log.i("Main", "URL: " +url);
	    			url = url.substring(14, 20);
	    			String runnerUp = jsonArray.getJSONObject(i).getString("runnerup_1");
	    			runnerUp = runnerUp.substring(14, 20);
	    			//http://www.astrobin.com/%@/0/rawthumb/hd/
	    			url = "http://www.astrobin.com/"+url+"/0/rawthumb/hd/";
	    			runnerUp = "http://www.astrobin.com/"+runnerUp+"/0/rawthumb/hd/";
	    			imageView.setImageUrl(url);
	    			imageView2.setImageUrl(runnerUp);
	    			Log.i("Main Activity", "url: " +url+ " runner up: " + runnerUp);
	    			}
    			
    			
    			//String title = jsonArray.getJSONObject(i).getString("title");
    			//String user = jsonArray.getJSONObject(i).getString("user");
    			//String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
    			//camera = camera.replace("[", "");
    			//camera = camera.replace("]", "");
    			//camera = camera.replace("\"", "");
    			//String hd = jsonArray.getJSONObject(i).getString("url_hd");

    			//Log.i("Returned objects", title+ " " +user+" " +camera+ " " +url);
    			//HashMap<String, Object> displayText = new HashMap<String, Object>();
    			
    			//displayText.put("title", title);
    			//displayText.put("user", user);
    			//displayText.put("imaging_cameras", camera);
    			//displayText.put("url", url);
    			//displayText.put("hdImage", hd);

    			//myData.add(displayText);*/
    		} catch (JSONException e) {
    			Log.e("Error displaying data in listview", e.getMessage().toString());
    			e.printStackTrace();
    		}
    	}
    }
    

    @Override
    protected void onNewIntent(Intent intent) {
    	
        handleSearchIntent(intent);
    }

    private void handleSearchIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("Main Activity", "Searched: " +query);
            getData(handler);
        }
    }

    
    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	FragmentManager fragmentManager = getFragmentManager();
        String itemClicked = new String("");
        
    	MainFragment fragmentMain = (MainFragment) fragmentManager.findFragmentByTag(MainFragment.TAG);
    	
    	if (position == 0) {
     	   Log.i("Nav Fragment", "You selected Image of the day");
     	  //fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
     	 fragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance(position + 1)).commit();
     	  itemClicked = "imageOfTheDay";
     	 currentView = "imageOfTheDay";
     	if (fragmentMain !=null) {
    	 } else {
    		 fragmentMain = new MainFragment();
    	 }
    	getData(handler);
     	  
        } else if (position == 1) {
        	Log.i("Nav Fragment", "You selected Most Recent");
        	itemClicked = "Recent";
        	currentView = "recent";
        	mTitle = getString(R.string.title_section2);
        	
        	getData(handler);
     	   
        } else if (position == 2) {
        	Log.i("Nav Fragment", "You selected Search AstroBin");
        	itemClicked = "Search";
        	currentView = "search";
        	mTitle = getString(R.string.title_section3);
        	SearchFragment sFrag = new SearchFragment();
       	    fragmentManager.beginTransaction().replace(R.id.container, sFrag).commit();
        	//getData(handler);
      	
        } else {
     	   Log.i("Nav Fragment", "You selected Favorites");
     	   itemClicked = "Favorites";
     	   currentView = "favorites";
     	  mTitle = getString(R.string.title_section4);
     	   SearchFragment sFrag = new SearchFragment();
     	  fragmentManager.beginTransaction().replace(R.id.container, sFrag).commit();
     	 
        }
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//getData(handler); 
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
        	if (currentView.equals("search")) {
        		getMenuInflater().inflate(R.menu.options_menu, menu);
        		
        		SearchManager searchManager =
        		           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        		    SearchView searchView =
        		            (SearchView) menu.findItem(R.id.search).getActionView();
        		    
        		    searchView.setSearchableInfo(
        		            searchManager.getSearchableInfo(getComponentName()));
        		    //ComponentName cn = new ComponentName(this, SearchActivity.class);
        		    //searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        		
        	}
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
