package assignments.rm.wikisearch.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.model.Page;
import assignments.rm.wikisearch.util.LogTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder> {

    private ArrayList<Page> pageList;
    private Context context;

    public SearchItemAdapter(ArrayList<Page> pageList,
                             Context context) {
        this.pageList = pageList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_card,parent,false);
        SearchItemViewHolder holder= new SearchItemViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.bindView(pageList.get(position));
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.search_thumbnail_iv)
        ImageView ivSearchThumbnail;

        @BindView(R.id.search_title_tv)
        TextView tvSearchTitle;

        @BindView(R.id.search_description_tv)
        TextView tvSearchDescription;

        public SearchItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(Page page){
            tvSearchTitle.setText("Stan Smith");
            tvSearchDescription.setText("The American Dad");

            try {
                Glide.with(context)
                        .load(Uri.parse("https://a1cf74336522e87f135f-2f21ace9a6cf0052456644b80fa06d4f.ssl.cf2.rackcdn.com/images/characters/p-American-Dad-Stan.jpg"))
                        .into(ivSearchThumbnail);
            }catch (Exception e){
                LogTracker.trackException(SearchItemAdapter.class,e);
            }
        }
    }
}