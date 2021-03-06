package com.paulina.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.paulina.gridimagesearch.R;
import com.paulina.gridimagesearch.adapters.ImageResultsAdapter;
import com.paulina.gridimagesearch.fragments.AdvancedFiltersFragment;
import com.paulina.gridimagesearch.helpers.EndlessScrollListener;
import com.paulina.gridimagesearch.models.Filters;
import com.paulina.gridimagesearch.models.ImageResult;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements AdvancedFiltersFragment.AdvancedFiltersFragmentDialogListener {

    private StaggeredGridView sgvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private FragmentManager mFragmentManager;
    private AdvancedFiltersFragment mFiltersDialog;
    private Filters mFilters;
    private String mImageSize;
    private String mImageColor;
    private String mImageType;
    private String mSite;
    private String mQuery;
    private int mOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        if (!isOnline()) {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//            // set dialog message
//            alertDialogBuilder
//                    .setTitle("ERROR")
//                    .setMessage("No Network Connectivity!")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // if OK button is clicked, close current activity
//                            SearchActivity.this.finish();
//                        }
//                    });
//
//            // create alert dialog
//            AlertDialog alertDialog = alertDialogBuilder.create();
//
//            // show it
//            alertDialog.show();
//        }
        setupViews();
        imageResults = new ArrayList<ImageResult>(); // creates the data source
        aImageResults = new ImageResultsAdapter(this, imageResults); // attaches the data source to an adapter
        sgvResults.setAdapter(aImageResults); // link adapter to the adapterview (gridview)

        mFragmentManager = getSupportFragmentManager();
        mFiltersDialog = AdvancedFiltersFragment.newInstance(mFilters);
    }

    @Override
    public void getDataFromDialog(Bundle result) {
        mFilters = (Filters)result.getSerializable("filters");
        mImageSize = mFilters.imageSize;
        mImageColor = mFilters.colorFilter;
        mImageType = mFilters.imageType;
        mSite = mFilters.siteFilter;
    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setupViews() {
//        etSearchQuery = (EditText) findViewById(R.id.etSearchField);
        sgvResults = (StaggeredGridView) findViewById(R.id.staggeredGridViewResults);
        sgvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an intent
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);

                // Get image result to display
                ImageResult result = imageResults.get(position);

                // Pass image result into intent
                intent.putExtra("result", result);

                // Launch the new activity
                startActivity(intent);
            }
        });
        // Attach the listener to the AdapterView onCreate
        sgvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                doImageSearch(mQuery);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                imageResults.clear(); // clear existing images from the array in cases where it's a new search
                doImageSearch(mQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    public void doImageSearch(String query) {

        String url = composeUrl(query);
        mOffset += 8;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    // you can update the items of the array list through the adapter itself
                    // when you make changes to the adapter, it does modify the underlying data for you automatically
                    // this adds data to the array list & triggers notify data change
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson)); // or imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showAdvancedFiltersDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAdvancedFiltersDialog() {
        mFiltersDialog.show(mFragmentManager, "filters dialog");
    }

    private String composeUrl(String query) {
        String params = "";
        if (mImageColor != null && !mImageColor.equals("any")) {
            params += "&imgcolor=" + mImageColor;
        }
        if (mImageSize != null && !mImageSize.equals("any")) {
            params += "&imgsz=" + mImageSize;
        }
        if (mImageType != null && !mImageType.equals("any")) {
            params += "&imgtype=" + mImageType;
        }
        if (!mSite.equals("") && !mSite.equals("any")) {
            params += "&as_sitesearch=" + mSite;
        }
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8&start=" + mOffset + params;
        return url;
    }
}