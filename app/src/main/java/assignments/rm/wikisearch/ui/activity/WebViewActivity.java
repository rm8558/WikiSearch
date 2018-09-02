package assignments.rm.wikisearch.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.constant.WikiConstants;
import assignments.rm.wikisearch.util.NetworkHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.wiki_page_wv)
    WebView wvWikiPage;

    @BindView(R.id.wiki_page_srl)
    SwipeRefreshLayout srlWikiPage;

    private String wikiUrl;
    private String wikiTitle;

    private class WikiWebViewClient extends WebViewClient{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ButterKnife.bind(this);
        initURL();
        initViews();
        showInitWebPage();
    }

    private void showInitWebPage(){
        if(!TextUtils.isEmpty(wikiUrl)){
            if(!TextUtils.isEmpty(wikiTitle)){
                getSupportActionBar().setTitle(wikiTitle);
            }

            wvWikiPage.loadUrl(wikiUrl);
        }
        else{
            Toast.makeText(WebViewActivity.this,
                    "blank url",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void initViews(){
        wvWikiPage.getSettings().setJavaScriptEnabled(true);
        wvWikiPage.getSettings().setAppCacheEnabled(true);
        wvWikiPage.getSettings().setLoadWithOverviewMode(true);
        wvWikiPage.getSettings().setBuiltInZoomControls(true);
        wvWikiPage.getSettings().setUseWideViewPort(true);
        wvWikiPage.setWebViewClient(new WikiWebViewClient());

        srlWikiPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetworkHelper.isOnline(WebViewActivity.this)){
                    showInitWebPage();
                }
                else{
                    Toast.makeText(WebViewActivity.this,
                            "Offline",
                            Toast.LENGTH_LONG)
                            .show();
                }

                srlWikiPage.setRefreshing(false);
            }
        });
    }

    private void initURL(){
        Intent intent=getIntent();
        if(intent!=null){
            wikiUrl=intent.getStringExtra(WikiConstants.INTENT_WIKI_URL);
            wikiTitle=intent.getStringExtra(WikiConstants.INTENT_WIKI_TITLE);
        }
    }
}
