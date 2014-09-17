package com.arianaantonio.astropix;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Search Activity", "Going to search activity");
        handleIntent(getIntent());
        //setContentView(R.layout.fragment_search);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("Search Activity", "Going to search activity");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("Search Activity", "Searched: " +query);
            
            Intent searchIntent = new Intent(getBaseContext(), MainActivity.class);
            searchIntent.putExtra("search_value", query);
            startActivity(searchIntent);
        }
    }


}
