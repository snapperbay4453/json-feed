package com.snapperbay4453.jsonfeed.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.snapperbay4453.jsonfeed.databinding.FragmentCreateFeedBinding;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.viewmodels.FeedViewModel;

public class CreateFeedFragment extends Fragment {

    private FragmentCreateFeedBinding binding;
    private View view;
    private FeedViewModel feedViewModel;
    private EditText nameInput;
    private EditText urlInput;
    private EditText descriptionInput;
    private Button submitButton;

    private boolean validateInputs() {
        boolean isValid = true;
        if(TextUtils.isEmpty(nameInput.getText())) {
            nameInput.setError("This field is required.");
            isValid = false;
        }
        if(TextUtils.isEmpty(urlInput.getText())) {
            urlInput.setError("This field is required.");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentCreateFeedBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        nameInput = binding.fragmentCreateFeedNameInput;
        urlInput = binding.fragmentCreateFeedUrlInput;
        descriptionInput = binding.fragmentCreateFeedDescriptionInput;
        submitButton = binding.fragmentCreateFeedSubmitButton;

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validateInputs()) {
                    feedViewModel.save(new Feed(nameInput.getText().toString(), urlInput.getText().toString(), descriptionInput.getText().toString()));
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
