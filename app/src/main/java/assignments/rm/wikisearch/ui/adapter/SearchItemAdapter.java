package assignments.rm.wikisearch.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import assignments.rm.wikisearch.R;
import assignments.rm.wikisearch.listener.CardClickListener;
import assignments.rm.wikisearch.model.Page;
import assignments.rm.wikisearch.util.LogTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder> {

    private List<Page> pageList;
    private Context context;
    private CardClickListener cardClickListener;

    public SearchItemAdapter(ArrayList<Page> pageList,
                             Context context,
                             CardClickListener cardClickListener) {
        this.pageList = pageList;
        this.context = context;
        this.cardClickListener = cardClickListener;
    }

    public void updatePages(List<Page> pageList){
        this.pageList=pageList;
        notifyDataSetChanged();
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

    public class SearchItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.search_thumbnail_iv)
        ImageView ivSearchThumbnail;

        @BindView(R.id.search_title_tv)
        TextView tvSearchTitle;

        @BindView(R.id.search_description_tv)
        TextView tvSearchDescription;

        public SearchItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        public void bindView(Page page){
            tvSearchTitle.setText(page.getTitle());
            tvSearchDescription.setText(page.getDescription());

            try {
                if(!TextUtils.isEmpty(page.getThumbnailURL())) {
                    Glide.with(context)
                            .load(Uri.parse(page.getThumbnailURL()))
                            .into(ivSearchThumbnail);
                }
                else{
                    ivSearchThumbnail.setImageDrawable(null);
                }
            }catch (Exception e){
                LogTracker.trackException(SearchItemAdapter.class,e);
            }
        }

        @Override
        public void onClick(View v) {
            try {
                cardClickListener.onClick(pageList.get(getAdapterPosition()).getPageId(),
                        pageList.get(getAdapterPosition()).getTitle());
            }catch (Exception e){
                cardClickListener.onClick(-1, null);
                LogTracker.trackException(SearchItemAdapter.class,e);
            }
        }
    }
}
