package com.arianaantonio.astropix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Fragments.DetailFragment;
import Fragments.FavoritesFragment;
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
import api.FileManager;
import api.ServiceClass;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchFragment.ParentListener, GridViewFragment.ParentListener, MainFragment.ParentListener, FavoritesFragment.ParentListener {
	
	Context mContext;
	FileManager mFile;
	String mFileName = "ImageFile.txt";
	static String currentView = new String();
	String query;
	String mainImageClicked;

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

        getData(handler);

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
			    		JSONObject json = new JSONObject(fileContent);
			    	
				    	Log.i("Main Activity", "Handler working here");
				    	if (currentView.equals("detail")) {
				    		activity.displayMainData(json);
				    	} else {
				    		JSONArray imagesArray = json.getJSONArray("objects");
				    		activity.displayData(imagesArray);
				    	}
				    	//activity.displayData(imagesArray);
	
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
    	} else if (currentView.equals("detail")) {
    		getIntent.putExtra("view" , mainImageClicked);
    	} else {
    		getIntent.putExtra("view", "imageOfTheDay");
    	}
    	startService(getIntent);
    }
    
    public void displayMainData(JSONObject json) {
    	try {
    		HashMap<String, Object> displayText = new HashMap<String, Object>();
			String url = json.getString("url_duckduckgo");
			String camera = json.getString("imaging_cameras");
			String telescope = json.getString("imaging_telescopes");
			String username = json.getString("user");
			String description = json.getString("description");
			String title = json.getString("title");
			String website = url.substring(24, 30);

			displayText.put("url", url);
			displayText.put("description", description);
			displayText.put("camera", camera);
			displayText.put("telescope", telescope);
			displayText.put("username", username);
			displayText.put("title", title);
			displayText.put("website", website);

			Bundle bundle = new Bundle();
			bundle.putSerializable("clicked data", displayText);
			
			FragmentManager manager = getFragmentManager();
			DetailFragment fragment = new DetailFragment();
			fragment.setArguments(bundle);
			manager.beginTransaction().replace(R.id.container, fragment).commit();

			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
    }
    
    public void displayData(JSONArray jsonArray) throws MalformedURLException {
  
    	FragmentManager manager = getFragmentManager();
    	myData.clear();
    	
		if (jsonArray.length() == 0) {
			HashMap<String, Object> displayText = new HashMap<String, Object>();
			String url = "null";
			displayText.put("url", url);
			myData.add(displayText);
			Bundle bundle = new Bundle();
			bundle.putSerializable("passed data", myData);
			
			mTitle = getString(R.string.title_section5);
			restoreActionBar();
			
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
    				String website = url.substring(24, 30);
    				

    				displayText.put("url", url);
    				displayText.put("description", description);
    				displayText.put("camera", camera);
    				displayText.put("telescope", telescope);
    				displayText.put("username", username);
    				displayText.put("title", title);
    				displayText.put("website", website);

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
    				String website = url.substring(24, 30);

    				displayText.put("url", url);
    				displayText.put("description", description);
    				displayText.put("camera", camera);
    				displayText.put("telescope", telescope);
    				displayText.put("username", username);
    				displayText.put("title", title);
    				displayText.put("website", website);

    				myData.add(displayText);
    				Bundle bundle = new Bundle();
    				bundle.putSerializable("passed data", myData);

    				SearchFragment fragment = new SearchFragment();
    				fragment.setArguments(bundle);
    				manager.beginTransaction().replace(R.id.container, fragment).commit();
    			}
    			else {
    				HashMap<String, Object> displayText = new HashMap<String, Object>();
    				
    				String url = jsonArray.getJSONObject(i).getString("image");
    				url = url.substring(14, 20);
    				String runnerUp = jsonArray.getJSONObject(i).getString("runnerup_1");
    				runnerUp = runnerUp.substring(14, 20);
    				
    				//http://www.astrobin.com/%@/0/rawthumb/hd/
    				url = "http://www.astrobin.com/"+url+"/0/rawthumb/hd/";
    				runnerUp = "http://www.astrobin.com/"+runnerUp+"/0/rawthumb/hd/";
    				displayText.put("url", url);
    				displayText.put("runnerUp", runnerUp);
    				myData.add(displayText);
    				Bundle bundle = new Bundle();
    				bundle.putSerializable("passed data", myData);

    				MainFragment fragment = new MainFragment();
    				fragment.setArguments(bundle);
    				manager.beginTransaction().replace(R.id.container, fragment).commit();
    				
    				//imageView.setImageUrl(url);
    				//imageView2.setImageUrl(runnerUp);
    				;
    			}

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
        
        
    	
    	
    	if (position == 0) {
    		Log.i("Nav Fragment", "You selected Image of the day");
 
    		currentView = "imageOfTheDay";
    		getData(handler);
     	  
        } else if (position == 1) {
        	Log.i("Nav Fragment", "You selected Most Recent");
        	currentView = "recent";
        	mTitle = getString(R.string.title_section2);
        	
        	getData(handler);
     	   
        } else if (position == 2) {
        	Log.i("Nav Fragment", "You selected Search AstroBin");

        	currentView = "search";
        	mTitle = getString(R.string.title_section3);
        	SearchFragment sFrag = new SearchFragment();
        	fragmentManager.beginTransaction().replace(R.id.container, sFrag).commit();

        } else {
        	Log.i("Nav Fragment", "You selected Favorites");
        	currentView = "favorites";
        	mTitle = getString(R.string.title_section4);
        	try {
        		getFavorites();
        	} catch (JSONException e) {
        	
        		e.printStackTrace();
        	} catch (StreamCorruptedException e) {
        		
        		e.printStackTrace();
        	} catch (IOException e) {
        		
        		e.printStackTrace();
        	}

        }
    }
    @SuppressWarnings("unchecked")
	public void getFavorites() throws JSONException, StreamCorruptedException, IOException {
    	
    	//final ArrayList<String> myData = new ArrayList<String>();
    	ArrayList<HashMap<String, ?>> myPassedData = new ArrayList<HashMap<String, ?>>();
    	
    	ArrayList<HashMap<String,String>> arraylist= new ArrayList<HashMap<String,String>>();
    	
    	String filePath = this.getFilesDir().getPath().toString() + "/FavoritesFile.ser"; 
  
    	try {
    		FileInputStream fileIn = new FileInputStream(filePath);
    		ObjectInputStream in = new ObjectInputStream(fileIn);
    		arraylist = (ArrayList<HashMap<String,String>>) in.readObject();
    		in.close();
    		fileIn.close();
    	} 
    	catch (IOException e) {
    		e.printStackTrace(); 
    	} 

    	catch (ClassNotFoundException e) {
    		
    		e.printStackTrace();
    	}
    	//Log.i("Main Activity", "Array File: " +arraylist);
    	
    	for (int i = 0; i < arraylist.size(); i++){
    		HashMap<String, String> displayText = new HashMap<String, String>();
    		
    		String url = arraylist.get(i).get("url");
    		String website = arraylist.get(i).get("website");
    		String username = arraylist.get(i).get("username");
    		String camera = arraylist.get(i).get("camera");
    		String telescope = arraylist.get(i).get("telescope");
    		String title = arraylist.get(i).get("title");
    		String description = arraylist.get(i).get("description");
    		
    		
    		displayText.put("url", url);
			displayText.put("description", description);
			displayText.put("camera", camera);
			displayText.put("telescope", telescope);
			displayText.put("username", username);
			displayText.put("title", title);
			displayText.put("website", website);
    		
    		myPassedData.add(displayText);
    		Bundle bundle = new Bundle();
			bundle.putSerializable("passed data", myPassedData);
			FragmentManager manager = getFragmentManager();
			FavoritesFragment fragment = new FavoritesFragment();
			fragment.setArguments(bundle);
			manager.beginTransaction().replace(R.id.container, fragment).commit();
    	}
    	Log.i("Main Activity", "My data:" +myPassedData);
    }
    @Override
	protected void onResume() {
		
		super.onResume(); 
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
	@Override
	public void passBackClickedItem(HashMap<String, ?> item) {
		//Log.i("Main Activity", "Passed over: " +item);
		Bundle bundle = new Bundle();
		bundle.putSerializable("clicked data", item);
		
		mTitle = getString(R.string.title_section5);
		restoreActionBar();
		
		FragmentManager manager = getFragmentManager();
		DetailFragment fragment = new DetailFragment();
		fragment.setArguments(bundle);
		manager.beginTransaction().replace(R.id.container, fragment).commit();
		
	}

	@Override
	public void passBackClickedItemMain(String item) {
		Log.i("Main Activity", "Passed image: " +item);
		item = item.substring(24, 30);
		mainImageClicked = "detail" +item;
		currentView = "detail";
		getData(handler);
		
	}

}
