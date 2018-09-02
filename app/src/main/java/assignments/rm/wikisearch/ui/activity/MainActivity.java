package assignments.rm.wikisearch.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.model.Page;
import assignments.rm.wikisearch.ui.adapter.SearchItemAdapter;
import assignments.rm.wikisearch.util.NetworkHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.wiki_search_rv)
    RecyclerView rvWikiSearch;

    SearchItemAdapter adapter;

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

    private void initViews(){
        ArrayList<Page> pageList=new ArrayList<Page>();
        pageList.add(new Page());
        pageList.add(new Page());
        pageList.add(new Page());
        pageList.add(new Page());
        pageList.add(new Page());
        pageList.add(new Page());


        if(adapter==null){
            adapter=new SearchItemAdapter(pageList, this);
        }

        rvWikiSearch.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvWikiSearch.setLayoutManager(linearLayoutManager);
        rvWikiSearch.setHasFixedSize(true);


    }
}
