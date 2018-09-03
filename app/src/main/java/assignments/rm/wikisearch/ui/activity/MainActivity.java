package assignments.rm.wikisearch.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.SearchResultModel;
import assignments.rm.wikisearch.constant.WikiConstants;
import assignments.rm.wikisearch.db.DatabaseHelper;
import assignments.rm.wikisearch.listener.CardClickListener;
import assignments.rm.wikisearch.model.Page;
import assignments.rm.wikisearch.model.SearchQuery;
import assignments.rm.wikisearch.ui.adapter.SearchItemAdapter;
import assignments.rm.wikisearch.util.LogTracker;
import assignments.rm.wikisearch.util.NetworkHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.wiki_search_rv)
    RecyclerView rvWikiSearch;

    @BindView(R.id.wiki_search_srl)
    SwipeRefreshLayout srlWikiSearch;

    SearchItemAdapter adapter;
    NetworkHelper.HttpGetCallback getCallback;

    private SearchResultModel searchResultModel;
    private String searchCriteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(NetworkHelper.isOnline(this)){
            Toast.makeText(this,"Online",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Offline",Toast.LENGTH_SHORT).show();
        }

        initViews();
    }

    private void initCallbacks(){
        getCallback=new NetworkHelper.HttpGetCallback() {
            @Override
            public void handleSuccess(final Object object) {
                if(!TextUtils.isEmpty(searchCriteria)) {
                    SearchQuery searchQuery = new SearchQuery();
                    searchQuery.setQuery(searchCriteria);
                    long queryId = -1;

                    try {
                        queryId = DatabaseHelper.getInstance(getApplicationContext())
                                .getDb()
                                .searchQueryDAO()
                                .insertOne(searchQuery);
                    } catch (Exception e) {

                    }

                    if (object != null
                            && object instanceof JSONObject) {
                        try {
                            searchResultModel = new SearchResultModel();
                            ArrayList<Page> pageList = new ArrayList<Page>();
                            searchResultModel.setPageList(pageList);

                            JSONObject response = (JSONObject) object;
                            if (response.has("query")
                                    && !response.isNull("query")) {
                                JSONObject queryObj = response.getJSONObject("query");
                                if (queryObj.has("pages")
                                        && !queryObj.isNull("pages")) {
                                    JSONArray pagesArray = queryObj.getJSONArray("pages");
                                    for (int i = 0; i < pagesArray.length(); i++) {
                                        JSONObject pagesObj = pagesArray.getJSONObject(i);
                                        Page page = new Page();
                                        if (pagesObj != null
                                                && pagesObj.has("pageid")
                                                && !pagesObj.isNull("pageid")) {
                                            page.setPageId(pagesObj.getLong("pageid"));
                                        }

                                        if (pagesObj != null
                                                && pagesObj.has("ns")
                                                && !pagesObj.isNull("ns")) {
                                            page.setNs(pagesObj.getInt("ns"));
                                        }

                                        if (pagesObj != null
                                                && pagesObj.has("title")
                                                && !pagesObj.isNull("title")) {
                                            page.setTitle(pagesObj.getString("title"));
                                        }

                                        if (pagesObj != null
                                                && pagesObj.has("index")
                                                && !pagesObj.isNull("index")) {
                                            page.setIndex(pagesObj.getLong("index"));
                                        }

                                        if (pagesObj != null
                                                && pagesObj.has("thumbnail")
                                                && !pagesObj.isNull("thumbnail")) {
                                            JSONObject thumbnailJSON = pagesObj.getJSONObject("thumbnail");

                                            if (thumbnailJSON != null
                                                    && thumbnailJSON.has("source")
                                                    && !thumbnailJSON.isNull("source")) {
                                                page.setThumbnailURL(thumbnailJSON.getString("source"));
                                            }

                                            if (thumbnailJSON != null
                                                    && thumbnailJSON.has("width")
                                                    && !thumbnailJSON.isNull("width")) {
                                                page.setThumbnailWidth(thumbnailJSON.getInt("width"));
                                            }

                                            if (thumbnailJSON != null
                                                    && thumbnailJSON.has("height")
                                                    && !thumbnailJSON.isNull("height")) {
                                                page.setThumbnailHeight(thumbnailJSON.getInt("height"));
                                            }
                                        }

                                        if (pagesObj != null
                                                && pagesObj.has("terms")
                                                && !pagesObj.isNull("terms")) {
                                            JSONObject termsObj = pagesObj.getJSONObject("terms");
                                            if (termsObj != null
                                                    && termsObj.has("description")
                                                    && !termsObj.isNull("description")) {
                                                JSONArray descriptionArray = termsObj.getJSONArray("description");
                                                StringBuilder descriptionBuffer = new StringBuilder("");
                                                for (int j = 0; j < descriptionArray.length(); j++) {
                                                    descriptionBuffer.append(descriptionArray.getString(j));
                                                    if (j < descriptionArray.length() - 1) {
                                                        descriptionBuffer.append(",");
                                                    }
                                                }
                                                page.setDescription(descriptionBuffer.toString());
                                            }
                                        }

                                        page.setQueryId(queryId);
                                        pageList.add(page);
                                        if (queryId != -1) {
                                            DatabaseHelper
                                                    .getInstance(getApplicationContext())
                                                    .getDb()
                                                    .pageDAO()
                                                    .insertAll(page);
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (adapter != null) {
                                                    adapter.updatePages(searchResultModel.getPageList());
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        } catch (Exception e) {
                            LogTracker.trackException(MainActivity.class, e);
                        }
                    }
                }
            }

            @Override
            public void handleFailure(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchResultModel=new SearchResultModel();
                        Toast.makeText(MainActivity.this,"Failed to load",Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
    }

    private void initViews(){
        ArrayList<Page> pageList=new ArrayList<Page>();

        if(adapter==null){
            adapter=new SearchItemAdapter(pageList, this, new CardClickListener() {
                @Override
                public void onClick(long pageId, String title) {
                    if(pageId!=-1) {
                        Intent webIntent = new Intent(MainActivity.this, WebViewActivity.class);
                        webIntent.putExtra(WikiConstants.INTENT_WIKI_URL, WikiConstants.BASE_WIKI_URL + pageId);
                        webIntent.putExtra(WikiConstants.INTENT_WIKI_TITLE, title);
                        startActivity(webIntent);
                    }
                }
            });
        }

        rvWikiSearch.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvWikiSearch.setLayoutManager(linearLayoutManager);
        rvWikiSearch.setHasFixedSize(true);

        initCallbacks();

        srlWikiSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSearchData();
                srlWikiSearch.setRefreshing(false);
            }
        });

    }

    private void getSearchData(){
        if(NetworkHelper.isOnline(MainActivity.this)) {
            if(!TextUtils.isEmpty(searchCriteria)) {
                try{
                    NetworkHelper.callApiGet("https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description" +
                                    "&gpssearch=" + URLEncoder.encode(searchCriteria, "UTF-8")+
                            "&gpslimit=10",
                            getCallback);
                }catch (Exception e){
                    LogTracker.trackException(MainActivity.class,e);
                }
            }
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!TextUtils.isEmpty(searchCriteria)) {
                        searchResultModel = new SearchResultModel();
                        List<Page> pageList = DatabaseHelper.getInstance(getApplicationContext())
                                .getDb().pageDAO().getAllPagesBySearchQuery(searchCriteria);
                        if(pageList!=null
                                && !pageList.isEmpty()) {
                            searchResultModel.setPageList(pageList);
                        }
                        else{
                            pageList=DatabaseHelper.getInstance(getApplicationContext())
                                    .getDb().pageDAO().getAllPagesBySearchQueryOffline("%"+searchCriteria+"%");
                            searchResultModel.setPageList(pageList);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.updatePages(searchResultModel.getPageList());
                            }
                        });
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.app_hint));
        searchView.setOnQueryTextListener(this );
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchCriteria=query.toLowerCase();
        getSearchData();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
