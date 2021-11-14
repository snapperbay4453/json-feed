package com.snapperbay4453.jsonfeed.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.snapperbay4453.jsonfeed.R;
import com.snapperbay4453.jsonfeed.databinding.FragmentEditFeedBinding;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.repositories.FeedRepository;
import com.snapperbay4453.jsonfeed.viewmodels.FeedViewModel;

public class EditFeedFragment extends Fragment {

    private FragmentEditFeedBinding binding;
    private FeedRepository feedRepository;
    private Feed feed;
    private View view;
    private FeedViewModel feedViewModel;
    private Toolbar toolbar;
    private TextInputLayout nameInputWrap;
    private TextInputLayout urlInputWrap;
    private TextInputLayout filterInputWrap;
    private TextInputLayout refreshIntervalInputWrap;
    private Button deleteButton;
    private Button submitButton;

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException exception){
            return false;
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if(TextUtils.isEmpty(nameInputWrap.getEditText().getText())) {
            nameInputWrap.setError(getString(R.string.this_field_is_required));
            isValid = false;
        } else {
            nameInputWrap.setError(null);
        }

        if(TextUtils.isEmpty(urlInputWrap.getEditText().getText())) {
            urlInputWrap.setError(getString(R.string.this_field_is_required));
            isValid = false;
        } else {
            urlInputWrap.setError(null);
        }

        if(TextUtils.isEmpty(refreshIntervalInputWrap.getEditText().getText())) {
            refreshIntervalInputWrap.setError(getString(R.string.this_field_is_required));
            isValid = false;
        } else if(!isNumeric(refreshIntervalInputWrap.getEditText().getText().toString())) {
            refreshIntervalInputWrap.setError(getString(R.string.only_numbers_are_allowed));
            isValid = false;
        } else {
            refreshIntervalInputWrap.setError(null);
        }

        return isValid;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentEditFeedBinding.inflate(inflater, container, false);
        feedRepository = new FeedRepository(getActivity().getApplication());
        feed = feedRepository.selectByIdSync(EditFeedFragmentArgs.fromBundle(getArguments()).getId());
        view = binding.getRoot();
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        toolbar = binding.fragmentEditFeedToolbar;
        nameInputWrap = binding.fragmentEditFeedNameInputWrap;
        urlInputWrap = binding.fragmentEditFeedUrlInputWrap;
        filterInputWrap = binding.fragmentEditFeedFilterInputWrap;
        refreshIntervalInputWrap = binding.fragmentEditFeedRefreshIntervalInputWrap;
        deleteButton = binding.fragmentEditFeedDeleteButton;
        submitButton = binding.fragmentEditFeedSubmitButton;

        nameInputWrap.getEditText().setText(feed.getName());
        urlInputWrap.getEditText().setText(feed.getUrl());
        filterInputWrap.getEditText().setText(feed.getFilter());
        refreshIntervalInputWrap.getEditText().setText(Integer.toString(feed.getRefreshInterval()));

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                feedViewModel.delete(feed);
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validateInputs()) {
                    feed.setName(nameInputWrap.getEditText().getText().toString());
                    feed.setUrl(urlInputWrap.getEditText().getText().toString());
                    feed.setFilter(filterInputWrap.getEditText().getText().toString());
                    feed.setRefreshInterval(Integer.parseInt(refreshIntervalInputWrap.getEditText().getText().toString()));
                    feedViewModel.update(feed);
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
