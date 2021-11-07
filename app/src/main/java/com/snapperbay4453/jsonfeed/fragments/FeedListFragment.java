package com.snapperbay4453.jsonfeed.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snapperbay4453.jsonfeed.R;
import com.snapperbay4453.jsonfeed.adapters.FeedListAdapter;
import com.snapperbay4453.jsonfeed.databinding.FragmentFeedListBinding;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.viewmodels.FeedViewModel;

import java.util.List;

public class FeedListFragment extends Fragment {

    private FragmentFeedListBinding binding;
    private View view;
    private FeedViewModel feedViewModel;
    private FeedListAdapter feedListAdapter;
    private Handler handler;
    private Toolbar toolbar;
    private RecyclerView feedRecyclerView;
    private FloatingActionButton refreshAllFeedsFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentFeedListBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        Context context = container.getContext();
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        feedListAdapter = new FeedListAdapter(feedViewModel);
        handler = new Handler();

        toolbar = binding.fragmentFeedListToolbar;
        feedRecyclerView = binding.fragmentFeedListFeedRecyclerView;
        refreshAllFeedsFab = binding.fragmentFeedListToggleServiceFab;

        toolbar.inflateMenu(R.menu.fragment_feed_list_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.fragment_feed_list_action_create:  {
                    Navigation.findNavController(view).navigate(R.id.action_feedListFragment_to_createFeedFragment);
                    return true;
                }
                case R.id.fragment_feed_list_action_reset: {
                    feedViewModel.nuke();
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
            }
        });

        refreshAllFeedsFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        feedViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<Feed>>() {
            @Override
            public void onChanged(List<Feed> feeds) {
                feedListAdapter.submitList(feeds);
            }
        });

        feedRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        feedRecyclerView.setAdapter(feedListAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
