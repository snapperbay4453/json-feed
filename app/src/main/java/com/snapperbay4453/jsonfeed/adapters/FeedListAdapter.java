package com.snapperbay4453.jsonfeed.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.snapperbay4453.jsonfeed.R;
import com.snapperbay4453.jsonfeed.databinding.ItemFeedListBinding;
import com.snapperbay4453.jsonfeed.models.Feed;

public class FeedListAdapter extends ListAdapter<Feed, FeedListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedListBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = ItemFeedListBinding.bind(view);
        }

        void onBind(Feed feed) {
            binding.itemFeedListNameTextView.setText(feed.getName());
        }
    }

    public FeedListAdapter() {
        super(Feed.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_list, parent, false);
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

