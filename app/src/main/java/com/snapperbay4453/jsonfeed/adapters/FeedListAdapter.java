package com.snapperbay4453.jsonfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.snapperbay4453.jsonfeed.R;
import com.snapperbay4453.jsonfeed.databases.LocalDatabase;
import com.snapperbay4453.jsonfeed.databinding.ItemFeedListBinding;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.viewmodels.FeedViewModel;

public class FeedListAdapter extends ListAdapter<Feed, FeedListAdapter.ViewHolder> {

    private Context context;
    private FeedViewModel feedViewModel;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedListBinding binding;
        private Feed feed;

        public ViewHolder(View view) {
            super(view);
            binding = ItemFeedListBinding.bind(view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }

        void onBind(Feed feed) {
            this.feed = feed;
            binding.itemFeedListNameTextView.setText(feed.getName());
            binding.itemFeedListNameTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    feedViewModel.remove(feed);
                }
            });
        }
    }

    public FeedListAdapter(FeedViewModel feedViewModel) {
        super(Feed.DIFF_CALLBACK);
        this.feedViewModel = feedViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_list, parent, false);
        context = parent.getContext();
        return new FeedListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = getItem(position);
        if(feed != null) {
            holder.onBind(feed);
        }
    }
}

